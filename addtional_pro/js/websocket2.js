//const uuidV4 = require('uuid/v4');

var express = require('express');
var app = express();
var WebSocketServer = require('ws').Server;
var wss = new WebSocketServer({
    port: 8181
}); //服务端口8181
var speed = {//transid-[inputspeed, outspeed]
    "A11111": {"inputSpeed": 1000, "outSpeed": 800 },
    "A22222": {"inputSpeed": 1000, "outSpeed": 800 },
}

var count = 0;
var randomSpeedUpdater = function () {
    try {
        count++;
        for (var item in speed) {
            // if(item != 'isExec'){
                var inChange = parseInt(Math.random() * 100);//Math.random(60, 120);
                var outChange = parseInt(Math.random() * 100);//Math.random(60, 120);
                if(Math.random() * 10>4){
                    speed[item]["inputSpeed"] += inChange;
                    speed[item]["outSpeed"] += outChange;
                } else {
                    speed[item]["inputSpeed"] -= inChange;
                    speed[item]["outSpeed"] -= outChange;
                }
            // }
            if(count%9==0){
                if(speed.A11111){
                    delete speed.A11111;
                } else {
                    speed.A11111 = {"inputSpeed": 1000, "outSpeed": 800 }
                }
            }
        }
        console.log(new Date()+" 更新数据:", JSON.stringify(speed));
    } catch (err) {
        console.log(err);
    }
}
//模拟车速实时变化

console.log(__filename, __dirname);
console.log(WebSocketServer.__dirname, WebSocketServer.__filename);

setInterval(
    function () {
        randomSpeedUpdater();
    }, 3000)

//var clientSpeeds = [];
var clientID = 1;
var clientMap = {};//clientID-transid
wss.on('connection', function (ws) {
    //var uuid = uuidV4();
    var currID = clientID ++;
    ws.id = currID;
    var sendSpeedUpdates = function (ws) {
        try {
            if (ws.readyState == 1) {
                var transid = clientMap[currID];
                if (transid) {
                    var speedObj = speed[transid]
                    if(speedObj){
                        speedObj.isExec = true;
                        speedObj.inputSpeed = speedObj.inputSpeed < 0 ? 0 : speedObj.inputSpeed;
                        speedObj.outSpeed = speedObj.outSpeed < 0 ? 0 : speedObj.outSpeed;
                    } else {
                        speedObj = {'isExec': false};
                    }
                    ws.send(JSON.stringify(speedObj)); //需要将对象转成字符串。WebSocket只支持文本和二进制数据,推送消息
                    console.log("服务器给客户端["+ currID +"]推送数据：", JSON.stringify(speedObj));
                }
            }
        } catch (err) {
            console.log(err);
        }
    }

    //每三秒发送一次
    var clientSpeedUpdater;
    ws.on('message', function (message) {
        var stockRequest = JSON.parse(message); //根据请求过来的数据来更新。
        console.log("服务器收到数据：", JSON.stringify(stockRequest));
        if(clientMap[currID]){//已监听,先取消之前的定时推送
            clearInterval(clientSpeedUpdater);
        }
        clientMap[currID] = stockRequest.transid;
        if(speed[stockRequest.transid]){//有速度，则启动定时推送
            clientSpeedUpdater = setInterval(function () {
                sendSpeedUpdates(ws);
            }, 5000);
        } else {//无速度，回复一次
            speedObj = {isExec : false}
            ws.send(JSON.stringify(speedObj)); //需要将对象转成字符串。WebSocket只支持文本和二进制数据,推送消息
            console.log("服务器给客户端["+ currID +"]推送数据：", JSON.stringify(speedObj));
        }
    });
    ws.on('close', function () {
        console.log("客户端["+ currID +"]断开连接");
        delete clientMap[currID];
        if (clientSpeedUpdater) {
            //断开连接清楚定时器
            clearInterval(clientSpeedUpdater);
        }
    });
});

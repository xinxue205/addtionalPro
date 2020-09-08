//const uuidV4 = require('uuid/v4');

var express = require('express');
var app = express();
var WebSocketServer = require('ws').Server;
var wss = new WebSocketServer({
    port: 8181
}); //服务端口8181
var speed = {
    "A11111": [1, 2],
    "A22222": [1, 2],
}

var count = 0;
var randomSpeedUpdater = function () {
    count++;
    for (var item in speed) {
        // if(item != 'isExec'){
            var randomizedChange = parseInt(Math.random() * 10+1);//Math.random(60, 120);
            if(Math.random() * 10>4){
                speed[item][0] += randomizedChange;
                speed[item][1] += randomizedChange;
            } else {
                speed[item][0] -= randomizedChange;
                speed[item][1] -= randomizedChange;
            }
        // }
    }
    // if(count%10==0){
    //     speed['isExec'] = !speed['isExec'] ;
    // }
    console.log("更新数据:", JSON.stringify(speed));
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
var clientMap = {};
wss.on('connection', function (ws) {
    //var uuid = uuidV4();
    var currID = clientID ++;
    ws.id = currID;
    var sendSpeedUpdates = function (ws) {
        if (ws.readyState == 1) {
            var speedObj = speed[clientMap[currID]];
            // for (var i = 0; i < clientSpeeds.length; i++) {
            //     var item = clientSpeeds[i];
            //     if(!speed[item]){
            //         speed[item] = 0;
            //     }
            //     speedObj[item] = speed[item];
            // }
            if (speedObj) {
                ws.send(JSON.stringify(speedObj)); //需要将对象转成字符串。WebSocket只支持文本和二进制数据,推送消息
                console.log("服务器推送数据：", JSON.stringify(speedObj));
            }

        }
    }

    //每三秒发送一次
    var clientSpeedUpdater = setInterval(function () {
        sendSpeedUpdates(ws);
    }, 3000);
    ws.on('message', function (message) {
        var stockRequest = JSON.parse(message); //根据请求过来的数据来更新。
        console.log("服务器收到数据：", JSON.stringify(stockRequest));
//        clientSpeeds = stockRequest['speed'];
        clientMap[currID] = stockRequest.transid;
        if(!speed[stockRequest.transid]){
            speed[stockRequest.transid] = [1, 1];
        }
        //sendSpeedUpdates(ws);
    });
    ws.on('close', function () {
        if (clientSpeedUpdater) {
            //断开连接清楚定时器
            clearInterval(clientSpeedUpdater);
        }
    });
});

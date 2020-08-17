var WebSocketServer = require('ws').Server,
    wss = new WebSocketServer({
        port: 8182
    }); //服务端口8181
var speed = {
    "A11111": 95.0,
    "A22222": 50.0
}

var randomSpeedUpdater = function () {
    for (var item in speed) {

        var randomizedChange = Math.random(60, 120);
        speed[item] += randomizedChange;

    }
}
//模拟车速实时变化

setInterval(
    function () {
        randomSpeedUpdater();
    }, 1000)

var clientSpeeds = [];
wss.on('connection', function (ws) {
    var sendSpeedUpdates = function (ws) {
        if (ws.readyState == 1) {
            var speedObj = {};
            for (var i = 0; i < clientSpeeds.length; i++) {
                var item = clientSpeeds[i];
                speedObj[item] = speed[item];
            }
            if (speedObj.length !== 0) {
                ws.send(JSON.stringify(speedObj)); //需要将对象转成字符串。WebSocket只支持文本和二进制数据,推送消息
                console.log("服务器：更新数据", JSON.stringify(speedObj));
            }

        }
    }

    //每三秒发送一次
    var clientSpeedUpdater = setInterval(function () {
        sendSpeedUpdates(ws);
    }, 3000);
    ws.on('message', function (message) {
        var stockRequest = JSON.parse(message); //根据请求过来的数据来更新。
        console.log("服务器：收到消息", stockRequest);
        clientSpeeds = stockRequest['speed'];
        sendSpeedUpdates(ws);
    });
    ws.on('close', function () {
        if (clientSpeedUpdater) {
            //断开连接清楚定时器
            clearInterval(clientSpeedUpdater);
        }
    });
});

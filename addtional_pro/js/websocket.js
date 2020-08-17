
var port = 8181;
var WebSocketServer = require('ws').Server;
var wss = new WebSocketServer({ port: port });//服务端口8181
var conns = {}
wss.on('connection', function (ws) {
    console.log('客户端连接');
    ws.on('message', function (message) {
        //打印客户端监听的消息
        console.log(message);
    });
    conns[ws] = ws 
});

wss.on('close', function (ws) {
    console.log('客户端断开');
    delete conns[ws]
});

console.log('server is listening on port ' + port);  

var data = {
	'name': '',
	'connection': '',
	'schema': '',
	'table': '',
	'commit': '1000',
	'truncate': false,
	'ignore_errors': false,
	'specify_fields': false,

	'partitioning_enabled': false,
	'partitioning_field': '',
	'partitioning': 'monthly',
	'use_batch': false,
	'tablename_in_field': false,
	'tablename_field': '',
	'tablename_in_table': true,
	'return_keys': false,
	'return_field': '',

	'fields': [
		{
		'column_name': 'a1',
		'stream_name': 'aa1',
		},
		{
		'column_name': 'b1',
		'stream_name': 'bb1',
		},
		{
		'column_name': 'c1',
		'stream_name': 'cc1',
		},
	],
};

var port = 8088;
 
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
let Mock = require('mockjs'); 

//app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
//设置一下body-parser的解析的编码方式

//express中是通过req.body  来去获取  http实体上所带的参数
//var jsonParser = bodyParser.json();
app.post('/api/save', function (req,res) {
	//req.body  先设置一下body-parser的解析的编码方式
	console.log('/api/save', req.body);
	//{ sex: 'male', user: 'Mr.koo', height: '180' }
	//req.body.sex  req.body.user req.body.height
	data = req.body;
	var result = {code: 'SUCC'}
	// Object.assign(data, {code: 'SUCC'})
	res.send(result);
})

app.post('/api/read', function (req,res) {
	//req.body  先设置一下body-parser的解析的编码方式
	console.log('/api/read', req.body);
	//{ sex: 'male', user: 'Mr.koo', height: '180' }
	//req.body.sex  req.body.user req.body.height
	var result = {code:'SUCC', metadata:data}
	//Object.assign(data)
	res.send(result);
})

app.post('/api/getConnections', function (req,res) {
	console.log('/api/getConnections', req.body);
	var data = Mock.mock({
        "code": "SUCC",
        "data|1-9": [{
			'name': '数据库连接-@ctitle', //随机生成一个中文标题
			// 'name': '@cparagraph(2)', //生成一段2句话的中文文本,
            "id|+1": 1,
        }]
    })
	// var data = [{id:1, name:'数据库连接1'},{id:2, name:'数据库连接2'},{id:3, name:'数据库连接3'}]
	// var result = {code:'SUCC', data: data}
	res.send(data);
})

app.post('/api/getFields', function (req,res) {
	//req.body  先设置一下body-parser的解析的编码方式
	console.log('/api/getFields', req.body);
	//{ sex: 'male', user: 'Mr.koo', height: '180' }
	//req.body.sex  req.body.user req.body.height
	var data = {column_fields: ['a2','b2','c2'], stream_fields: ['aa2','bb2','cc2']}
	var result = {code:'SUCC', data: data}
	//Object.assign(data)
	res.send(result);
})


var port = 8088;
app.listen(port, function () {
	  console.log('server is listening on port ' + port);  
})
 
/*
var http = require('http');
var urllib = require('url'); 
http.createServer(function(req, res){
  var params = urllib.parse(req.url, true);
  console.log(params );
 if (params.query && params.query.callback) {
    //console.log(params.query.callback);
    var str =  params.query.callback + '(' + JSON.stringify(data) + ')';//jsonp
    res.end(str);
  } else {
    res.end(JSON.stringify(data));//普通的json
  }     
}).listen(port, function(){
  console.log('server is listening on port ' + port);  
})
*/

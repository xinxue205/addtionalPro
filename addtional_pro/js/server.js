var data = {
	'name': '',
	'connection': '',
	'schema': '',
	'table': '',
	'commit': '1000',
	'truncate': false,
	'ignore_errors': false,
	'specify_fields': true,

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
		'column_name': 'aaa',
		'stream_name': 'a2',
		},
		{
		'column_name': 'b',
		'stream_name': 'b2',
		},
		{
		'column_name': 'c',
		'stream_name': 'c2',
		},
	],
};

var port = 8088;
 
var express = require('express');
var app = express();
var bodyParser = require('body-parser');

//app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
//设置一下body-parser的解析的编码方式

//express中是通过req.body  来去获取  http实体上所带的参数
//var jsonParser = bodyParser.json();
app.post('/api/save', function (req,res) {
	//req.body  先设置一下body-parser的解析的编码方式
	console.log(req.body);
	//{ sex: 'male', user: 'Mr.koo', height: '180' }
	//req.body.sex  req.body.user req.body.height
	data = req.body;
	var result = {code: 'SUCC'}
	// Object.assign(data, {code: 'SUCC'})
	res.send(result);
})

app.post('/api/read', function (req,res) {
	//req.body  先设置一下body-parser的解析的编码方式
	console.log(req.body);
	//{ sex: 'male', user: 'Mr.koo', height: '180' }
	//req.body.sex  req.body.user req.body.height
	var result = {code:'SUCC', metadata:data}
	//Object.assign(data)
	res.send(result);
})

app.post('/api/getFields', function (req,res) {
	//req.body  先设置一下body-parser的解析的编码方式
	console.log(req.body);
	//{ sex: 'male', user: 'Mr.koo', height: '180' }
	//req.body.sex  req.body.user req.body.height
	var data = {column_fields: ['aa','bb','cc'], stream_fields: ['aa1','bb1','cc1']}
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

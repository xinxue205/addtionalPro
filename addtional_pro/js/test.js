var param = {
	name: 'name',
	test_func: function(){
		console.log(this);
	},
	arr: [{a: '1'},{b: '2'}]
};

var data = {code: 'succ'}
Object.assign(data, param)
console.log(data)
let test1 = {newData: '123'}; 
let test = {...param, ...test1}
console.log(test)
param.test_func();
param.test_func.call(2);
var func = param.test_func.bind(3);
func();

var func1 = function(){
	console.log(this);
}.bind(4);//bind后，该对象不会再发生变化
func1.bind(5);
func1();

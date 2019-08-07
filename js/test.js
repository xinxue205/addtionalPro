var param = {
	name: 'name',
	test_func: function(){
		console.log(this);
	},
};
param.test_func();
param.test_func.call(2);
var func = param.test_func.bind(3);
func();

var func1 = function(){
	console.log(this);
}.bind(4);//bind后，该对象不会再发生变化
func1.bind(5);
func1();

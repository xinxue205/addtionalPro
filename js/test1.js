function A(n) {
    return new Promise(resolve => {//resolve是调用then方法时传的方法
    	console.log(`Promise ${n}`);
        setTimeout(() => resolve(n + 200), n*10);//调用resolve对应的方法
    });
}

function step1(n) {
    console.log(`step1 with ${n}`);
    return A(n+100);//返回一个promise
}

function step2(n) {
    console.log(`step2 with ${n}`);
    return A(n+100);//返回一个promise
}

function step3(n) {
    console.log(`step3 with ${n}`);
    return A(n+100);//返回一个promise
}

function doIt() {
    console.time('do it now')
    const time1 = 300;
    step1(time1)
          .then( result => step2(result))
          .then( result => step3(result))
          .then( result => {
              console.log(`result is ${result}`)
           });
}

// doIt();

function* gen(x) {
    var y = yield x +2;
    y = yield x +3;
    return y;
}
  
var g = gen(1);
console.log( g.next()) // { value: 3, done: false }  value 属性是 yield 语句后面的表达式在当前阶段的值，done表示Generator函数是否执行完毕。
console.log( g.next()) // { value: 4, done: false }
console.log( g.next()) // { value: undefined, done: true }


async function test() {
  return "async 有什么用？";
}
const result = test();
console.log(result); //Promise { 'async 有什么用？' } async函数返回的是一个通过PromIse.resolve()封装的Promise对象

function takeLongTime(n) {
    return new Promise(resolve => {
        setTimeout(() => resolve(n + 200), n);
    });
}

function step1(n) {
    console.log(`step1 with ${n}`);
    return takeLongTime(n);
}

function step2(n) {
    console.log(`step2 with ${n}`);
    return takeLongTime(n);
}

function step3(n) {
    console.log(`step3 with ${n}`);
    return takeLongTime(n);
}

async function doIt() {//可以把async看做方法的同步修饰符，约束方法的步骤同步执行
    console.time("doIt");
    const time1 = 300;
    const time2 = await step1(time1);//同步一个await的Promise对象，会阻塞后面的代码，等待Promise对象resolve，取得resolve的值，作为表达式的结果
    const time3 = await step2(time2);
    const result = await step3(time3);
    console.log(`result is ${result}`);
    console.timeEnd("doIt");
}

doIt();

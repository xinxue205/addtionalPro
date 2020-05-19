namespace java com.micmiu.thrift.demo
 
service  HelloWorldService {
  string sayHello(1:string username, 2:string nickName, 3:string password)
}

//thrift-0.8.0.exe -r -gen java ./demoHello.thrift
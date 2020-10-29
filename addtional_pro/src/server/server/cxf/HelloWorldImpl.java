package server.server.cxf;

import javax.jws.WebService;

@WebService(endpointInterface = "server.server.cxf.HelloWorld",serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    @Override
    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }
    @Override
    public Integer getSquare(Integer num){
        return num*num;
    }
}
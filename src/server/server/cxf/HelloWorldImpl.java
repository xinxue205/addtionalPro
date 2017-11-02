package server.server.cxf;

import javax.jws.WebService;  

@WebService(endpointInterface="test.HelloWorld")  
public class HelloWorldImpl implements HelloWorld {  
      public String sayHello(String text) {  
                  return "Hello" + text ;  
    }  
  } 
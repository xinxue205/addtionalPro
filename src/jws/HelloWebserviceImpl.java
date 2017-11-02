package jws;

import javax.jws.WebService;
@WebService
public class HelloWebserviceImpl
{
    public String sayHello(String message)
    {
        System.out.println(message);
        return "Hello,"+message;
    }

}

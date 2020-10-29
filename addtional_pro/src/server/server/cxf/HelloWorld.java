package server.server.cxf;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
@WebService
public interface HelloWorld {
    /**
    * exclude=true,此方法不对外暴露
    */
    @WebMethod(exclude=true)
    String sayHi(String text);
    Integer getSquare(Integer num);
}


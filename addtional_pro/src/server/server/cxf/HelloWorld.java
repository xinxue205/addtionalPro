package server.server.cxf;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
@WebService
public interface HelloWorld {
    /**
    * exclude=true,�˷��������Ⱪ¶
    */
    @WebMethod(exclude=true)
    String sayHi(String text);
    Integer getSquare(Integer num);
}


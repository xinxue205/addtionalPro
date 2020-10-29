package server.server.cxf;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import java.net.URL;
 
public class Client1 {
    private static final QName SERVICE_NAME = new QName("http://cxf.server.server/", "HelloWorld");
    private static final QName PORT_NAME
            = new QName("http://cxf.server.server/", "HelloWorldPort");
 
    public static void main(String args[]) throws Exception {
//        Service service = Service.create(SERVICE_NAME);
        //Service service = Service.create(new URL("http://localhost:9000/helloWorld?wsdl"),SERVICE_NAME);
        // Endpoint Address
        // If web service deployed on Tomcat (either standalone or embedded)
        // as described in sample README, endpoint should be changed to:
        // String endpointAddress = "http://localhost:8080/java_first_jaxws/services/hello_world";
 
        // Add a port to the Service
//        String endpointAddress = "http://localhost:9000/helloWorld";
//        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
// 
//        HelloWorld hw = service.getPort(HelloWorld.class);
//        System.out.println(hw.sayHi("my baby2"));
// 
//        System.out.println(hw.getSquare(123));
        
        URL WSDL_LOCATION = new URL("http://localhost:9000/helloWorld?wsdl");  
        Service service = Service.create(WSDL_LOCATION, SERVICE_NAME);  
        HelloWorld hw = service.getPort(HelloWorld.class);  
        System.out.println(hw.sayHi("World"));  
 
    }
}

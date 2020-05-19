package jws;

import javax.xml.ws.Endpoint;

public class HelloWebservicePublish
{
    public static void main(String[] args)
    {
        try
        {
            Endpoint.publish("http://127.0.0.1:8002/webservice/hello", new HelloWebserviceImpl());
            System.out.println("webservice≤‚ ‘Õ®π˝");
        } catch (Exception e)
        {
            System.out.println("webservice≤‚ ‘ ß∞‹"+e.getMessage());
            ;
        }
    }
}

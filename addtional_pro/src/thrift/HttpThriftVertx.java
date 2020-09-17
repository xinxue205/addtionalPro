package thrift;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import thrift.add.AddService;

public class HttpThriftVertx extends AbstractVerticle{
    public static void main(String[] args) {
        Vertx vertx=Vertx.vertx();
        Router router = Router.router(vertx);
        // 解决跨域问题
                router.route().handler(
                        CorsHandler.create("*").allowedMethod(HttpMethod.GET)
                                .allowedMethod(HttpMethod.POST)
                                .allowedMethod(HttpMethod.OPTIONS)
                                .allowedHeader("X-PINGARUNER")
                                .allowedHeader("Content-Type"));
                router.route(HttpThriftMime.servName).handler(context->{
                    context.request().handler(buffer->{
                        byte[] arr=buffer.getBytes();
                        vertx.executeBlocking(future->{
                            String result=thriftRequest(arr);
                            future.complete(result);
                        }, res->{
                            if(res.succeeded()) {
                                context.response().end(res.result().toString());
                            }else {
                                context.response().end(res.cause().getMessage());
                            }
                        });
                    });
                });
                vertx.createHttpServer().requestHandler(router::accept).listen(HttpThriftMime.port, res->{
                    if(res.succeeded()) {
                        System.out.println("Server starts successfully!");
                    }else {
                        System.out.println("Server fails to start!");
                    }
                });
                vertx.deployVerticle(new HttpThriftVertx());
    }
    private static String thriftRequest(byte[] input){
        try{

            //Input
            TMemoryBuffer inbuffer = new TMemoryBuffer(input.length);   
            System.out.println("input-length:"+input.length);
            inbuffer.write(input);              
            TProtocol  inprotocol   = new TJSONProtocol(inbuffer);                   

            //Output
            TMemoryBuffer outbuffer = new TMemoryBuffer(100);           
            TProtocol outprotocol   = new TJSONProtocol(outbuffer);
            //这里是关键的地方，thrift提供了一个通用的service接口TProcessor，前端发送请求后，实际通过DbSourceTableMgrImp的方法进行处理
            TProcessor processor = new AddService.Processor(new AddService.Iface() {
				
				@Override
				public String addFunc(String param1, String param2) throws TException {
					int par1 = Integer.parseInt(param1);
					int par2 = Integer.parseInt(param2);
					return String.valueOf(par1+par2);
				}
			});      
  
            processor.process(inprotocol, outprotocol);
            byte[] output = new byte[outbuffer.length()];
            outbuffer.readAll(output, 0, output.length);
            return new String(output,"UTF-8");
        }catch(Throwable t){
            return "Error:"+t.getMessage();
        }


    }
}

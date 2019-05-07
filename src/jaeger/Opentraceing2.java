package jaeger;

import java.net.SocketException;

import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerSpanContext;
import io.jaegertracing.spi.Sender;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class Opentraceing2 {

private static Logger log = LoggerFactory.getLogger(Opentraceing2.class);;

public static void main(String[] args) throws InterruptedException {
        
        Configuration conf = new Configuration("EK Demo Jaeger."); //配置全局configuration
        //发送sender configuration
        Configuration.SenderConfiguration senderConf = new Configuration.SenderConfiguration();
        
        senderConf.withAgentHost("192.168.1.111");
        senderConf.withAgentPort(5775);
        
        Sender sender = senderConf.getSender();
        log.info("[ sender ] : "+sender);
        
        conf.withReporter(
                new Configuration.ReporterConfiguration()
                        .withSender(senderConf)
                        .withFlushInterval(100)
                        .withLogSpans(false)
        );
        
        conf.withSampler(
                new Configuration.SamplerConfiguration()
                        .withType("const")
                        .withParam(1)
        );
        
        Tracer tracer = conf.getTracer();
        log.info(tracer.toString());
        GlobalTracer.register(tracer);
        
        Tracer.SpanBuilder spanBuilder = GlobalTracer.get().buildSpan("EK Demo P");
        Span parent = spanBuilder.start();
        parent.log(100, "before Controller Method is running......");
        log.info("before Controller Method is running......");
        
        Tracer.SpanBuilder childB = GlobalTracer.get().buildSpan("EK Demo child").asChildOf(parent);
        Span child = childB.start();
        JaegerSpanContext context = (JaegerSpanContext) child.context();
        child.log("......"+context);
        
        String url = "http://localhost:8080/jeeek/services/phopuService/getUserPost";
        HttpClient httpClient = HttpClients.createSystem();
        final HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "text/plain");
        StringEntity se = null;
        
        String weatherInfo = null;
        try {
            
            //透传context到服务端
            tracer.inject(parent.context(), Format.Builtin.TEXT_MAP, new TextMap() {
                @Override
                public Iterator<Map.Entry<String, String>> iterator() {
                    throw new UnsupportedOperationException("TextMapInjectAdapter should only be used with Tracer.inject()");
                }
                
                @Override
                public void put(String key, String value) {
                    log.info(key+",----------------------- "+value);
                    httpPost.setHeader(key, value);
                }
            });
            
            se = new StringEntity("101010500");
            se.setContentType("text/plain");
            httpPost.setEntity(se);
            HttpResponse response = null;
            
            response = httpClient.execute(httpPost);
            
            int status = response.getStatusLine().getStatusCode();
            log.info("[接口返回状态吗] : " + status);
            
            
            weatherInfo = getReturnStr(response);
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        log.info("[接口返回信息] : " + weatherInfo);
        
        Thread.sleep(5000);
        child.finish();
        Thread.sleep(5000);
        parent.finish();
        log.info("after Controller Method is running.......");
        
        Thread.sleep(10000);
    }
}

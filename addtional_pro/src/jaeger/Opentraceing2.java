package jaeger;

import java.net.SocketException;
import io.jaegertracing.Configuration;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class Opentraceing2 {

    public static void main(String[] args) throws SocketException, InterruptedException {

    	// 将 manualDemo 替换为您的应用名称
    	io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("manualDemo");
    	io.jaegertracing.Configuration.SenderConfiguration sender = new io.jaegertracing.Configuration.SenderConfiguration();
    	// 将 <endpoint> 替换为控制台概览页面上相应客户端和相应地域的接入点
    	sender.withEndpoint("<endpoint>");
    	config.withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1));
    	config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000));
    	GlobalTracer.register(config.getTracer());
    	
    	Tracer tracer = GlobalTracer.get();
    	// 创建 Span
    	Span span = tracer.buildSpan("parentSpan").withTag("myTag", "spanFirst").start();
    	tracer.scopeManager().activate(span, false);
    	tracer.activeSpan().setTag("methodName", "testTracing");
    	// ...业务逻辑
//    	secondBiz();
    	span.finish();

        Thread.sleep(30000);
    }
}

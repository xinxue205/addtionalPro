package jaeger;

import java.net.SocketException;
import io.jaegertracing.Configuration;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class Opentraceing3 {

    public static void main(String[] args) throws SocketException, InterruptedException {

    	// �� manualDemo �滻Ϊ����Ӧ������
    	io.jaegertracing.Configuration config = new io.jaegertracing.Configuration("manualDemo");
    	io.jaegertracing.Configuration.SenderConfiguration sender = new io.jaegertracing.Configuration.SenderConfiguration();
    	// �� <endpoint> �滻Ϊ����̨����ҳ������Ӧ�ͻ��˺���Ӧ����Ľ����
    	sender.withEndpoint("<endpoint>");
    	config.withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType("const").withParam(1));
    	config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withMaxQueueSize(10000));
    	GlobalTracer.register(config.getTracer());
    	
    	Tracer tracer = GlobalTracer.get();
    	// ���� Span
    	Span span = tracer.buildSpan("parentSpan").withTag("myTag", "spanFirst").start();
    	tracer.scopeManager().activate(span, false);
    	tracer.activeSpan().setTag("methodName", "testTracing");
    	// ...ҵ���߼�
//    	secondBiz();
    	span.finish();

        Thread.sleep(30000);
    }
}

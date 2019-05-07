package jaeger;

import java.net.SocketException;
import io.jaegertracing.Configuration;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class Opentraceing {

    public static void main(String[] args) throws SocketException, InterruptedException {

        Configuration config = new Configuration("Hello Jaeger");

        Configuration.SenderConfiguration sender = new Configuration.SenderConfiguration();
        sender.withAgentHost("192.168.11.29");
        sender.withAgentPort(5775);
        config.withReporter(new Configuration.ReporterConfiguration().withSender(sender).withFlushInterval(100).withLogSpans(false));

        config.withSampler(new Configuration.SamplerConfiguration().withType("const").withParam(1));

        io.opentracing.Tracer tracer = config.getTracer();
        System.out.println(tracer.toString());
        GlobalTracer.register(tracer);


        Tracer.SpanBuilder spanBuilder = GlobalTracer.get().buildSpan("wxxhello");
        Span parent = spanBuilder.start();
        parent.log(100, "wxxhaha");


        Tracer.SpanBuilder spanBuilder2 = GlobalTracer.get().buildSpan("wxxworld").asChildOf(parent);
        Span child = spanBuilder2.start();
        child.log("wxxhaha2");
        child.finish();

        parent.finish();

        Thread.sleep(30000);
    }
}

package thrift.doubleside1;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.transport.TTransport;
 
public class ProcessorFactoryImpl extends TProcessorFactory {
 
	public ProcessorFactoryImpl(TProcessor processor) {
		super(processor);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public TProcessor getProcessor(TTransport trans) {
		// TODO Auto-generated method stub
		//return super.getProcessor(trans);
        return new CommunicateService.Processor(new CommunicateServiceImpl(trans));
	}
}


package server.server.cxf.inter;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2016-04-07T18:39:52.718+08:00
 * Generated source version: 3.1.6
 * 
 */
public final class IJobStatusUpdate_IJobStatusUpdatePort_Client {

    private static final QName SERVICE_NAME = new QName("http://inter.mcenter.sjjhpt.sinobest.cn/", "IJobStatusUpdateService");

    private IJobStatusUpdate_IJobStatusUpdatePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = IJobStatusUpdateService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        IJobStatusUpdateService ss = new IJobStatusUpdateService(wsdlURL, SERVICE_NAME);
        IJobStatusUpdate port = ss.getIJobStatusUpdatePort();  
        
        {
        System.out.println("Invoking updateJobStatus...");
        java.lang.String _updateJobStatus_jobID = "";
        java.lang.String _updateJobStatus_jobUID = "";
        int _updateJobStatus_currState = 0;
        int _updateJobStatus_lastState = 0;
        long _updateJobStatus_lastTime = 0;
        long _updateJobStatus_nextTime = 0;
        java.lang.String _updateJobStatus__return = port.updateJobStatus(_updateJobStatus_jobID, _updateJobStatus_jobUID, _updateJobStatus_currState, _updateJobStatus_lastState, _updateJobStatus_lastTime, _updateJobStatus_nextTime);
        System.out.println("updateJobStatus.result=" + _updateJobStatus__return);


        }

        System.exit(0);
    }

}

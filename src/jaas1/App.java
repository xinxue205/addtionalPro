package jaas1;

import javax.security.auth.login.LoginContext;  
import javax.security.auth.login.LoginException;  

public class App {  

    public static void main(String[] args) {  
        System.setProperty("java.security.auth.login.config", "D:/work/workspace-src/addtional_pro/src/jaas1/demo.config");  
        System.setProperty("java.security.policy", "D:/work/workspace-src/addtional_pro/src/jaas1/demo.policy");  
        System.setSecurityManager(new SecurityManager());  

        try {  
            //������¼������  
            LoginContext context = new LoginContext("demo", new DemoCallbackHander());  
            //���е�¼����¼���ɹ���ϵͳ�˳�  
            context.login();  
        } catch (LoginException le) {  
            System.err.println("Cannot create LoginContext. " + le.getMessage());  
            System.exit(-1);  
        } catch (SecurityException se) {  
            System.err.println("Cannot create LoginContext. " + se.getMessage());  
            System.exit(-1);  
        }  


        //������Դ  
        System.out.println(System.getProperty("java.home"));  
    }  
} 

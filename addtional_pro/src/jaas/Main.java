package jaas;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.security.Principal;
import java.security.PrivilegedAction;
 
public class Main {
    public static void main(String[] args) throws LoginException {
        // 配置文件中查找 Sample 名字的 LoginModule，并指定 CallbackHandler
    	System.setProperty("java.security.auth.login.config", "D:/work/workspace-src/addtional_pro/src/jaas/jaas.config");
    	System.setProperty("java.security.policy", "D:/work/workspace-src/addtional_pro/src/jaas/jaas.policy");
        LoginContext lc = new LoginContext("Sample", new MyCallbackHandler());
 
        try {
            lc.login();
            Subject subject = lc.getSubject();
//            System.out.println(subject);
            System.out.println("Authentication succeeded!");
 
 
            // 下面是 JAAS “授权” 示例代码
            Subject mySubject = lc.getSubject();
            System.out.println("Authenticated user has the following Principals:");
            for (Principal principal : mySubject.getPrincipals()) {
                System.out.println("\t" + principal.toString());
            }
 
            System.out.println("User has " +
                    mySubject.getPublicCredentials().size() +
                    " Public Credential(s)");
 
            // now try to execute the MyPrivilegedAction as the authenticated Subject
            PrivilegedAction action = new MyPrivilegedAction();
            Subject.doAsPrivileged(mySubject, action, null);
 
        } catch (LoginException e) {
            System.err.println("Authentication failed:" + e.getMessage());
            e.printStackTrace();
        }
    }
}

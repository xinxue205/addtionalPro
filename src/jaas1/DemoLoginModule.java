package jaas1;

import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.Map;

import javax.security.auth.Subject;  
import javax.security.auth.callback.Callback;  
import javax.security.auth.callback.CallbackHandler;  
import javax.security.auth.callback.NameCallback;  
import javax.security.auth.callback.PasswordCallback;  
import javax.security.auth.callback.UnsupportedCallbackException;  
import javax.security.auth.login.FailedLoginException;  
import javax.security.auth.login.LoginException;  
import javax.security.auth.spi.LoginModule;  

public class DemoLoginModule implements LoginModule {  
    private Subject subject;  
    private CallbackHandler callbackHandler;  
    private boolean success = false;  
    private String user;  
    private String password;  

    @Override  
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {  
        this.subject = subject;  
        this.callbackHandler = callbackHandler;  
    }  

    @Override  
    public boolean login() throws LoginException {  
        NameCallback nameCallback = new NameCallback("�������û���");  
        PasswordCallback passwordCallback = new PasswordCallback("����������", false);  
        Callback[] callbacks = new Callback[]{nameCallback, passwordCallback};  
        try {  
            //ִ�лص����ص������л�ȡ�û���������  
            callbackHandler.handle(callbacks);  
            //�õ��û���������  
            user = nameCallback.getName();  
            password = new String(passwordCallback.getPassword());  
        } catch (IOException | UnsupportedCallbackException e) {  
            success = false;  
            throw new FailedLoginException("�û����������ȡʧ��");  
        }  
        //Ϊ�������֤����д��  
        if(user.length()>3 && password.length()>3) {  
            success = true;//��֤�ɹ�  
        }  
        return true;  
    }  

    @Override  
    public boolean commit() throws LoginException {  
        if(!success) {  
            return false;  
        } else {  
            //�����֤�ɹ����subject�����һ��Principal����  
            //����ĳ����û�����֤ͨ������¼�˸�Ӧ�ã���������˭��ִ�иó���  
            this.subject.getPrincipals().add(new DemoPrincipal(user));  
            return true;  
        }  
    }  

    @Override  
    public boolean abort() throws LoginException {  
        logout();  
        return true;  
    }  

    @Override  
    public boolean logout() throws LoginException {  
        //�˳�ʱ����Ӧ��Principal�����subject���Ƴ�  
        Iterator<Principal> iter = subject.getPrincipals().iterator();  
        while(iter.hasNext()) {  
            Principal principal = iter.next();  
            if(principal instanceof DemoPrincipal) {  
                if(principal.getName().equals(user)) {  
                    iter.remove();  
                    break;  
                }  
            }  
        }  
        return true;  
    }  

}

package jaas1;

import java.io.IOException;  

import javax.security.auth.callback.Callback;  
import javax.security.auth.callback.CallbackHandler;  
import javax.security.auth.callback.NameCallback;  
import javax.security.auth.callback.PasswordCallback;  
import javax.security.auth.callback.UnsupportedCallbackException;  

public class DemoCallbackHander implements CallbackHandler {  

    @Override  
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {  
        NameCallback nameCallback = (NameCallback) callbacks[0];  
        PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];  
        //�����û���������  
        nameCallback.setName(getUserFromSomeWhere());  
        passwordCallback.setPassword(getPasswordFromSomeWhere().toCharArray());  
    }  


    //Ϊ������û���������д��ֱ�ӷ��أ���ʵ����������û�����Ⱦ����ȡ  
    public String getUserFromSomeWhere() {  
        return "zhangsan";  
    }  
    public String getPasswordFromSomeWhere() {  
        return "zhangsan";  
    }  
}

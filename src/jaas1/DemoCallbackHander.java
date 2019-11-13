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
        //设置用户名与密码  
        nameCallback.setName(getUserFromSomeWhere());  
        passwordCallback.setPassword(getPasswordFromSomeWhere().toCharArray());  
    }  


    //为简单起见用户名与密码写死直接返回，真实情况可以由用户输入等具体获取  
    public String getUserFromSomeWhere() {  
        return "zhangsan";  
    }  
    public String getPasswordFromSomeWhere() {  
        return "zhangsan";  
    }  
}

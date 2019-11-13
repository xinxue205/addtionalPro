package jaas;

import javax.security.auth.callback.*;
import java.io.IOException;
 
public class MyCallbackHandler implements CallbackHandler {
 
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
 
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof TextOutputCallback) {// display the message according to the specified type
                TextOutputCallback toc = (TextOutputCallback) callbacks[i];
                switch (toc.getMessageType()) {
                    case TextOutputCallback.INFORMATION:
                        System.out.println(toc.getMessage());
                        break;
                    case TextOutputCallback.ERROR:
                        System.err.println("ERROR: " + toc.getMessage());
                        break;
                    case TextOutputCallback.WARNING:
                        System.err.println("WARNING: " + toc.getMessage());
                        break;
                    default:
                        throw new IOException("Unsupported message type: " + toc.getMessageType());
                }
 
            } else if (callbacks[i] instanceof NameCallback) {// prompt the user for a username
                NameCallback nc = (NameCallback) callbacks[i];
                String name = "user";// TODO 这里可以实现为从控制台允许用户输入等方式接收用户参数。。。
                nc.setName(name);
            } else if (callbacks[i] instanceof PasswordCallback) {// prompt the user for sensitive information
                PasswordCallback pc = (PasswordCallback) callbacks[i];
                String pwd = "pwd";// TODO 这里可以实现为从控制台允许用户输入等方式接收用户参数。。。
                pc.setPassword(pwd.toCharArray());
            } else {
                throw new UnsupportedCallbackException
                        (callbacks[i], "Unrecognized Callback");
            }
        }
    }
}

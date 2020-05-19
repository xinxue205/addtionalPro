package jaas;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.security.Principal;
import java.util.Map;
 
public class MyLoginModule implements LoginModule {
 
    // username and password
    private String username;
    private char[] password;
 
    // the authentication status
    private boolean userPwdSucceeded = false;
    private boolean commitSucceeded = false;
 
    // user's Principal
    private Principal userPrincipal;
 
 
    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
 
 
    /**
     * Initialize this <code>LoginModule</code>.
     */
    public void initialize(Subject subject,
                           CallbackHandler callbackHandler,
                           Map<java.lang.String, ?> sharedState,
                           Map<java.lang.String, ?> options) {
 
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }
 
    /**
     * Authenticate the user by prompting for a user name and password.
     */
    public boolean login() throws LoginException {
        // prompt for a user name and password
        if (callbackHandler == null)
            throw new LoginException("Error: no CallbackHandler available " +
                    "to garner authentication information from the user");
 
        Callback[] callbacks = new Callback[4];
        callbacks[0] = new NameCallback("user name");
        callbacks[1] = new PasswordCallback("password", false);
        callbacks[2] = new TextOutputCallback(TextOutputCallback.INFORMATION, "hello, just a msg!");
        callbacks[3] = new TextOutputCallback(TextOutputCallback.WARNING, "just warn you!");
 
        try {
            callbackHandler.handle(callbacks);
            NameCallback nameCallback = (NameCallback) callbacks[0];
            PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];
 
            username = nameCallback.getName();
 
            char[] tmpPassword = passwordCallback.getPassword();
            passwordCallback.clearPassword();// clean password in memory space
            if (tmpPassword == null) {
                tmpPassword = new char[0];// treat a NULL password as an empty password
            }
            password = new char[tmpPassword.length];
            System.arraycopy(tmpPassword, 0, password, 0, tmpPassword.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
 
        // verify the username/password
        boolean usernameCorrect = false;
        if (username.equals("user")) usernameCorrect = true;
 
        if (usernameCorrect &&
                password.length == 3 &&
                password[0] == 'p' &&
                password[1] == 'w' &&
                password[2] == 'd') {
 
            userPwdSucceeded = true;
        } else {
            userPwdSucceeded = false;
            cleanUserAndPwdData();
            if (!usernameCorrect) {
                throw new FailedLoginException("User Name Incorrect");
            } else {
                throw new FailedLoginException("Password Incorrect");
            }
        }
        return userPwdSucceeded;
    }
 
    public boolean commit() throws LoginException {
        if (!userPwdSucceeded) return false;
 
        // add a Principal (authenticated identity) to the Subject
        userPrincipal = new SamplePrincipal(username);
        subject.getPrincipals().add(userPrincipal);
 
        // in any case, clean out state
        cleanUserAndPwdData();
 
        return commitSucceeded = true;
    }
 
    public boolean abort() throws LoginException {
        if (!userPwdSucceeded) return false;
 
        if (commitSucceeded) {
            logout();
        } else {
            cleanState();
        }
 
        return true;
    }
 
    public boolean logout() throws LoginException {
        subject.getPrincipals().remove(userPrincipal);
        cleanState();
        userPwdSucceeded = commitSucceeded;
        return true;
    }
 
    private void cleanState() {
        userPwdSucceeded = false;
        cleanUserAndPwdData();
        userPrincipal = null;
    }
 
    private void cleanUserAndPwdData() {
        username = null;
        if (password != null) {
            for (int i = 0; i < password.length; i++)
                password[i] = ' ';
            password = null;
        }
    }
}

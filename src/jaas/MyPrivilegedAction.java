package jaas;

import java.io.File;
import java.security.PrivilegedAction;
 
public class MyPrivilegedAction implements PrivilegedAction {
 
    public Object run() {
 
        System.out.println("\nYour java.home property value is: "
                + System.getProperty("java.home"));
 
        System.out.println("\nYour user.home property value is: "
                + System.getProperty("user.home"));
 
        File f = new File("foo.txt");
        System.out.print("\nfoo.txt does ");
        if (!f.exists())
            System.out.print("not ");
        System.out.println("exist in the current working directory.");
        return null;
    }
}

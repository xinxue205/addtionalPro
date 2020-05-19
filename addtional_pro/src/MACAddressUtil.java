

import java.util.regex.*;

import java.net.*;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;

public class MACAddressUtil
{

	private static final String G = "(.*wireless.*)|(.*tunnel.*)|(.*atapi.*)|(.*bluetooth.*)|(.*vnic.*)|(.*vmnet.*)";
    private static final Pattern A;
    private int D;
    private int F;
    private int E;
    private int B;
    private String C;
    
    static {
        A = Pattern.compile(G, 2);
    }
    
    public static void main(String[] args) throws Exception {
    	MACAddressUtil n = new MACAddressUtil();
    	n.s();
	}
    
    public void s() throws Exception{
    	String line;
    	final A a3 = new A();
    	String a = "";
    	BufferedReader bufferedReader = new BufferedReader(new FileReader("Test"), 128);
		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
			 a = N.A(line);
             if (a != null && a3.A(a) != 255) {
                 break;
             }
        }
		System.out.println(a);
    }
    
    public MACAddressUtil() {
        this.D = 6;
        this.F = 5;
        this.E = 0;
        this.B = this.F;
        this.C = null;
    }
    
    public String getMACAddress(boolean isCheck, String check) {
        if (this.C != null) {
            return this.C;
        }
        try {
            if (this.B == this.E) {
                Class.forName("java.net.InterfaceAddress");
                this.C = this.A();
                this.B = this.D;
            }
            else if (this.B == this.D) {
                this.C = this.A();
            }
            else {
                this.C = this.B(isCheck, check);
            }
        }
        catch (Throwable t) {
            this.C = this.B(false, null);
            this.B = this.F;
        }
        return this.C;
    }
    
    public boolean hasMACAddress(final String s) {
        boolean b;
        try {
            if (this.B == this.E) {
                Class.forName("java.net.InterfaceAddress");
                b = this.B(s);
                this.B = this.D;
            }
            else if (this.B == this.D) {
                b = this.B(s);
            }
            else {
                b = this.A(s);
                this.B = this.F;
            }
        }
        catch (Throwable t) {
            b = this.A(s);
            this.B = this.F;
        }
        return b;
    }
    
    private String A() {
        String format = null;
        try {
            final Class<NetworkInterface> clazz = NetworkInterface.class;
            final Method method = clazz.getMethod("getHardwareAddress", (Class<?>[])null);
            final Method method2 = clazz.getMethod("isVirtual", (Class<?>[])null);
            final Method method3 = clazz.getMethod("isLoopback", (Class<?>[])null);
            final Method method4 = clazz.getMethod("isUp", (Class<?>[])null);
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (format == null) {
                if (!networkInterfaces.hasMoreElements()) {
                    break;
                }
                final NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface == null) {
                    continue;
                }
                if ((Boolean) method2.invoke(networkInterface, (Object[])null)) {
                    continue;
                }
                if ((Boolean) method3.invoke(networkInterface, (Object[])null)) {
                    continue;
                }
                if (!(Boolean)method4.invoke(networkInterface, (Object[])null)) {
                    continue;
                }
                final byte[] array = (byte[])method.invoke(networkInterface, (Object[])null);
                if (array == null) {
                    continue;
                }
                if (array.length < 2) {
                    continue;
                }
                boolean b = false;
                for (int n = 1; n < array.length && !b; b = (array[n] > 0), ++n) {}
                if (!b) {
                    continue;
                }
                final String displayName = networkInterface.getDisplayName();
                if (displayName == null) {
                    continue;
                }
                if (displayName.length() == 0) {
                    continue;
                }
                if (MACAddressUtil.A.matcher(displayName).lookingAt()) {
                    continue;
                }
                format = String.format("%02X%02X", array[array.length - 2], array[array.length - 1]);
            }
        }
        catch (Throwable t) {}
        return this.C = ((format != null) ? format : "0000");
    }
    
    private String B(boolean isCheck, String check) {
    	String m1 = isCheck ? check.substring(2,4) : null ;
    	String m2 = isCheck ? check.substring(4,6) : null ;
        Process process = null;
        BufferedReader bufferedReader = null;
        String a = "";
        String nn = "";
        try {
            final String property = System.getProperty("os.name", "");
            if (property.startsWith("Windows")) {
                process = Runtime.getRuntime().exec(new String[] { "ipconfig", "/all" }, null);
            } else if (property.startsWith("Solaris") || property.startsWith("SunOS")) {
                final String a2 = this.A("uname", "-n");
                if (a2 != null) {
                    process = Runtime.getRuntime().exec(new String[] { "/usr/sbin/arp", a2 }, null);
                }
            } else if (new File("/usr/sbin/lanscan").exists()) {
                process = Runtime.getRuntime().exec(new String[] { "/usr/sbin/lanscan" }, null);
            } else if (new File("/sbin/ifconfig").exists()) {
                process = Runtime.getRuntime().exec(new String[] { "/sbin/ifconfig", "-a" }, null);
            }
            if (process != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 128);
                final A a3 = new A();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                	line = line.trim();
                	if(isCheck && !line.endsWith(m1+":"+m2) && line.endsWith(m1+"-"+m2)){
                		continue;
                	}
                    a = N.A(line);
                    if (a != null && a3.A(a) != 255) {
                        break;
                    }
                }
            }
        }
        catch (SecurityException ex) {}
        catch (IOException ex2) {}
        finally {
            if (process != null) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    }
                    catch (IOException ex3) {}
                }
                try {
                    process.getErrorStream().close();
                }
                catch (IOException ex4) {}
                try {
                    process.getOutputStream().close();
                }
                catch (IOException ex5) {}
                process.destroy();
            }
        }
        if (process != null) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException ex6) {}
            }
            try {
                process.getErrorStream().close();
            }
            catch (IOException ex7) {}
            try {
                process.getOutputStream().close();
            }
            catch (IOException ex8) {}
            process.destroy();
        }
        String string = null;
        final int n = (a != null) ? a.length() : 0;
        if (n >= 5) {
            string = String.valueOf(a.substring(n - 5, n - 3)) + a.substring(n - 2, n);
        }
        return this.C = ((string != null) ? string : "0000");
    }
    
    private boolean A(final String s) {
        return s!=null && s.equalsIgnoreCase(this.getMACAddress(false, null));
    }
    
    private boolean B(final String c) {
        if (c == null) {
            return false;
        }
        try {
            final Method method = NetworkInterface.class.getMethod("getHardwareAddress", (Class<?>[])null);
            final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                final NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface == null) {
                    continue;
                }
                final byte[] array = (byte[])method.invoke(networkInterface, (Object[])null);
                if (array == null) {
                    continue;
                }
                if (array.length < 2) {
                    continue;
                }
                boolean b = false;
                for (int n = 1; n < array.length && !b; b = (array[n] > 0), ++n) {}
                if (!b) {
                    continue;
                }
                final String displayName = networkInterface.getDisplayName();
                if (displayName == null) {
                    continue;
                }
                if (displayName.length() < 2) {
                    continue;
                }
                if (c.equalsIgnoreCase(String.format("%02X%02X", array[array.length - 2], array[array.length - 1]))) {
                    this.C = c;
                    return true;
                }
            }
        }
        catch (Throwable t) {}
        return false;
    }
    
    private String A(final String... array) throws IOException {
        Process exec = null;
        BufferedReader bufferedReader = null;
        try {
            exec = Runtime.getRuntime().exec(array);
            bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()), 128);
            return bufferedReader.readLine();
        }
        finally {
            if (exec != null) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    }
                    catch (IOException ex) {}
                }
                try {
                    exec.getErrorStream().close();
                }
                catch (IOException ex2) {}
                try {
                    exec.getOutputStream().close();
                }
                catch (IOException ex3) {}
                exec.destroy();
            }
        }
    }
    
    class A
    {
        private final char[] A;
        
        A() {
            this.A = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        }
        
        public Appendable A(final Appendable appendable, final short n) {
            return this.A(appendable, (long)n, 4);
        }
        
        public Appendable A(final Appendable appendable, final short n, final int n2) {
            return this.A(appendable, (long)n, n2);
        }
        
        public Appendable A(final Appendable appendable, final int n) {
            return this.A(appendable, (long)n, 8);
        }
        
        public Appendable A(final Appendable appendable, final int n, final int n2) {
            return this.A(appendable, (long)n, n2);
        }
        
        public Appendable A(final Appendable appendable, final long n) {
            return this.A(appendable, n, 16);
        }
        
        public Appendable A(final Appendable appendable, final long n, final int n2) {
            try {
                for (int i = (n2 << 2) - 4; i >= 0; i -= 4) {
                    appendable.append(this.A[(byte)(n >> i) & 0xF]);
                }
            }
            catch (IOException ex) {}
            return appendable;
        }
        
        public Appendable A(final Appendable appendable, final byte[] array) {
            try {
                for (final byte b : array) {
                    appendable.append(this.A[(byte)((b & 0xF0) >> 4)]);
                    appendable.append(this.A[(byte)(b & 0xF)]);
                }
            }
            catch (IOException ex) {}
            return appendable;
        }
        
        public long A(final CharSequence charSequence) {
            long n = 0L;
            for (int n2 = 0, n3 = 0; n3 < charSequence.length() && n2 < 16; ++n3) {
                final char char1 = charSequence.charAt(n3);
                if (char1 > '/' && char1 < ':') {
                    n2 = (byte)(n2 + 1);
                    n = (n << 4 | char1 - '0');
                }
                else if (char1 > '@' && char1 < 'G') {
                    n2 = (byte)(n2 + 1);
                    n = (n << 4 | char1 - '7');
                }
                else if (char1 > '`' && char1 < 'g') {
                    n2 = (byte)(n2 + 1);
                    n = (n << 4 | char1 - 'W');
                }
            }
            return n;
        }
        
        public short A(final String s) {
            short n = 0;
            for (int n2 = 0, n3 = 0; n3 < s.length() && n2 < 4; ++n3) {
                final char char1 = s.charAt(n3);
                if (char1 > '/' && char1 < ':') {
                    n2 = (byte)(n2 + 1);
                    n = (short)((short)(n << 4) | char1 - '0');
                }
                else if (char1 > '@' && char1 < 'G') {
                    n2 = (byte)(n2 + 1);
                    n = (short)((short)(n << 4) | char1 - '7');
                }
                else if (char1 > '`' && char1 < 'g') {
                    n2 = (byte)(n2 + 1);
                    n = (short)((short)(n << 4) | char1 - 'W');
                }
            }
            return n;
        }
    }
    
   static class N
    {
        static String A(final String s) {
            String s2 = s;
            final int index = s2.indexOf("0x");
            if (index != -1 && s2.indexOf("ETHER") != -1) {
                final int index2 = s2.indexOf(32, index);
                if (index2 > index + 2) {
                    s2 = s2.substring(index, index2);
                }
            }
            else {
                int n = 0;
                if (s2.indexOf(45) > -1) {
                    s2 = s2.replace('-', ':');
                }
                int n2 = s2.lastIndexOf(58);
                if (n2 > s2.length() - 2) {
                    s2 = null;
                }
                else {
                    final int min = Math.min(s2.length(), n2 + 3);
                    ++n;
                    int n3 = n2;
                    while (n != 5 && n2 != -1 && n2 > 1) {
                        n2 = s2.lastIndexOf(58, --n2);
                        if (n3 - n2 == 3 || n3 - n2 == 2) {
                            ++n;
                            n3 = n2;
                        }
                    }
                    if (n == 5 && n2 > 1) {
                        s2 = s2.substring(n2 - 2, min).trim();
                    }
                    else {
                        s2 = null;
                    }
                }
            }
            if (s2 != null && s2.startsWith("0x")) {
                s2 = s2.substring(2);
            }
            return s2;
        }
        
        static String NN(final String s) {
        	return s.substring(s.indexOf(58)+1, s.length()).trim();
        }
        
        static String NNX(final String s) {
        	return s.substring(0, s.indexOf(58)).trim();
        }
    }
}

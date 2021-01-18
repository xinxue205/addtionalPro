import com.sun.tools.attach.VirtualMachine;

public class VirtualMachineTest {
	public static void main(String[] args) {
		try {
			VirtualMachine virtualmachine = VirtualMachine.attach(args[0]);
			String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");
			System.out.println(javaHome);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}

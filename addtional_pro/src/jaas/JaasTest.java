package jaas;

import java.io.File;

public class JaasTest {
	
	public static void main(String[] args) {
//		System.out.println((new File("")).getAbsolutePath());
		System.setProperty("java.security.policy", "D:/work/workspace-src/addtional_pro/demo.policy");//当前路径（也就是工程路径）
//	    System.setProperty("java.security.policy", "D:/work/workspace-src/addtional_pro/target/classes/demo.policy");
	    //安装安全管理器
        System.setSecurityManager(new SecurityManager());

        System.out.println(System.getProperty("java.home"));
    }

}

package jaas;

import java.io.File;

public class JaasTest {
	
	public static void main(String[] args) {
//		System.out.println((new File("")).getAbsolutePath());
		System.setProperty("java.security.policy", "D:/work/workspace-src/addtional_pro/demo.policy");//��ǰ·����Ҳ���ǹ���·����
//	    System.setProperty("java.security.policy", "D:/work/workspace-src/addtional_pro/target/classes/demo.policy");
	    //��װ��ȫ������
        System.setSecurityManager(new SecurityManager());

        System.out.println(System.getProperty("java.home"));
    }

}

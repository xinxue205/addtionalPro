/**
 * 
 */
package serializable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;


import org.apache.xerces.impl.dv.util.Base64;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-8-29 下午5:04:09
 * @Description
 * @version 1.0 Shawn create
 */
public class Test1 {
	public static void main(String[] args) throws Exception {
		File f = new File("D://work//workspace-src//kettle-src-test//engine//dist//kettle-engine-5.3.0.0-213.jar");
//		String logChannelId = UUID.randomUUID().toString();
//		byte[] obj  = Base64.decode(logChannelId);
//		Object objT = new TestObject(obj);
		Set map = new HashSet();
		while(true){
			byte[] obj = getBytesFromFile(f);
			map.add(obj);
			Iterator i = map.iterator();
			while (i.hasNext()) {
				byte[] o = (byte[]) i.next();
				System.out.println((new String(o)).length());
			}
			map.remove(obj);
		}
	}
	
	public static void main1(String args[]) throws Exception {   
		ObjectInputStream si = new ObjectInputStream(new FileInputStream("data.ser"));   
		StudentA stu1 = (StudentA) new ObjectInputStream(new FileInputStream("data.ser")).readObject();  
	    si.close();
		System.out.println("StudentA Info:");   
		System.out.println("ID:" + stu1.id);   
		System.out.println("Name:" + stu1.name);   
	}
	
	public static void main2(String[] args) throws Exception {
		StudentA stu = new StudentA();
		stu.setId(1);
		stu.setName("name");
		ObjectOutputStream so = new ObjectOutputStream(new FileOutputStream("data.ser"));   
	    so.writeObject(stu);   
	    so.close();
	}
	
	public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            for (int n;(n = stream.read(b)) != -1;) {
				out.write(b, 0, n);
			}
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e){
        	return null;
        }
    }
}

class TestObject{
	byte[] obj;
	
	public TestObject(byte[] obj) {
		this.obj = obj;
	}
}
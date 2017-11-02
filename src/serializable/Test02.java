/**
 * 
 */
package serializable;

import java.io.*;

/**
 * @author Administrator
 * @date 2010-11-7 ÉÏÎç9:13:00
 * @version 1.0  Administrator create
 * @CopyRight (c) 2010 xxxx¹«Ë¾ 
 */

public class Test02 {

    public static void main(String[] args) {          
    	Cat cat = new Cat();
        try {
            FileOutputStream fos = new FileOutputStream("catDemo.out");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            System.out.println(" 1> " + cat.getName());
            cat.setName("My Cat");                        
            oos.writeObject(cat);
            oos.close();                        
        } catch (Exception ex) {  
        	ex.printStackTrace();   
        }
        try { 
            FileInputStream fis = new FileInputStream("catDemo.out");
            ObjectInputStream ois = new ObjectInputStream(fis);
            cat = (Cat) ois.readObject();
            System.out.println(" 2> " + cat.getName());
            ois.close();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }
}

class Cat implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public Cat () {
		this.name = "new cat";
	}

    public String getName() {
            return this.name;
    }

    public void setName(String name) {
            this.name = name;
    }
}

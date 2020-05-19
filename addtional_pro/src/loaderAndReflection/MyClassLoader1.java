/**
 * 
 */
package loaderAndReflection;

import java.io.*;

/**
 * @author Administrator
 * @date 2010-11-6 ����4:05:47
 * @version 1.0  Administrator create
 * @CopyRight (c) 2010 xxxx��˾ 
 */
public class MyClassLoader1 extends ClassLoader {

    //�����������
    private String name;
    //�������·��
    private String path = "D:/";
    private final String fileType = ".class";
    public MyClassLoader1(String name){
        //��ϵͳ���������Ϊ�� ��������ĸ�������
        super();
        this.name = name;
    }

    public MyClassLoader1(ClassLoader parent, String name){
        //��ʾָ������������ĸ�������
        super(parent);
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        return (loadClass(name, false));

    }
    
    protected Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException{
    	Class c = findLoadedClass(name);
    	if (c == null) {
    		c = findClass(name);
    	} 
    	
    	if (resolve) {
            resolveClass(c);
        }
    	return c;
    }
    
    /**
     * ��ȡ.class�ļ����ֽ�����
     * @param name
     * @return
     */
    private byte[] loaderClassData(String name){
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        name = name.replace(".", "/");
        try {
            is = new FileInputStream(new File(path + name + fileType));
            int c = 0;
            while(-1 != (c = is.read())){
                baos.write(c);
            }
            data = baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * ��ȡClass����
     */
    @Override
    public Class<?> findClass(String name){
        byte[] data = loaderClassData(name);
        return this.defineClass(name, data, 0, data.length);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //loader1�ĸ�������Ϊϵͳ�������
    	String path = "D:/download/classes/";
        MyClassLoader1 loader1 = new MyClassLoader1("loader1");
        loader1.setPath(path);
        //loader2�ĸ�������Ϊloader1
        MyClassLoader1 loader2 = new MyClassLoader1(loader1, "loader2");
        loader2.setPath(path);
        //loader3�ĸ�������Ϊ���������
        MyClassLoader1 loader3 = new MyClassLoader1(null, "loader3");
        loader3.setPath(path);

        Class clazz1 = loader1.loadClass("cn.sinobest.knob.ganger.persist.model.SchedJob");
        Class clazz2 = loader2.loadClass("cn.sinobest.knob.ganger.persist.model.SchedJob");
        Class clazz3 = loader3.loadClass("cn.sinobest.knob.ganger.persist.model.SchedJob");
        Object object1 = clazz1.newInstance();
        Object object2 = clazz2.newInstance();
        Object object3 = clazz3.newInstance();
    }
}
class Sample {

    public Sample(){
        System.out.println("Sample is loaded by " + this.getClass().getClassLoader());
        new A();
    }
}
class A {

    public A(){
        System.out.println("A is loaded by " + this.getClass().getClassLoader());
    }
}

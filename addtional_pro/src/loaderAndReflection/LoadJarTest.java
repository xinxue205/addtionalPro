/**
 * 
 */
package loaderAndReflection;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2010-11-6 ����4:37:18
 * @version 1.0  Administrator create
 * @CopyRight (c) 2010 xxxx��˾ 
 */
public class LoadJarTest {
	public static void main(String[] args) throws Exception {
		loadJarsByPath();
	}
	
	static void loadJarsByPath() throws Exception{
		loadJarPath("D:\\jar");
		Class<?> clazz = new MyClassLoader().loadClass("test.LoaderTest");
		Object obj = clazz.newInstance();
		int iResult = (Integer) clazz.getMethod("getTestResult", null).invoke(obj, null);
		System.out.println(iResult);
	}
	
	static void loadSingleJar() throws Exception{
		URL url = new URL("file:D:\\jar\\multThreadManage.jar");
		URLClassLoader loader = new URLClassLoader(new URL[]{url});
		Class clazz = loader.loadClass("test.LoaderTest");
		Object obj = clazz.newInstance();
		int iResult = (Integer) clazz.getMethod("getTestResult", null).invoke(obj, null);
		System.out.println(iResult);
	}
	
    /** URLClassLoader��addURL���� */  
    private static Method addURL = initAddMethod();   
       
    /** ��ʼ������ */  
    private static final Method initAddMethod() {   
        try {   
            Method add = URLClassLoader.class  
                .getDeclaredMethod("addURL", new Class[] { URL.class });   
            add.setAccessible(true);   
            return add;   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return null;   
    }   
  
    private static URLClassLoader system = (URLClassLoader) ClassLoader.getSystemClassLoader();   
  
    /**  
     * ѭ������Ŀ¼���ҳ����е�JAR��  
     */  
    private static final void loopFiles(File file, List<File> files) {   
        if (file.isDirectory()) {   
            File[] tmps = file.listFiles();   
            for (File tmp : tmps) {   
                loopFiles(tmp, files);   
            }   
        } else {   
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {   
                files.add(file);   
            }   
        }   
    }   
  
    /**  
     * <pre>  
     * ����JAR�ļ�  
     * </pre>  
     *  
     * @param file  
     */  
    public static final void loadJarFile(File file) {   
        try {   
            addURL.invoke(system, new Object[] { file.toURI().toURL() });   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
  
    /**  
     * <pre>  
     * ��һ��Ŀ¼��������JAR�ļ�  
     * </pre>  
     *  
     * @param path  
     */  
    public static final void loadJarPath(String path) {   
        List<File> files = new ArrayList<File>();   
        File lib = new File(path);   
        loopFiles(lib, files);   
        for (File file : files) {   
            loadJarFile(file);   
        }   
    }   
}

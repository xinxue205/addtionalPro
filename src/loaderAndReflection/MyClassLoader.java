package loaderAndReflection;

import java.io.*;

public class MyClassLoader extends ClassLoader{
	public static void main(String[] args) throws Exception {
		String srcPath = args[0];
		String destDir = args[1];
		FileInputStream fis = new FileInputStream(srcPath);
		String destFileName = srcPath.substring(srcPath.lastIndexOf("\\")+1);
		String destFilePath = destDir + "\\" + destFileName;
		FileOutputStream fos = new FileOutputStream(destFilePath);
		cypher(fis,fos);
		fis.close();
		fos.close();
	}

	private static void cypher(InputStream ips, OutputStream ops) throws IOException {
		int b = -1;
		while ((b=ips.read())!=-1){
			ops.write(b ^ 0xff);
		}
	}

	private String	classDir;
	
	@SuppressWarnings("deprecation")
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String classFilePath = classDir+"\\"+name+".class";
		try {
			FileInputStream fis = new FileInputStream(classFilePath);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			cypher(fis, bos);
			fis.close();
			byte[] bytes = bos.toByteArray();
			return defineClass(bytes, 0, bytes.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.findClass(name);
	}
	
	public MyClassLoader(){
		
	}
	
	public MyClassLoader(String classDir){
		this.classDir = classDir;
	}

	/**
	 * @param decryptedData
	 * @param string
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public Class loadClass(byte[] decryptedData, String string) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		super.loadClass(string, false);
		return null;
	}
}


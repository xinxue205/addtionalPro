package syncCompile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class Test {
	
	//使用jdk自带的rt.jar中的javax.tools包提供的编译器
	public void compiler1(){
		String javaAbsolutePath = "D:/test/AlTest1.java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, "-encoding", "UTF-8", "-classpath", javaAbsolutePath.toString(), javaAbsolutePath);
	}


	//使用Runtime执行javac命令
	public void compiler2(){
		String javaAbsolutePath = "D:/test/AlTest2.java";
		
		try {
			Process process = Runtime.getRuntime().exec("javac -classpath D:/test/ " + javaAbsolutePath);
			InputStream errorStream = process.getErrorStream();
			InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line=bufferedReader.readLine()) != null){
				System.out.println(line);
			}
			int exitVal = process.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	 
}


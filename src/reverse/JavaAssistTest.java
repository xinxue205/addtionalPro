package reverse;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
public class JavaAssistTest {     
	public static void main(String[] args) throws Exception { 

        //这个是得到反编译的池 

       ClassPool pool = ClassPool.getDefault(); 

       //取得�?��反编译的jar文件，设定路�?

       pool.insertClassPath("d:/sinobest-sjjh-worker-p.jar"); 

       //取得�?��反编译修改的文件，注意是完整路径 

       CtClass cc1 = pool.get("cn.sinobest.pe.engine.excutor.TaskExecutor"); 

       try { 

           //取得�?��修改的方�?

           CtMethod method = cc1.getDeclaredMethod("abortAllJobs"); 

          //插入修改项，我们让他直接返回(注意：根据方法的具体返回值返回，因为这个方法返回值是void，所以直接return�? 

//           method.insertBefore(""); 
           method.setBody("{if(true) return ;}");
           //写入保存 

           cc1.writeFile(); 

       } catch (NotFoundException e) { 

           e.printStackTrace(); 

      } 
}      
}

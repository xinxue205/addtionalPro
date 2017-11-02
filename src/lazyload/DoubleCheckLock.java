/**
 * 
 */
package lazyload;

import java.util.Vector;

/**
 * @author wxxx
 * @date 2015-1-30 ����4:35:44
 * @version 1.0  wxxx create
 * @CopyRight (c) 2015 xxxx��˾ 
 */
public class DoubleCheckLock {

}
//���ӵ�˫�ؼ������ȫ���
class LazySingleton {    
  private LazySingleton() {}    
  private static class FinalPlaceHolder {
    final LazySingleton singleton;    
    FinalPlaceHolder(LazySingleton s) {    
      this.singleton = s;    
    }    
  }    
  private static FinalPlaceHolder holder = null;    
  public static LazySingleton instance() {    
    if (holder == null) {    
      synchronized(LazySingleton.class) {    
        if (holder == null) {    
          holder = new FinalPlaceHolder(new LazySingleton());    
        }    
      }    
    }    
    return holder.singleton;    
  }
}

//���õ�˫�ؼ������ȫ�����������̬����ʵ����
class Singleton  
{  
	//private Vector v;  
	//private boolean inUse;  
	private static Singleton instance = new Singleton();  
	
	private Singleton()  
	{  
	//  v = new Vector();  
	//  inUse = true;  
	  //...  
	}  
	
	public static Singleton getInstance()  
	{  
	  return instance;  
	}  
}

//˫�ؼ������ȫ�������2
class Foo {  
	//ʹ�þ�̬�ڲ���, ֻ�е�������ʱ, ����Żᱻװ��  
	private static class LazyFoo {  
		public static Foo foo = new Foo();  
	}  
	  
	public static Foo getInstance() {  
		return LazyFoo.foo;  
	}  
}

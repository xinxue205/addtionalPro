/**
 * 
 */
package lazyload;

import java.util.Vector;

/**
 * @author wxxx
 * @date 2015-1-30 下午4:35:44
 * @version 1.0  wxxx create
 * @CopyRight (c) 2015 xxxx公司 
 */
public class DoubleCheckLock {

}
//复杂的双重检查锁安全解决
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

//常用的双重检查锁安全解决方法，静态参数实例化
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

//双重检查锁安全解决方法2
class Foo {  
	//使用静态内部类, 只有当有引用时, 该类才会被装载  
	private static class LazyFoo {  
		public static Foo foo = new Foo();  
	}  
	  
	public static Foo getInstance() {  
		return LazyFoo.foo;  
	}  
}

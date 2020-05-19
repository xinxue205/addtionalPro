/**
 * 
 */
package design.model.observe;

/**
 * @author wxx
 * @date 2015-3-24 下午9:55:33
 * @version 1.0  wxx create
 * @CopyRight (c) 2015 xxxx公司 
 */
public class Test
{
    public static void main(String[] args)
    {
        Watched girl = new ConcreteWatched();
        
        Watcher watcher1 = new ConcreteWatcher();
        Watcher watcher2 = new ConcreteWatcher();
        Watcher watcher3 = new ConcreteWatcher();
        
        girl.addWatcher(watcher1);
        girl.addWatcher(watcher2);
        girl.addWatcher(watcher3);
        
        girl.notifyWatchers("开心");
    }

}

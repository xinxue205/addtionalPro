/**
 * 
 */
package design.model.observe;

/**
 * @author wxx
 * @date 2015-3-24 下午9:54:39
 * @version 1.0  wxx create
 * @CopyRight (c) 2015 xxxx公司 
 */
import java.util.ArrayList;
import java.util.List;

public class ConcreteWatched implements Watched
{
    // 存放观察者
    private List<Watcher> list = new ArrayList<Watcher>();

    public void addWatcher(Watcher watcher)
    {
        list.add(watcher);
    }

    public void removeWatcher(Watcher watcher)
    {
        list.remove(watcher);
    }

    public void notifyWatchers(String str)
    {
        // 自动调用实际上是主题进行调用的
        for (Watcher watcher : list)
        {
            watcher.update(str);
        }
    }

}

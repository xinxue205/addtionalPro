/**
 * 
 */
package design.model.observe;

/**
 * @author wxx
 * @date 2015-3-24 ����9:54:39
 * @version 1.0  wxx create
 * @CopyRight (c) 2015 xxxx��˾ 
 */
import java.util.ArrayList;
import java.util.List;

public class ConcreteWatched implements Watched
{
    // ��Ź۲���
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
        // �Զ�����ʵ������������е��õ�
        for (Watcher watcher : list)
        {
            watcher.update(str);
        }
    }

}

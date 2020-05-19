/**
 * 
 */
package design.model.observe;

/**
 * @author wxx
 * @date 2015-3-24 ÏÂÎç9:52:57
 * @version 1.0  wxx create
 * @CopyRight (c) 2015 xxxx¹«Ë¾ 
 */
public interface Watched
{
    public void addWatcher(Watcher watcher);

    public void removeWatcher(Watcher watcher);

    public void notifyWatchers(String str);

}

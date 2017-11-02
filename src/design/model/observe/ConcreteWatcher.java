/**
 * 
 */
package design.model.observe;

/**
 * @author wxx
 * @date 2015-3-24 ÏÂÎç9:53:34
 * @version 1.0  wxx create
 * @CopyRight (c) 2015 xxxx¹«Ë¾ 
 */
public class ConcreteWatcher implements Watcher
{

    public void update(String str)
    {
        System.out.println(str);
    }

}
/**
 * 
 */
package listener;

/**
 * @author wuxinxue
 * @time 2015-7-1 上午9:01:00
 * @copyright hnisi
 */
public class Model { 

    //初始化50个监听器 
     private MouseListener[] listeners = new MouseListener[50]; 
    //用于记录当前listeners数组记录到了哪里 
     int index = 0; 

    public void addListener(MouseListener listener) { 
        //向这个model中注册一个监听器 
         listeners[index++] = listener; 
    } 

    public void clickModel() { 
        //调用注册进来所有的listener的click方法 
        for (int i = 0; i < index; i++) { 
            listeners[i].click(); 
        } 
    } 
}

interface MouseListener {
	void click();
}

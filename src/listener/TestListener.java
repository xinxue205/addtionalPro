/**
 * 
 */
package listener;

/**
 * @author wuxinxue
 * @time 2015-7-1 上午9:00:00
 * @copyright hnisi
 */
public class TestListener {
	//测试model 
    public static void main(String[] args) { 
       Model model = new Model(); 
       //添加一个匿名内部类,里面实现了click方法. 
        model.addListener(new MouseListener() { 

           public void click() { 
               System.out.println("我被点击了"); 
           } 
       }); 
       //再添加一个内部类. 
       model.addListener(new MouseListener() { 

           public void click() { 
               System.out.println("我被点击了"); 
           } 
       }); 

       //下面给model一个点击,在实际的JDK源代码中,也是这样子去调用Model中的响应方法的. 
       model.clickModel(); 
   }
}

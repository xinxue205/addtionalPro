/**
 * 
 */
package listener;

/**
 * @author wuxinxue
 * @time 2015-7-1 ����9:00:00
 * @copyright hnisi
 */
public class TestListener {
	//����model 
    public static void main(String[] args) { 
       Model model = new Model(); 
       //���һ�������ڲ���,����ʵ����click����. 
        model.addListener(new MouseListener() { 

           public void click() { 
               System.out.println("�ұ������"); 
           } 
       }); 
       //�����һ���ڲ���. 
       model.addListener(new MouseListener() { 

           public void click() { 
               System.out.println("�ұ������"); 
           } 
       }); 

       //�����modelһ�����,��ʵ�ʵ�JDKԴ������,Ҳ��������ȥ����Model�е���Ӧ������. 
       model.clickModel(); 
   }
}

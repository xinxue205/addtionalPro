/**
 * 
 */
package listener;

/**
 * @author wuxinxue
 * @time 2015-7-1 ����9:01:00
 * @copyright hnisi
 */
public class Model { 

    //��ʼ��50�������� 
     private MouseListener[] listeners = new MouseListener[50]; 
    //���ڼ�¼��ǰlisteners�����¼�������� 
     int index = 0; 

    public void addListener(MouseListener listener) { 
        //�����model��ע��һ�������� 
         listeners[index++] = listener; 
    } 

    public void clickModel() { 
        //����ע��������е�listener��click���� 
        for (int i = 0; i < index; i++) { 
            listeners[i].click(); 
        } 
    } 
}

interface MouseListener {
	void click();
}

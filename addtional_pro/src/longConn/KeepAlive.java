/**
 * 
 */
package longConn;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wuxinxue
 * @time 2015-7-13 ����10:49:35
 * @copyright hnisi
 */
public class KeepAlive {
	private static final long serialVersionUID = -2813120366138988480L;  
	  
    /* ���Ǹ÷����������ڲ���ʹ�á� 
     * @see java.lang.Object#toString() 
     */  
    @Override  
    public String toString() {  
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\tά�����Ӱ�";  
    }  
}

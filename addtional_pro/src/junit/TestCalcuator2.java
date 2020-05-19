/**
 * 
 */
package junit;

import junit.framework.TestCase;

/**
 * @author wuxinxue
 * @time 2015-7-6 обнГ2:25:33
 * @copyright hnisi
 */
public class TestCalcuator2 extends TestCase {
	public void testAdd(){ 
        Calcuator calcuator=new Calcuator(); 
        double result=calcuator.add(1,2); 
        assertEquals(3,result,0); 
    } 
}

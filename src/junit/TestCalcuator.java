/**
 * 
 */
package junit;

import junit.framework.TestCase;

/**
 * @author wuxinxue
 * @time 2015-7-6 обнГ2:23:25
 * @copyright hnisi
 */
public class TestCalcuator extends TestCase { 
    public void testAdd(){ 
        Calcuator calcuator=new Calcuator(); 
        double result=calcuator.add(1,2); 
        assertEquals(4,result,0); 
    } 

}

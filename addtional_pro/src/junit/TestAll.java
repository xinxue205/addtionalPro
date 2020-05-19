/**
 * 
 */
package junit;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author wuxinxue
 * @time 2015-7-6 обнГ2:24:34
 * @copyright hnisi
 */
public class TestAll extends TestSuite { 
    public static Test suite() { 
        TestSuite suite = new TestSuite("TestSuite Test"); 
        suite.addTestSuite(TestCalcuator.class); 
        suite.addTestSuite(TestCalcuator2.class); 
        return suite; 
    }
    
    public static void main(String args[]){ 
        TestRunner.run(suite()); 
    } 
}

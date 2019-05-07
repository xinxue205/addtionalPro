package powerMockito;

import java.io.File;  
import org.junit.Assert;  
import org.junit.Test;  
import org.powermock.api.mockito.PowerMockito;  
  
public class FlySunMockTest {  
    @Test  
    public void testCallArgumentInstance(){  
        //mock�����File����  
        File file = PowerMockito.mock(File.class);  
        PowerMockito.when(file.exists()).thenReturn(true);  
        FlySunDemo demo = new FlySunDemo();  
        Assert.assertTrue(demo.callArgumentInstance(file));  
    }  
}

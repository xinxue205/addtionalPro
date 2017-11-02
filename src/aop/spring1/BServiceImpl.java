package aop.spring1;

// π”√cglib  
public class BServiceImpl {  

 public void barB(String _msg, int _type) {  
     System.out.println("BServiceImpl.barB(msg:" + _msg + " type:" + _type + ")");  
     if (_type == 1)  
         throw new IllegalArgumentException("≤‚ ‘“Ï≥£");  
 }  

 public void fooB() {  
     System.out.println("BServiceImpl.fooB()");  
 }  

}
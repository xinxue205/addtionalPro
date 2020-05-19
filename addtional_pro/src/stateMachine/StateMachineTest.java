package stateMachine;

//≤‚ ‘¿‡  
public class StateMachineTest {  
  public static void main(String[] args) {  
       Machine machine=new Machine(10);  
       for(int i=0;i<11;i++){  
//		   System.out.println(machine);  
		   machine.insertQuerter();  
		   machine.turnCrank();  
       }  
  }  
}

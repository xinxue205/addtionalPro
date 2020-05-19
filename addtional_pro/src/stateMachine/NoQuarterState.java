package stateMachine;

/** 
 * 机器处于没有投硬币的状态 
 * @author seacean 
 * @date 2013-8-29 
 */  
public class NoQuarterState implements State {  
    private Machine machine;  
  
    public NoQuarterState(Machine machine) {  
        this.machine = machine;  
    }  
  
    @Override  
    public void insertQuarter() {  
        machine.setState(machine.getHasQuarterState());  
        System.out.println("a quarter is inserted, please turn the crank");  
    }  
  
    @Override  
    public void ejectQuarter() {  
        System.out.println("please insert a quarter!");  
    }  
  
    @Override  
    public void turnCrank() {  
        System.out.println("please insert a quarter!");  
    }  
  
    @Override  
    public void dispense() {  
    }  
  
}
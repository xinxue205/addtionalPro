package stateMachine;

/** 
 * 机器处于无糖果状态 
 * @author seacean 
 * @date 2013-8-29 
 */  
public class SoldOutState implements State {  
    private Machine machine;  
    public SoldOutState(Machine machine) {  
        this.machine=machine;  
    }  
  
    @Override  
    public void insertQuarter() {  
        System.out.println("Sorry, there is no gumball in the machine!");  
    }  
  
    @Override  
    public void ejectQuarter() {  
        System.out.println("Sorry, there is no gumball in sold!");  
    }  
  
    @Override  
    public void turnCrank() {  
        System.out.println("Sorry, there is no gumball! Turn is no meaning.");  
        machine.setState(machine.getNoQuarterState());  
    }  
  
    @Override  
    public void dispense() {  
        System.out.println("Sorry, there is no gumball!");  
    }  
  
} 

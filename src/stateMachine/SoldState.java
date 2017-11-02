package stateMachine;

/** 
 * 机器正在出售糖果的状态 
 *  
 * @author seacean 
 * @date 2013-8-29 
 */  
public class SoldState implements State {  
    private Machine machine;  
  
    public SoldState(Machine machine) {  
        this.machine = machine;  
    }  
  
    @Override  
    public void insertQuarter() {  
        System.out.println("please wait,we are already giving you a gumball!");  
    }  
  
    @Override  
    public void ejectQuarter() {  
        System.out.println("Sorry, you have turned the crank!");  
    }  
  
    @Override  
    public void turnCrank() {  
        System.out.println("Turning twice does not give you another gumball!");  
    }  
  
    @Override  
    public void dispense() {  
        machine.releaseBall();  
        if (machine.getCount() > 0) {  
            machine.setState(machine.getNoQuarterState());  
        } else {  
            System.out.println("Out of Gumballs!");  
            machine.setState(machine.getSoldOutState());  
        }  
    }  
  
}

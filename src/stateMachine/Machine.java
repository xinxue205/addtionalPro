package stateMachine;

/** 
 * �����࣬��������״̬���������� 
 * @author seacean 
 * @date 2013-8-29 
 */  
public class Machine {  
    //��������������е�״̬��  
    private State soldOutState;  
    private State noQuarterState;  
    private State hasQuarterState;  
    private State soldState;  
  
    private State state; //�����ĵ�ǰ״̬  
    private int count = 0;//�����е�ǰ�ǹ�������  
    /** 
     * ��ʼ���������������е�״̬������ʼ���ǹ���������ʼ������״̬ 
     * @param count 
     */  
    public Machine(int count) {  
        this.soldOutState = new SoldOutState(this);  
        this.noQuarterState = new NoQuarterState(this);  
        this.hasQuarterState = new HasQuarterState(this);  
        this.soldState = new SoldState(this);  
        this.count = count;  
        if (this.count > 0) {  
            this.state = noQuarterState;  
        }
        System.out.println("the machine is ready, can insert quarter now!");  
    }  
    /** 
     * �ͷ��ǹ�ʱ���ڲ�������� 
     */  
    public void releaseBall() {  
        System.out.println("a gumball comes rolling out the solt...");  
        if (count > 0) {  
            count -= 1;  
        }  
    }  
      
    public void insertQuerter() {  
        state.insertQuarter();//����Ӳ��  
    }  
  
    public void ejectQuarter() {  
        state.ejectQuarter();  
    }  
  
    public void turnCrank() {  
        state.turnCrank();  
        state.dispense();  
    }  
  
    public State getSoldOutState() {  
        return soldOutState;  
    }  
  
    public State getNoQuarterState() {  
        return noQuarterState;  
    }  
  
    public State getHasQuarterState() {  
        return hasQuarterState;  
    }  
  
    public State getSoldState() {  
        return soldState;  
    }  
  
    public State getState() {  
        return state;  
    }  
  
    public int getCount() {  
        return count;  
    }  
  
    public void setState(State state) {  
        this.state = state;  
    }  
}

package stateMachine;

/** 
 * ״̬���ӿ� 
 * @author seacean 
 * @date 2013-8-29 
 */  
public interface State {  
    /** 
     * Ͷ��Ӳ�� 
     */  
    void insertQuarter();  
    /** 
     * ����ҡ�����������ҡ����������ش��������ͷ��ǹ� 
     */  
    void ejectQuarter();  
    /** 
     * ת��ҡ�� 
     */  
    void turnCrank();  
    /** 
     * �����ų��ǹ�����������ڲ�״̬�����س�ʼ��Ͷ��״̬ 
     */  
    void dispense();  
}  

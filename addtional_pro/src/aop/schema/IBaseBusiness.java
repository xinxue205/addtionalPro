package aop.schema;

/**
 * ������ӿڣ�Ҳ��ҵ����ӿ�<br>
 * 
 * ���ýӿڵķ�ʽ��spring aop ��Ĭ��ͨ��jdk ��̬������ʵ�ִ�����<br>
 * �����ýӿڣ���spring aop ��ͨ��cglib ��ʵ�ִ�����
 * 
 * @author yanbin
 * 
 */
public interface IBaseBusiness {

    /**
     * �������������㷽��
     * 
     * @param obj
     * @return
     */
    public String delete(String obj);

    /**
     * �ⷽ������������
     * 
     * @param obj
     * @return
     */
    public String add(String obj);

    /**
     * �ⷽ���в����أ���������
     * 
     * @param obj
     * @return
     */
    public String modify(String obj);

}

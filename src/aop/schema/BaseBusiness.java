package aop.schema;

/**
 * ҵ���࣬Ҳ��Ŀ�����
 * 
 * @author yanbin
 * 
 */
public class BaseBusiness implements IBaseBusiness {

    /**
     * �����
     */
    public String delete(String obj) {
        System.out.println("==========��������㣺" + obj + "˵�����ɾ���ң�===========\n");
        return obj + "���顫";
    }

    public String add(String obj) {
        System.out.println("================����������ܱ��С�����============== \n");
        return obj + "���顫 �ٺ٣�";
    }

    public String modify(String obj) {
        System.out.println("=================���Ҳ���ü����а�====================\n");
        return obj + "������鰡��";
    }

}

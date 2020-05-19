package aop.schema1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ����һ������
 * 
 * @author yanbin
 * 
 */
public class AspectAdvice {

    /**
     * ǰ��֪ͨ
     * 
     * @param jp
     */
    public void doBefore(JoinPoint jp) {
        System.out.println("===========����before advice============ \n");

        System.out.print("׼����" + jp.getTarget().getClass() + "��������");
        System.out.print(jp.getSignature().getName() + "�������ж� '");
        System.out.print(jp.getArgs()[0] + "'����ɾ����\n\n");

        System.out.println("Ҫ��������㷽���� \n");
    }

    /**
     * ����֪ͨ
     * 
     * @param jp
     *            ���ӵ�
     * @param result
     *            ����ֵ
     */
    public void doAfter(JoinPoint jp, String result) {
        System.out.println("==========����after advice=========== \n");
        System.out.println("����㷽��ִ������ \n");

        System.out.print(jp.getArgs()[0] + "��");
        System.out.print(jp.getTarget().getClass() + "�����ϱ�");
        System.out.print(jp.getSignature().getName() + "����ɾ����");
        System.out.print("ֻ���£�" + result + "\n\n");
    }

    /**
     * ����֪ͨ
     * 
     * @param pjp
     *            ���ӵ�
     */
    public void doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===========����around���Ʒ�����=========== \n");

        // ����Ŀ�귽��֮ǰִ�еĶ���
        System.out.println("���÷���֮ǰ: ִ�У�\n");

        // ���÷����Ĳ���
        Object[] args = pjp.getArgs();
        // ���õķ�����
        String method = pjp.getSignature().getName();
        // ��ȡĿ�����
        Object target = pjp.getTarget();
        // ִ���귽���ķ���ֵ������proceed()�������ͻᴥ������㷽��ִ��
        Object result = pjp.proceed();

        System.out.println("�����" + args[0] + ";" + method + ";" + target + ";" + result + "\n");
        System.out.println("���÷���������֮��ִ�У�\n");
    }

    /**
     * �쳣֪ͨ
     * 
     * @param jp
     * @param e
     */
    public void doThrow(JoinPoint jp, Throwable e) {
        System.out.println("ɾ��������");
    }

}

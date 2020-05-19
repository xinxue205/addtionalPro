package java8;

import java.util.function.Function;
 
//�������ã�һ��������Ϊ����������һ��Ŀ�귽����
public class MethodRefTest2 {
	/* 
    ���������������һ��int���Ͳ���a,����a+1,����������˵�Ľ���һ������,����һ��ֵ
    ��������������ͷ���Function�ӿڵĶ���,��Ҫ��ô����,���������� 
*/
public static final int addOne(int a){
    return a+1;
}

public static final int addTwo(String a){
    return Integer.parseInt(a)+2;
}

/* 
    *�÷����ڶ�����������һ��function���͵���Ϊ,Ȼ�����apply����aִ�������Ϊ
    *Function�Ƿ����壬2��������������ָ������ֵ���ͺ����ֵ����
*/
public static int oper(String a, Function<String,Integer> action){
	//���÷���
    return action.apply(a);
}

/* ����������oper����,��addOne������Ϊ�������� */
public static void main(String[] args){
    String x = "1";
    int y = oper(x, (c) -> addTwo(c));//������Ի��ɷ������õ�д�� int y = oper(x,Operation::addOne)
    System.out.printf("x= %s, y = %s\n", x, y); // ��ӡ��� x=1, y=2
    
    /* ��Ȼ��Ҳ����ʹ��lambda���ʽ����ʾ�����Ϊ,ֻҪ��֤һ������,һ������ֵ����ƥ�� */
     y = oper(x, c -> Integer.parseInt(c) + 3); // y = 4
     System.out.printf("x= %s, y = %s\n", x, y);

     y = oper(x, c -> Integer.parseInt(c) * 3 ); // y = 3    
     System.out.printf("x= %s, y = %s\n", x, y);

}
}

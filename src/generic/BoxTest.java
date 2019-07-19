package generic;

/**
 * Generic version of the Box class.
 * @param <T> the type of the value being boxed
 * ʹ�÷��������ºô���
 * ����ʱǿ���ͼ��
 * �����ֶ���������ת��
 * ����ʵ�ָ��ã���дͨ���㷨
 */
public class BoxTest{
	
	public static void main(String[] args) {
		Box<Integer> integerBox = new Box<Integer>();
		Box<String> stringBox = new Box<String>();
		
		integerBox.set(new Integer(10));
		stringBox.set(new String("���Ͳ���"));
		
		System.out.printf("����ֵΪ :%d\n\n", integerBox.get());
		System.out.printf("�ַ���Ϊ :%s\n", stringBox.get());
	}
}

class Box<T> {
    // T stands for "Type"
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}


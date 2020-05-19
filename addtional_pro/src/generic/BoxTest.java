package generic;

/**
 * Generic version of the Box class.
 * @param <T> the type of the value being boxed
 * 使用泛型有以下好处：
 * 编译时强类型检查
 * 无需手动进行类型转换
 * 可以实现复用，编写通用算法
 */
public class BoxTest{
	
	public static void main(String[] args) {
		Box<Integer> integerBox = new Box<Integer>();
		Box<String> stringBox = new Box<String>();
		
		integerBox.set(new Integer(10));
		stringBox.set(new String("泛型测试"));
		
		System.out.printf("整型值为 :%d\n\n", integerBox.get());
		System.out.printf("字符串为 :%s\n", stringBox.get());
	}
}

class Box<T> {
    // T stands for "Type"
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}


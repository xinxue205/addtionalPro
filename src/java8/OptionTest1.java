package java8;

import java.util.Optional;

public class OptionTest1 {
   public static void main(String args[]){
	   Optional<User> emptyOpt = Optional.empty();//对象可能包含值，也可能为空。你可以使用同名方法创建一个空的 Optional。
//	   System.out.println(emptyOpt.get());;//尝试访问 emptyOpt 变量的值会导致 NoSuchElementException。
	   
	   //可以使用 of() 和 ofNullable() 方法创建包含值的 Optional
	   User user = null;
//	   emptyOpt = Optional.of(user);//把 null 值作为参数传递进去，of() 方法会抛出 NullPointerException,所以，只有明确对象不为 null时才使用 of()。
	   emptyOpt = Optional.ofNullable(user);//对象即可能是 null 也可能是非 null，你就应该使用 ofNullable() 方法
//	   System.out.println(emptyOpt.get());//尝试访问null optional的值会导致 NoSuchElementException。

	   User user1 = new User("john@gmail.com", "1234");
	   Optional<User> opt = Optional.ofNullable(user1);
	   if(opt.isPresent()) {//首先验证是否有值
		   System.out.println(opt.get().getEmail());
	   }
	   
	   opt.ifPresent( u ->  System.out.println(u.getEmail()));//lambda表达式方式

	   
	   User user3 = null;
	    User user2 = new User("anna@gmail.com", "1234");
	    User result = Optional.ofNullable(user3).orElse(user2);//orElse()，如果有值则返回该值，否则返回传递的参数值;
	    System.out.println(result.getEmail());


//	   User user = new User("john@gmail.com", "1234");
//	   Optional<User> opt = Optional.ofNullable(user);
	    
   }
	      
}

class User{
	String email;
	public User(String email, String info) {
		this.email = email;
		this.info = info;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	String info;
	
	
}
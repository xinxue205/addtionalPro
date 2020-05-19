package container;

import java.util.*;

public class TestIterator {
	public static void main(String[] args) {
		Collection c = new HashSet();
		c.add(new Name("f1","l1"));
		c.add(new Name("f2","l2"));
		c.add(new Name("f3","l3"));
		Iterator i = c.iterator();
		while(i.hasNext()){
			Name n = (Name)i.next();
			System.out.println(n.getName());
		}
		
	}
}

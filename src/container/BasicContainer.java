package container;

import java.util.*;

public class BasicContainer {
	public static void main1(String[] args) {
		Collection<Comparable> c = new HashSet<Comparable>();
		c.add("hello");
		c.add(new Name("f1", "l1"));
		c.add(new Integer(100));
		System.out.println(c.remove("hello"));;
		System.out.println(c.remove(new Integer(100)));;
		//System.out.println(c.remove(new Name("f1", "l1")));
		System.out.println(c);
	}
	
	public static void main(String[] args) {
		Name n1 = new Name("", "");
		Name n2 = new Name("", "");
		System.out.println(n1.hashCode());
		System.out.println(n2.hashCode());
	}
}

class Name implements Comparable<Object>{
	private String firstName, lastName;
	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		// TODO Auto-generated constructor stub
	}
	public Name() {
		new Name("", "");
	}
	public boolean equals(Object obj) {
		if (obj instanceof Name) {
			Name name = (Name)obj;
			return (firstName.equals(name.firstName)) && (lastName.equals(name.lastName));
		}
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return lastName.hashCode();
	}
	public String getName() {
		// TODO Auto-generated method stub
		return firstName+" "+lastName;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return firstName+" "+lastName;
	}
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Name n = (Name)o;
		int lastCmp = lastName.compareTo(n.lastName);
		return (lastCmp != 0 ? lastCmp : firstName.compareTo(firstName));
	}
}

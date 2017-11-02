package container;

import java.util.*;

public class TestArgsWords {
	private static final Integer ONE = 1;
	public static void main(String[] args) {
		Map m = new HashMap();
		for (int i=0; i<args.length; i++){
			Integer freq = (Integer)m.get(args[i]);
			//m.put(args[i], (freq == null ? ONE : new Integer(freq.intValue()+1) ));
			m.put(args[i], (freq == null ? ONE : freq+1 ));
		}
		System.out.println(m.size() + " distinct words were detected:");
		System.out.println(m);
	}
}

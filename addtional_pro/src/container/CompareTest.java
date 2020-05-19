package container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompareTest {
	
	public static void main(String[] args) {
		int maxSize = 3;
		Map<String, CompareThing> olds = new HashMap<String, CompareThing>();
		Date date1 = new Date();
		Date date2 = new Date(date1.getTime() + 100000);
		Date date3 = new Date(Long.MAX_VALUE);
		olds.put("111", new CompareThing("111", date1));
		olds.put("222", new CompareThing("222", date2));
		olds.put("333", new CompareThing("333", date3));
		olds.put("444", new CompareThing("444", null));
		
		List<CompareThing> all = new ArrayList<CompareThing>( olds.values() );
		Collections.sort( all, new Comparator<CompareThing>() {
			@Override
			public int compare( CompareThing o1, CompareThing o2 ) {
				if ( ( o1 == null ) && ( o2 != null ) ) {
					return -1;
				}
				if ( ( o1 != null ) && ( o2 == null ) ) {
					return 1;
				}
				if ( ( o1 == null ) && ( o2 == null ) ) {
					return 0;
				}
				if ( o1.getRegistrationDate() == null && o2.getRegistrationDate() != null ) {
					return -1;
				}
				if ( o1.getRegistrationDate() != null && o2.getRegistrationDate() == null ) {
					return 1;
				}
				if ( o1.getRegistrationDate() == null && o2.getRegistrationDate() == null ) {
					return 0;
				}
				return ( o1.getRegistrationDate().compareTo( o2.getRegistrationDate() ) );
			}
		} );
		
		int cutCount = maxSize < 5 ? maxSize : 5;
        for ( int i = 0; i < cutCount; i++ ) {
        	CompareThing toRemove = all.get( i );
        	olds.remove( toRemove.name );
        }
		
        Set keys = olds.entrySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			CompareThing thing = olds.get(key);
			System.out.println(thing.name+""+thing.registrationDate);

		}
	}
	
	
}

class CompareThing{
	String name;
	Date registrationDate;

	public CompareThing(String name, Date registrationDate) {
		this.name = name;
		this.registrationDate = registrationDate;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}
	
}

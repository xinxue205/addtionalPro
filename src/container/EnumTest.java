/**
 * 
 */
package container;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-11 下午5:06:26
 * @Description
 * @version 1.0 Shawn create
 */
public class EnumTest {
	public static void main(String[] args) {
		EnumDomain domain = EnumDomain.ALL_OTHER;
		switch(domain){
	        case ALL_HTML: {
	        	System.out.println("ALL_HTML");
	        	break;
	        }
	        default : {
	        	//分页的HTML
	        	System.out.println("other");
	        	break;
	        }
		}
		
		/*if(domain.equals(EnumDomain.ALL_EXCEL)){
			System.out.println("ALL_EXCEL");
		} else {
			System.out.println("other_type");
		}*/
	}
}

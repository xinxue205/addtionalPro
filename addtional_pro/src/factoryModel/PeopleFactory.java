/**
 * 
 */
package factoryModel;

/**
 * @author wuxinxue
 * @time 2015-6-15 ионГ9:21:11
 * @copyright hnisi
 */
public class PeopleFactory{

    public static People create(int type){
    	People p = null;
         if(type==1){

             p = new Chinese(); 

         }else if(type==2){

        	 p = new American(); 

         }
		return p;
   }

}



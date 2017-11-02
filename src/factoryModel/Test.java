/**
 * 
 */
package factoryModel;

/**
 * @author wuxinxue
 * @time 2015-6-15 ионГ9:23:00
 * @copyright hnisi
 */
public class Test{

    public static void main(String []args){

         People p=PeopleFactory.create(1);

         p.say();

         p=PeopleFactory.create(2);

         p.say();

   }

}



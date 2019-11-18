package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
 
public class TestHibernate {
 
    public static void main(String[] args) {
 
        //得到Configuration实例
        Configuration configuration=new Configuration().configure("hibernate/hibernate.cfg.xml");
 
        //利用Configuration实例的方法得到SessionFactory
        StandardServiceRegistry build = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory factory=configuration.buildSessionFactory( build);
 
//        StandardServiceRegistryBuilder stdr=new StandardServiceRegistryBuilder();
//        SessionFactory sessionFactory =configuration.buildSessionFactory(stdr.applySettings(configuration.getProperties()).build());
        //得到session
//        Session session=sessionFactory.getCurrentSession();
        
//        利用session开启一个事务
//        session.getTransaction().begin();
 
        //插入一条学生信息
//        Student student=new Student();
//        student.setStuName("x");
//        student.setStuSex("cc");
//        session.save(student);
//        session.getTransaction().commit();
 
        //查询学生表的信息
//        ManageEmployee ME = new ManageEmployee();
        Session session = factory.openSession();
        
        try{
        	Transaction tx = session.beginTransaction();
        	Employee employee = new Employee("Zara1", "Ali1", 11000);
        	System.out.println( (Integer) session.save(employee)); 
        	tx.commit();
        }catch (HibernateException e) {
           e.printStackTrace(); 
        }finally {
           session.close(); 
           factory.close();
        }
        
        
//        Transaction  tx = session.beginTransaction();
//        List employees = session.createQuery("FROM Employee").list(); 
//        for (Iterator iterator = employees.iterator(); iterator.hasNext();){
//           Employee employee = (Employee) iterator.next(); 
//           System.out.print("First Name: " + employee.getFirstName()); 
//           System.out.print("  Last Name: " + employee.getLastName()); 
//           System.out.println("  Salary: " + employee.getSalary()); 
//        }
//        tx.commit();

        /* Add few employee records in database */
//        Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
//        Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
//        Integer empID3 = ME.addEmployee("John", "Paul", 10000);
//
//        /* List down all the employees */
//        ME.listEmployees();
//
//        /* Update employee's records */
//        ME.updateEmployee(empID1, 5000);
//
//        /* Delete an employee from the database */
//        ME.deleteEmployee(empID2);
//
//        /* List down new list of the employees */
//        ME.listEmployees();
 
    }
}

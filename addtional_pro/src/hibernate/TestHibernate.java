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
 
        //�õ�Configurationʵ��
        Configuration configuration=new Configuration().configure("hibernate/hibernate.cfg.xml");
 
        //����Configurationʵ���ķ����õ�SessionFactory
        StandardServiceRegistry build = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory factory=configuration.buildSessionFactory( build);
 
//        StandardServiceRegistryBuilder stdr=new StandardServiceRegistryBuilder();
//        SessionFactory sessionFactory =configuration.buildSessionFactory(stdr.applySettings(configuration.getProperties()).build());
        //�õ�session
//        Session session=sessionFactory.getCurrentSession();
        
//        ����session����һ������
//        session.getTransaction().begin();
 
        //����һ��ѧ����Ϣ
//        Student student=new Student();
//        student.setStuName("x");
//        student.setStuSex("cc");
//        session.save(student);
//        session.getTransaction().commit();
 
        //��ѯѧ�������Ϣ
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

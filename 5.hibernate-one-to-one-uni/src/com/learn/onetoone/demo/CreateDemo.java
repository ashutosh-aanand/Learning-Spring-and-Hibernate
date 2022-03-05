package com.learn.onetoone.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetoone.entity.Instructor;
import com.learn.onetoone.entity.InstructorDetail;

// 1.
public class CreateDemo {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			
			// 1. Create the objects
			Instructor tempInstructor = 
					new Instructor("Chad", "Dayby", "cd@gmail.com");
			
			InstructorDetail tempInstructorDetail = 
					new InstructorDetail("https://www.youtube.com/user/luv2codetv", "coding, teaching");
			
			// 2. Associate the objects
			tempInstructor.setInstructorDetail(tempInstructorDetail);
			
			System.out.println(tempInstructor);
			
			// start a transaction ------------------------------------------------------
			session.beginTransaction();
			
			// 3. Save the instructor
			// Note: this will also save the details object
			// because of CascadeType.ALL
			session.save(tempInstructor);
			
			// commit transaction --------------------------------------------------------
			session.getTransaction().commit();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			factory.close();
		}
	}

}

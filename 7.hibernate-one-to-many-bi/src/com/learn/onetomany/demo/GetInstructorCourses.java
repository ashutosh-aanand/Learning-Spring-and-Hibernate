package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;

// 3.
public class GetInstructorCourses {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Course.class)
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {			
			// start a transaction ------------------------------------------------------
			session.beginTransaction();

			// get the instructor form db
			int id = 1;
			Instructor tempInstructor = session.get(Instructor.class, id);
			
			System.out.println("\nInstructor: \n" + tempInstructor);
			
			// get courses for the instructor
			// (to do this first make sure to add getter method for it in the Instructor class)
			System.out.println("\nCourses: \n" + tempInstructor.getCourses());

			// commit transaction --------------------------------------------------------
			session.getTransaction().commit();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			// cleanup code
			session.close();
			factory.close();
		}
		
	}

}

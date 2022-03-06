package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;

public class EagerDemo {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Course.class)
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			/*
			 * BEFORE TESTING:
			 * ----------------
			 * In Instructor entity, change the fetch type for Courses to "EAGER"
			 * -----------------------------------------------------------------
			 */
			
			// start a transaction ------------------------------------------------------
			session.beginTransaction();

			// get the instructor form db
			int id = 1;
			Instructor tempInstructor = session.get(Instructor.class, id);
			// here it will load everything Instructor, InstructorDetail and Courses completely
			// No need to hit the db again for these things
			// we can just use tempInstructor.get...(), and the required data will be retrieved
			// from the memory itself
			
			System.out.println("\nInstructor: \n" + tempInstructor);
			
			// get courses for the instructor
			System.out.println("\nCourses: \n" + tempInstructor.getCourses());

			// commit transaction --------------------------------------------------------
			session.getTransaction().commit();
			
			System.out.println("Done !");
			
			
			// This works even outside the session
			System.out.println("\nOutside session: \n" + tempInstructor.getCourses());
			// here the courses are fetched from the memory, NOT DB
			
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

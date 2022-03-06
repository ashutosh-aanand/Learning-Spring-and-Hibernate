package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;

public class LazyDemo {

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
			 * In Instructor entity, change the fetch type for Courses to "LAZY"
			 * -----------------------------------------------------------------
			 */
			
			// start a transaction ------------------------------------------------------
			session.beginTransaction();

			// get the instructor form db
			int id = 1;
			Instructor tempInstructor = session.get(Instructor.class, id);
			// here it will load everything Instructor, InstructorDetail only
			// as Courses is set to LAZY loading
			
			System.out.println("\nInstructor: \n" + tempInstructor);
			
			// get courses for the instructor
//			System.out.println("\nCourses: \n" + tempInstructor.getCourses());
			// Here it will again make call to db and fetch Courses Lazily

			// commit transaction --------------------------------------------------------
			session.getTransaction().commit();
			
			System.out.println("Done !");
			
			
			// This will work here only if the courses have already been fetched
			// inside the session
			System.out.println("\nCourses: \n" + tempInstructor.getCourses());
			// comment the code inside session which fetches course, and check.
			
			// It gives an error
			// org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: 
			// com.learn.onetomany.entity.Instructor.courses, could not initialize proxy - no Session
			
			// lets solve this 
			/*
			 * m1) call the .getCourses() method inside the session once.
			 * Then, it will be saved in memory and accessible outside the session
			 * using the .getCourses() method itself.
			 * We already discussed it earlier.
			 * 
			 * m2) Query using HQL
			 * lets discuss this seperately in another file
			 */
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

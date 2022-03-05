package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;

// 4.
public class DeleteCourse {

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

			// get the course form db
			int id = 10;
			Course tempCourse = session.get(Course.class, id);
			
			// delete the course
			System.out.println("Deleting course: \n" + tempCourse);
			session.delete(tempCourse);
			
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

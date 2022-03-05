package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;

// 2.
public class CreateCourses {

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
			
			// create some courses
			Course c1 = new Course("Balanced diet");
			Course c2 = new Course("Yoga for beginners");
			
			// add courses to instructor using addCourse method
			tempInstructor.addCourse(c1);
			tempInstructor.addCourse(c2);
			
			// save the courses
			session.save(c1);
			session.save(c2);

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

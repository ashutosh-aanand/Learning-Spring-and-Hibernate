package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;
import com.learn.onetomany.entity.Review;

public class DeleteCourseAndReviews {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Course.class)
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.addAnnotatedClass(Review.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {			
			// start a transaction ------------------------------------------------------
			session.beginTransaction();
			
			// get course with given id
			int id = 10;
			Course tempCourse = session.get(Course.class, id);
			// will fetch only Course and NOT its reviews (due to lazy loading)
			
			// delete the course
			System.out.println("Deleting Course: " + tempCourse +"\n");
			
			session.delete(tempCourse);
			
			// this will also delete all the associated reviews due to "cascading"
			
			// its working. checked in the db as well. Deletions were as expected.
			
			// commit transaction --------------------------------------------------------
			session.getTransaction().commit();
			
			System.out.println("Done !");
			
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

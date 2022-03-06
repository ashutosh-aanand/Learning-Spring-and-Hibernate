package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;
import com.learn.onetomany.entity.Review;

public class GetCourseAndReviews {

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
			
			System.out.println("Course: \n" + tempCourse);
			
			// get its reviews
			System.out.println("Reviews are: \n" + tempCourse.getReviews());

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

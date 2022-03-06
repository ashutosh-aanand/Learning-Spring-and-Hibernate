package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;
import com.learn.onetomany.entity.Review;

public class CreateCourseAndReviews {

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
			
			// create a course
			Course tempCourse = new Course("Learn guitar in 30 days");
			
			// add some reviews
			tempCourse.addReview(new Review("Thank you for making such a nice course."));
			tempCourse.addReview(new Review("Cool job, well done."));
			tempCourse.addReview(new Review("Average course for me."));
			
			// save the course .. and for saving the reviews leverage
			// the cascade.ALL to save added reviews automatically
			System.out.println("Saving the course: \n" + tempCourse);
			System.out.println("Reviews: \n" + tempCourse.getReviews());
			
			session.save(tempCourse);

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

package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;

// 1.
public class CreateInstructor {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Course.class)
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			Instructor tempInstructor = 
					new Instructor("Vid", "Jam", "vj@gmail.com");
			
			InstructorDetail tempInstructorDetail = 
					new InstructorDetail("https://www.youtube.com/", "fitness, lifestyle, martial arts");
			
			tempInstructor.setInstructorDetail(tempInstructorDetail);
			
			System.out.println(tempInstructor);
			
			// start a transaction ------------------------------------------------------
			session.beginTransaction();
			
			session.save(tempInstructor);
			
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

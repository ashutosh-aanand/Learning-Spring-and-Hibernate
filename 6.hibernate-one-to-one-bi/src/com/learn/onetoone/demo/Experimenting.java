package com.learn.onetoone.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetoone.entity.Instructor;
import com.learn.onetoone.entity.InstructorDetail;

public class Experimenting {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			
			/*
			 * 1. Create a instructor
			 * add its detail
			 * replace its detail with new detail object
			 * check if the original detail got replaced or not
			 */
			
			session.beginTransaction();
			
//			List<Instructor> instructors = session
//											.createQuery("FROM Instructor")
//											.getResultList();
//			for(Instructor i: instructors) {
//				System.out.println(i);
//			}
			
//			Instructor i1 = session.get(Instructor.class, 1);
//			
//			System.out.println(i1);
//			
//			InstructorDetail d1 = new InstructorDetail("www.youtube.com", "painting, skating");
//			
//			i1.setInstructorDetail(d1);
			
//			InstructorDetail id1 = session.get(InstructorDetail.class, 4);
//			
//			session.delete(id1);
			
			/*
			 * no, its not replacing. rather it is adding a new detail object to the details table
			 * and updating the reference in the instructor table
			 * and the previous detail is as it is.
			 * 
			 * This might be happening due to bi-directional mapping as here the detail can exist alone
			 * Try it in unidirectional and check whats happening.
			 */
			
			
			
			session.getTransaction().commit();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
			factory.close();
		}
		
	}

}

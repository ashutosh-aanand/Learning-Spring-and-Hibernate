package com.learn.onetoone.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetoone.entity.Instructor;
import com.learn.onetoone.entity.InstructorDetail;

public class GetInstructorFromInstructorDetail {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			
			/*
			 * As we have added mappedBy=instructorDetail to InstructorDetail,
			 * lets check if we can get Instructor from InstructorDetail object.
			 */
			
			session.beginTransaction();
			
			// get and print InstructorDetail with given id
			int id = 6;
			InstructorDetail detail = session.get(InstructorDetail.class, id);
			System.out.println(detail);
						
			// print associated Instructor
			if(detail!=null) {
				System.out.println("Associated Instructor is: \n" + detail.getInstructor());
			} else {
				System.out.println("Instructor detail with id : " + id + " NOT FOUND !!");
			}			
			
			session.getTransaction().commit();
			
			// working fine
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			// handle connection leak issue
			session.close();
			
			factory.close();
		}
		
	}

}

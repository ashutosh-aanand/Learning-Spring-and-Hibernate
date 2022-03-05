package com.learn.onetoone.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetoone.entity.Instructor;
import com.learn.onetoone.entity.InstructorDetail;

// 2.
public class DeleteDemo {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			// start a transaction
			session.beginTransaction();
			
//			int id = 3;
//			InstructorDetail detail = session.get(InstructorDetail.class, id);
//			
//			System.out.println(detail);
//			
//			session.delete(detail);
//			
//			we can't modify(update/delete) rows in instructor_details table directly.
			
//			We can only modify the instructor table
//			and can use the references to fetch the row of instructor_details table
//			modify it, and then set it to its parent row in the instructor table and
//			thats how it will get updated.
			
			// Delete Instructor with id = 3
			
			// 1. get the instructor
			int id = 3;
			Instructor instructor = session.get(Instructor.class, id);
			
			System.out.println(instructor);
			
			// 2. delete the instructor
			if(instructor!=null) {
				System.out.println("deleting: " + instructor);
				
				// NOTE: this will also delete the associated entry in "instructor_details" table
				// because of CascadeType.ALL	
				session.delete(instructor);
			}
			
			// commit transaction
			session.getTransaction().commit();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			factory.close();
		}
		
	}

}

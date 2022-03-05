package com.learn.onetoone.demo;

import javax.persistence.CascadeType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.onetoone.entity.Instructor;
import com.learn.onetoone.entity.InstructorDetail;

public class DeleteInstructorDetails {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Instructor.class)
									.addAnnotatedClass(InstructorDetail.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			
			session.beginTransaction();
			
			// get and print InstructorDetail with given id
			int id = 7;
			InstructorDetail detail = session.get(InstructorDetail.class, id);
			System.out.println(detail);
			
			// print associated Instructor
			if(detail!=null) {
				System.out.println("Associated Instructor is: \n" + detail.getInstructor());
			} else {
				System.out.println("Instructor detail with id : " + id + " NOT FOUND !!");
			}
			
			/*
			 * Since we have use cascade=CascadeType.ALL
			 * now lets see if deleting detail deletes instructor as well
			 */
			
//			int instructor_id = detail.getInstructor().getId();
//			
//			// delete the InstructorDetail
//			System.out.println("Deleting detail: " + detail);
//			if(detail!=null)
//				session.delete(detail);
//			
//			// Check if the associated instructor is deleted as well
//			Instructor instructor = session.get(Instructor.class, instructor_id);
//			
//			if(instructor==null) {
//				System.out.println("The instructor was also deleted !");
//			} else {
//				System.out.println("The isntructor wasn't deleted");
//			}
			
			/*
			 * Yes, currently deleting the details deletes the instructor as well.
			 * 
			 * Lets fix that.
			 * Lets configure the cascade for Instructor in InstructorDetail
			 * and add all types except CascadeType.DELETE.
			 * 
			 * Now, we will be able to delete the details alone.
			 * ------------------
			 * 
			 * But, now its throwing an error:
			 * deleted object would be re-saved by cascade (remove deleted object from associations)
			 * : [com.learn.onetoone.entity.InstructorDetail#7]
			 * 
			 * So, we need to remove the reference to InstructorDetail (which we are deleting) form the Instructor table
			 * and set it to null
			 * 
			 */
			
			int instructor_id = detail.getInstructor().getId();
			
			// delete the InstructorDetail
			System.out.println("Deleting detail: " + detail);
			
			// before deleting
			// remove the associated object reference
			// break the bi-directional link
			detail.getInstructor().setInstructorDetail(null);
			
			// now the detail is separate, and can be deleted
			if(detail!=null) {
				session.delete(detail);
				System.out.println("Deleted successfully");
			}
			
			
			
			Instructor instructor = session.get(Instructor.class, instructor_id);
			
			if(instructor==null) {
				System.out.println("The instructor was also deleted !");
			} else {
				System.out.println("The isntructor wasn't deleted");
			}
			
			// Now, the instructor didn't get deleted.
			// Thats what we needed.
			
			session.getTransaction().commit();
			
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

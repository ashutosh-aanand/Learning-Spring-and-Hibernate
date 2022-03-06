package com.learn.onetomany.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.learn.onetomany.entity.Course;
import com.learn.onetomany.entity.Instructor;
import com.learn.onetomany.entity.InstructorDetail;

public class HqlFetchDemo {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
								.configure()
								.addAnnotatedClass(Course.class)
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			/*
			 * BEFORE TESTING:
			 * ----------------
			 * In Instructor entity, change the fetch type for Courses to "LAZY"
			 * -----------------------------------------------------------------
			 */
			
			// start a transaction ------------------------------------------------------
			session.beginTransaction();
			
			
			// m2) Hibernate query using HQL: 
			// even if the fetch type is LAZY, hql will work.
			
			int id = 1;
			
			Query<Instructor> query = 
					session.createQuery("select i from Instructor i "
							+ "JOIN FETCH i.courses "
							+ "where i.id=:xyz",
							Instructor.class);
			// import org.hibernate.Query.query
			
			
			// set parameter on query (parameters are preceded with ':', as :xyz here)
			query.setParameter("xyz", id);
			
			// execute query and get instructor
			Instructor tempInstructor = query.getSingleResult(); 
			
			// here it will load everything Instructor, InstructorDetail and Courses as well
			System.out.println("\nInstructor: \n" + tempInstructor);
			
			
			// get courses for the instructor
			System.out.println("\nCourses: \n" + tempInstructor.getCourses());
			// The courses have already been fetched, so it will load the courses from memory

			// commit transaction --------------------------------------------------------
			session.getTransaction().commit();
			
			System.out.println("Done !");
			
			
			
			// This will work here only if the courses have already been fetched
			// inside the session
			System.out.println("\nCourses: \n" + tempInstructor.getCourses());
			// It works
			// as we have already fetched everything using hibernate HQL query
			
			
			// Here, hql did the same thing as was done by EAGER fetch.
			
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

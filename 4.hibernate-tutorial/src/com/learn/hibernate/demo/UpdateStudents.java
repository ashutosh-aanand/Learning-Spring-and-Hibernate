package com.learn.hibernate.demo;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.hibernate_entity.Student;

public class UpdateStudents {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			// begin transaction
			session.beginTransaction();
			
			Query q = session.createQuery("FROM Student");
			List<Student> students = q.getResultList();
			
			printList(students);
			
			// get student with id=2
			Student s = session.get(Student.class, 2);
			
			System.out.println("\n\n" + s);
			
			// update its email
			s.setEmail("rs_updated@mi.com");
			
			System.out.println("\n\n" + s);
			
			students = q.getResultList();
			printList(students);
			
			// commit transaction
			session.getTransaction().commit();
			// this commit statement finally updates the data in the database
			
			
			/*--------------------------------------------------------------------------------------------------------------
			 * Update all student's emails to mi@email.com
			 * -------------------------------------------------------------------------------------------------------------
			 */
			
			session = factory.getCurrentSession();
			
			session.beginTransaction();
			
			q = session.createQuery("FROM Student");
			students = q.getResultList();
			printList(students);
			
			session
			.createQuery("update Student set email='mi@email.com'")
			.executeUpdate();
			
			
			students = q.getResultList();
			printList(students);
			
			session.getTransaction().commit();
			
		}
		finally {
			factory.close();
		}
		
	}

	private static void printList(List<Student> students) {
		System.out.println("\n\n");
		for(Student s: students) {
			System.out.println(s);
		}
	}

}

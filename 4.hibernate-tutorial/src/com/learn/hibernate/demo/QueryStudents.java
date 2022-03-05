package com.learn.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.hibernate_entity.Student;

public class QueryStudents {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();
		// create session
		Session session = factory.getCurrentSession();
		
		try {
			// begin transaction
			session.beginTransaction();
			
			// 1. get all students
			List<Student> students = session
							.createQuery("FROM Student")
							.getResultList();
			/*
			 * here .createQuery("FROM student") won't work but
			 * .createQuery("FROM Student") will work
			 * as student is not the table name but the java class name
			 * which starts with a "capital letter".
			 */
			
			printList(students);
			
			// 2. get students with lastName = "sharma"
			students = session
					.createQuery("FROM Student WHERE lastName='sharma'")
					.getResultList();
			
			printList(students);
			
			// 3. 
			
			
			
			// commit transaction
			session.getTransaction().commit();
			
		} finally {
			factory.close();
		}
	}

	private static void printList(List<Student> students) {
		for(Student s: students) {
			System.out.println(s);
		}
	}

}

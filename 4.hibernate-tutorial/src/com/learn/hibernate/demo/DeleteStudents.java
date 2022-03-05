package com.learn.hibernate.demo;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.hibernate_entity.Student;

public class DeleteStudents {

	public static void main(String[] args) {
		
		SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			/*
			 * Delete Student with id 1
			 */
			// method 1: by using the whole object to be deleted
			Student s = session.get(Student.class, 1);
			if(s!=null) {
				session.delete(s);
			}
			
			Query q = session.createQuery("FROM Student");
			List<Student> students = q.getResultList();
			
			printList(students);
			
			// method 2: using hql query => better way
			session
				.createQuery("delete from Student where id=2")
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
		for(Student s: students) {
			System.out.println(s);
		}
		System.out.println("\n");
	}

}

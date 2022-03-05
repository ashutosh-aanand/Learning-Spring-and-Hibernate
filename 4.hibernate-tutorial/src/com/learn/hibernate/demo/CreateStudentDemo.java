package com.learn.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.learn.hibernate_entity.Student;

public class CreateStudentDemo {

	public static void main(String[] args) {
		// create session factory
		 SessionFactory factory = new Configuration()
									.configure()
									.addAnnotatedClass(Student.class)
									.buildSessionFactory();
		// create session
		 Session session = factory.getCurrentSession();
		 
		 try {
			 // create a student object
			 System.out.println("creating a new student object...");
			 Student s = new Student("Rohit", "Sharma", "r.sharma@gmail.com");
			 // as we created a constructor for Student without id field.
			 // since id is primary key, it will be added automatically in incremental way
			 
			 // start a transaction
			 session.beginTransaction();
			 
			 // save the student obj
			 System.out.println("saving the student...");
			 session.save(s);
			 
			 // commit transaction
			 session.getTransaction().commit();
			 
			 System.out.println("done !");
			 
		 } finally {
			 factory.close();
		 }

	}

}

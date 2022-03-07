package com.learn.manytomany.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course {
	/*
	 * Steps:
	 * 1. define fields
	 * 2. define constructor
	 * 3. getters and setters
	 * 4. toString
	 * 5. annotate fields
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="instructor_id")
	private Instructor instructor;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="course_id")
	private List<Review> reviews;
	
//	--- new code ---
	
	@ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE,
						CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name="course_student",
			joinColumns = @JoinColumn(name="course_id"),
			inverseJoinColumns = @JoinColumn(name="student_id")
			)
	private List<Student> students;
	
//	----------------
	
	
	public Course() {}
	
	public Course(String title) {
		this.title = title;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	
	public List<Review> getReviews() {
		return reviews;
	}
	
	// add a convenience method
	// to add new review to this course
	public void addReview(Review newReview) {
		if(reviews==null) {
			reviews = new ArrayList<>();
		}
		
		reviews.add(newReview);
	}
	
//	---- new -----------------------------------
	
	public List<Student> getStudents() {
		return students;
	}
	
	// add a convenience method
	// to add new Student to this course
	public void addStudent(Student theStudent) {
		if(students == null) {
			students = new ArrayList<>();
		}
		
		students.add(theStudent);
	}

//	--------------------------------------------	

	@Override
	public String toString() {
		return "Course [id=" + id + ", title=" + title + "]";
	}
	
}

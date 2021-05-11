/*
 * Filename: SMSRunner.java
* Author: Stefanski
* 02/25/2020 
 */
package jpa.mainrunner;

import static java.lang.System.out;
import static jpa.util.JpaUtil.getEntityManagerFactory;

import java.util.List;
import java.util.Scanner;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;
import jpa.service.CourseService;
import jpa.service.StudentCourseService;
import jpa.service.StudentService;
import jpa.util.JpaUtil;


import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**1
 * 
 * @author Harry
 *
 */
public class SMSRunner {
	public static EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

	private Scanner sin;
	private StringBuilder sb;

	static CourseService courseService;
	static StudentService studentService;
	private Student currentStudent;

	public SMSRunner() {
		sin = new Scanner(System.in);
		sb = new StringBuilder();
		courseService = new CourseService();
		studentService = new StudentService();
	}

	public static void showStudentCourses(String sEmail){
		System.out.println("My Classes");
		List<Course> courses = studentService.getStudentCourses(sEmail);
		System.out.printf("%-5s | % -30s | %s \n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
		for (Course course : courses)
		{
			System.out.printf("%-5s | % -30s | %s \n", course.getCId(), course.getCName(), course.getCInstructorName());
		}
	}
	public static void showAllCourses(String sEmail){
		System.out.println("My Classes");
		List<Course> allCourses = courseService.getAllCourses();
		System.out.printf("%-5s | % -30s | %s\n", "ID", "COURSE NAME", "INSTRUCTOR NAME");
		for (Course course : allCourses)
		{
			System.out.printf("%-5s | % -30s | %s\n", course.getCId(), course.getCName(), course.getCInstructorName());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SMSRunner sms = new SMSRunner();
		try {
			sms.run();
		}catch (NullPointerException | IllegalArgumentException e ){
			e.printStackTrace();
		}
	}

	 private void run() {
		// Login or quit
		 System.out.println("Please select 1 to login or 2 to quit");
		switch (mainMenu()) {
		case 1:

			if (studentLogin()) {
				registerMenu();
			}
			break;
		case 2:
			out.println("Goodbye!");
			break;

		default:

		}
	}

	private int mainMenu() {
		sb.append("\n1. Login\n2. Quit Application\nPlease Enter Selection: ");
		out.print(sb.toString());
		sb.delete(0, sb.length());

		return sin.nextInt();
	}

	private boolean studentLogin() {
		String query;

		boolean retValue = false;
		System.out.println("Enter your email address: ");
		String email = sin.next();
		System.out.print("Enter your password: ");
		String password = sin.next();
		if(studentService.validateStudent(email, password)){
			showStudentCourses(email);
		}

		Student students = studentService.getStudentByEmail(email);
		if (students != null) {
			currentStudent = students;
		}

		if (currentStudent != null & currentStudent.getSPass().equals(password)) {
			List<Course> courses = studentService.getStudentCourses(email);
			System.out.println("MyClasses");
			for (Course course : courses) {
				out.println(course);
			}
			retValue = true;
		} else {
			out.println("User Validation failed. GoodBye!");
		}
		return retValue;
	}



	private void registerMenu() {
		sb.append("\n1.Register a class\n2. Logout\nPlease Enter Selection: ");
		out.print(sb.toString());
		sb.delete(0, sb.length());

		switch (sin.nextInt()) {
		case 1:
			List<Course> allCourses = courseService.getAllCourses();
			List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getSEmail());
			allCourses.removeAll(studentCourses);
			out.printf("%5s%15S%15s\n", "ID", "Course", "Instructor");
			for (Course course : allCourses) {
				out.println(course);
			}
			out.println();
			out.print("Enter Course Number: ");
			int number = sin.nextInt();
			Course newCourse = courseService.getAllCourses().get(0);

			if (newCourse != null) {
				studentService.registerStudentToCourse(currentStudent.getSEmail(), number);
				Student temp = studentService.getStudentByEmail(currentStudent.getSEmail());
				
				StudentCourseService scService = new StudentCourseService();
				List<StudentCourses> sCourses = scService.getAllStudentCourses(temp.getSEmail());
				

				out.println("MyClasses");
				for (StudentCourses course : sCourses) {
					out.println(course);
				}
			}
			break;

		case 2:
		default:
			out.println("Goodbye!");
		}
	}
}


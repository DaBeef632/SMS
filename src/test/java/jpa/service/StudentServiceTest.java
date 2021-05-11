package jpa.service;

import jpa.entitymodels.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService ss = new StudentService();
    @Test
    void getAllStudents() {
    }

    @Test
    void getStudentByEmail() {
        Student s = ss.getStudentByEmail("sbowden1@yellowbook.com");

        //test if the name and password are  correct
        Assert.assertTrue(s.getSName().equals("Sonnnie Bowden"));
        Assert.assertTrue(s.getSPass().equals("SJc4aWSU"));
    }

    @Test
    void validateStudent() {
        //check email and password for proper login
        Assert.assertTrue(ss.validateStudent("sbowden1@yellowbook.com", "SJc4aWSU"));
        //check wrong pass
        Assert.assertFalse(ss.validateStudent("sbowden1@yellowbook.com", "wrong"));
        //check wrong email
        Assert.assertFalse(ss.validateStudent("wrong", "SJc4aWSU"));
    }

    @Test
    void registerStudentToCourse() {
    }

    @Test
    void getStudentCourses() {
    }
}
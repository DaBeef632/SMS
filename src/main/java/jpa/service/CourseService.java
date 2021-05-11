package jpa.service;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;
import jpa.mainrunner.SMSRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class CourseService implements CourseDAO {

    @Override
    public List<Course> getAllCourses() {
        EntityManager em = SMSRunner.emf.createEntityManager();
        //creating an empty course list
        List<Course> courses = null;
        em.getTransaction().begin();
        Query query = em.createQuery("Select c From Courses c");
        //set the course list equal to the resultlis
         courses = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return courses;
    }

    @Override
    public Course getCourseById(int id) {

        EntityManager em = SMSRunner.emf.createEntityManager();
        Course course = null;

        try {
            // begins transaction
            em.getTransaction().begin();
            //find the course by id
            course = em.find(Course.class, id);

            //commit
            em.getTransaction().commit();

        } catch (IllegalArgumentException | EntityNotFoundException | RollbackException e) {
            e.printStackTrace();
        } finally {
            //closes entity manager
            em.close();
        }
        // returns the course found by id
        return course;
    }
}


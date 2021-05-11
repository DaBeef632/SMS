package jpa.service;
import jpa.entitymodels.StudentCourses;
import jpa.mainrunner.SMSRunner;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

public class StudentCourseService {
    public List<StudentCourses> getAllStudentCourses(String email) {

        EntityManager em = SMSRunner.emf.createEntityManager();
        //creating a studentcourse list to fill by the query
        List<StudentCourses> studCourses;
        studCourses = null;

        try {
            // begins transaction
            em.getTransaction().begin();
            // created a query to find all student courses
            Query query = em.createQuery("SELECT sc FROM StudentCourses sc WHERE sc.eMail = :email");
            // email is the parameter
            query.setParameter("email", email);
            // the empty studentcourse list is set to the result list
            studCourses = (List<StudentCourses>) query.getResultList();
            em.getTransaction().commit();

            return studCourses;

        } catch(NullPointerException | IllegalStateException | IllegalArgumentException | EntityNotFoundException | RollbackException e){

            e.printStackTrace();

        } finally {

            em.close();
        }
        // return student course list
        return studCourses;
    }
}

package jpa.service;
import jpa.service.CourseService;
import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.entitymodels.StudentCourses;
import jpa.mainrunner.SMSRunner;
import jpa.util.JpaUtil;



import javax.persistence.*;
import java.util.List;

public class StudentService implements StudentDAO {


    @Override
    public List<Student> getAllStudents() {
        EntityManager em = SMSRunner.emf.createEntityManager();
        //creating an empty student list
        List<Student> students = null;
        //start transaction
        em.getTransaction().begin();
        //create a query to select all the students
        Query query = em.createQuery("Select s From Students s");
        // set the student list equal to the query result list
        students = query.getResultList();
       //commit
        em.getTransaction().commit();
        //close
        em.close();
        //return student list
        return students;
    }

    @Override
    public Student getStudentByEmail(String sEmail) {
        EntityManager em = SMSRunner.emf.createEntityManager();
        Student student = null;
        try {
            //transaction
            em.getTransaction().begin();
          //create named query
            Query query = em.createNamedQuery("find student by email");
            query.setParameter("email", sEmail);
            em.getTransaction().commit();
            //cast the single result to student
            //set the student as the single result
            student = (Student) query.getSingleResult();

        } catch (NullPointerException| NoResultException | IllegalStateException | IllegalArgumentException | EntityNotFoundException e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            //close em
            em.close();
            //return the student
           return student;
        }
    }



    @Override
    public boolean validateStudent(String sEmail, String sPassword) {


        try{
            //get the student by email
            Student student = getStudentByEmail(sEmail);
            //if the student is not null and the sPass equals the spassword return true
            if(student !=null && student.getSPass().equals(sPassword) ){
                return true;
            } else{
                return false;
            }
        } catch ( IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public void registerStudentToCourse(String sEmail, int cId) {
        EntityManager em = SMSRunner.emf.createEntityManager();
        em.getTransaction().begin();
        try {

            //find course by id
            Course course = em.find(Course.class, cId);
            //find student by email
            Student student = getStudentByEmail(sEmail);
            //create a course list using the get student courses method
            List<Course> sc = getStudentCourses(sEmail);
           //Check if the student is already in the class,
            if (!sc.contains(course)) {
                    sc.add(course);
                    student.setSCourses(sc);
                    em.merge(student);
                    em.getTransaction().commit();
                } else{
                System.out.println("You are already in the class");
            }
                }catch (IllegalArgumentException | EntityNotFoundException | RollbackException e) {
            e.printStackTrace();
        } finally {
            em.close();

            }
        }
    @Override
    public List<Course> getStudentCourses(String sEmail) {
        EntityManager em = SMSRunner.emf.createEntityManager();
        //creating an empty course list
        List<Course> courses = null;
        try {
            em.getTransaction().begin();

            Query query = em.createQuery("select course from Student s where email = :email");
            query.setParameter("email", sEmail);
            //filling the course list with the query result list
            courses = query.getResultList();
            em.close();
            //catching the exceptions
        } catch(IllegalArgumentException | EntityNotFoundException | RollbackException e){
            e.printStackTrace();

        }finally {
            em.close();
        }

        //returning the course list
        return courses;
    }
}
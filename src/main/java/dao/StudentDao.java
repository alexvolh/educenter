package dao;

import entity.Lecturer;
import entity.Student;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@LocalBean
public class StudentDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void saveStudent(Student student) {
        entityManager.persist(student);
    }

    public void updateLecturer(Student student) {
        Student detachStudent = getStudent(student.getId());
        detachStudent.setSurname(student.getSurname());
        detachStudent.setName(student.getName());
        detachStudent.setMiddle(student.getMiddle());
        detachStudent.setEmail(student.getEmail());
        detachStudent.setPhone(student.getPhone());
        detachStudent.setAdress(student.getAdress());
        detachStudent.setDescription(student.getDescription());
        entityManager.merge(detachStudent);
    }

    public Student getStudent(Long studentId) {
        Student student;
        student =  entityManager.find(Student.class, studentId);
        return student;
    }

    public void deleteStudent(Student student) {
        Student detachStudent = getStudent(student.getId());
        entityManager.remove(detachStudent);
    }

    public List<Student> getStudentList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = criteriaQuery.from(Student.class);
        criteriaQuery.select(root);
        TypedQuery<Student> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}

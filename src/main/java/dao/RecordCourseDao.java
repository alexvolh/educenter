package dao;

import entity.RecordOnCourse;
import entity.Student;
import entity.Studgroup;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@LocalBean
public class RecordCourseDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void saveRecCourse(RecordOnCourse recordOnCourse) {
        entityManager.persist(recordOnCourse);
    }

    public boolean isContain(Student student, Studgroup studgroup) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RecordOnCourse> recordOnCourseCriteriaQuery = criteriaBuilder.createQuery(RecordOnCourse.class);
        Root <RecordOnCourse> root = recordOnCourseCriteriaQuery.from(RecordOnCourse.class);
        recordOnCourseCriteriaQuery.select(root).where(criteriaBuilder.equal(root.get("student"), student), criteriaBuilder.equal(root.get("studgroup"), studgroup));

        List<RecordOnCourse> recordOnCourses =  entityManager.createQuery(recordOnCourseCriteriaQuery).getResultList();

        return !recordOnCourses.isEmpty();
    }

}


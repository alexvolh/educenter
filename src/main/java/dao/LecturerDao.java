package dao;

import entity.Course;
import entity.Lecturer;

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
public class LecturerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void saveLecturer(Lecturer lecturer) {
        entityManager.persist(lecturer);
    }

    public void updateLecturer(Lecturer lecturer) {
        Lecturer detachLecturer = this.getLecturer(lecturer.getId());
        detachLecturer.setSurname(lecturer.getSurname());
        detachLecturer.setName(lecturer.getName());
        detachLecturer.setMiddle(lecturer.getMiddle());
        detachLecturer.setEmail(lecturer.getEmail());
        detachLecturer.setPhone(lecturer.getPhone());
        detachLecturer.setDescription(lecturer.getDescription());
        entityManager.merge(detachLecturer);
    }

    public Lecturer getLecturer(Long lecturerId) {
        Lecturer lecturer;
        lecturer = entityManager.find(Lecturer.class, lecturerId);
        return lecturer;
    }

    public void deleteLecturer(Lecturer lecturer) {
        Lecturer detachLecturer = getLecturer(lecturer.getId());
        entityManager.remove(detachLecturer);
    }

    public List<Lecturer> getLecturerList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lecturer> criteriaQuery = criteriaBuilder.createQuery(Lecturer.class);
        Root<Lecturer> root = criteriaQuery.from(Lecturer.class);
        criteriaQuery.select(root);
        TypedQuery<Lecturer> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public List<Course> getListCourses(Lecturer lecturer) {
        Lecturer detachLecturer = getLecturer(lecturer.getId());
        return detachLecturer.getCourses();
    }
}

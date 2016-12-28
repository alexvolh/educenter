package dao;

import entity.Studgroup;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Stateless
@LocalBean
public class StudgroupDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void saveStudgroup(Studgroup studgroup) {
        entityManager.persist(studgroup);
    }

    public List<Studgroup> getStudgroupList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Studgroup> criteriaQuery = criteriaBuilder.createQuery(Studgroup.class);
        Root<Studgroup> root = criteriaQuery.from(Studgroup.class);
        criteriaQuery.select(root);
        TypedQuery<Studgroup> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    public Studgroup getStudgroup(Long StudgroupId) {
        Studgroup studgroup;
        studgroup = entityManager.find(Studgroup.class, StudgroupId);
        return studgroup;
    }

    public void deleteStudgroup(Studgroup studgroup) {
        Studgroup detachStudgroup = getStudgroup(studgroup.getId());
        entityManager.remove(detachStudgroup);
    }

    public void updateListStudgroupByName(String oldName, String newName, String description) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Studgroup> studgroupCriteriaUpdate = criteriaBuilder.createCriteriaUpdate(Studgroup.class);
        Root root = studgroupCriteriaUpdate.from(Studgroup.class);

        studgroupCriteriaUpdate.set("name", newName).set("description", description);
        studgroupCriteriaUpdate.where(criteriaBuilder.equal(root.get("name"), oldName));

        entityManager.createQuery(studgroupCriteriaUpdate).executeUpdate();
    }

    public void deleteStudgroupByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Studgroup> studgroupCriteriaDelete = criteriaBuilder.createCriteriaDelete(Studgroup.class);
        Root studgroupRoot = studgroupCriteriaDelete.from(Studgroup.class);

        studgroupCriteriaDelete.where(criteriaBuilder.equal(studgroupRoot.get("name"), name));
        entityManager.createQuery(studgroupCriteriaDelete).executeUpdate();
    }
}

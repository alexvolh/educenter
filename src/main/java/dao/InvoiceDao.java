package dao;

import entity.Invoice;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class InvoiceDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void saveInvoice(Invoice invoice) {
        entityManager.persist(invoice);
    }
}

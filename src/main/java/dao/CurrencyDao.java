
package dao;

import datasource.MariaDbJpaConnection;
import entity.currency;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CurrencyDao {

    public List<currency> findAll() {
        EntityManager em = MariaDbJpaConnection.getInstance();
        TypedQuery<currency> q = em.createQuery("SELECT c FROM Currency c", currency.class);
        return q.getResultList();
    }

    public currency findByAbbreviation(String abbr) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        TypedQuery<currency> q = em.createQuery("SELECT c FROM Currency c WHERE c.abbreviation = :abbr", currency.class);
        q.setParameter("abbr", abbr);
        List<currency> results = q.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public void persist(currency currency) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(currency);
        em.getTransaction().commit();
    }

    public void update(currency currency) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.merge(currency);
        em.getTransaction().commit();
    }

    public void delete(currency currency) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        currency managed = em.find(currency.class, currency.getId());
        if (managed != null) {
            em.remove(managed);
        }
        em.getTransaction().commit();
    }
}

package dao;

import entity.currency;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import datasource.MariaDbJpaConnection;

import java.util.List;

public class CurrencyDao {

    public List<currency> findAll() {
        EntityManager em = MariaDbJpaConnection.getInstance();
        TypedQuery<currency> query = em.createQuery("SELECT c FROM Currency c", currency.class);
        return query.getResultList();
    }

    public currency findByAbbreviation(String abbr) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        TypedQuery<currency> query = em.createQuery("SELECT c FROM Currency c WHERE c.abbreviation = :abbr", currency.class);
        query.setParameter("abbr", abbr);
        return query.getSingleResult();
    }

    public void persist(currency currency) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(currency);
        em.getTransaction().commit();
    }
}

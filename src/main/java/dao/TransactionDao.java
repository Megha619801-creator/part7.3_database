package dao;

import entity.Transaction;
import jakarta.persistence.EntityManager;
import datasource.MariaDbJpaConnection;

public class TransactionDao {

    public void persist(Transaction transaction) {
        EntityManager em = MariaDbJpaConnection.getInstance();
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
    }
}

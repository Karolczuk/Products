package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Category;
import MK.model.Trade;
import MK.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class TradeRepositoryImpl extends AbstractGenericRepository<Trade> implements TradeRepository {
    @Override
    public Optional<Trade> findByName(String name) {
        Optional<Trade> trade = Optional.empty();
        //Session session = null;
        EntityManager em = null;
        //Transaction tx = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            trade = em
                    .createQuery("select t from Trade t where t.name = :name", Trade.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.TRADE, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return trade;
    }
}

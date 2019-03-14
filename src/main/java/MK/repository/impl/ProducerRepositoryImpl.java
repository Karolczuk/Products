package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Producer;
import MK.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class ProducerRepositoryImpl extends AbstractGenericRepository<Producer> implements ProducerRepository {
    @Override
    public Optional<Producer> findByNameTradeCountry(String name, String tradeName, String countryName) {
        Optional<Producer> producer = Optional.empty();
        //Session session = null;
        EntityManager em = null;
        //Transaction tx = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            producer = em
                    .createQuery("select p from Producer p where p.name = :name and p.trade.name = :tradeName and p.country.name = :countryName", Producer.class)
                    .setParameter("name", name)
                    .setParameter("tradeName", tradeName)
                    .setParameter("countryName", countryName)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.PRODUCER, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return producer;
    }
}

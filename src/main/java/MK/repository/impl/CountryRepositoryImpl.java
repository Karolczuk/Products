package MK.repository.impl;


import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Country;
import MK.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class CountryRepositoryImpl extends AbstractGenericRepository<Country> implements CountryRepository {

    @Override
    public Optional<Country> findByName(String name) {
        Optional<Country> country = Optional.empty();
        //Session session = null;
        EntityManager em = null;
        //Transaction tx = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            country = em
                    .createQuery("select c from Country c where c.name = :name", Country.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.CUSTOMER, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return country;
    }
}

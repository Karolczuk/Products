package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Country;
import MK.model.Customer;
import MK.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl extends AbstractGenericRepository<Customer> implements CustomerRepository {
    @Override
    public Optional<Customer> findByName(String name) {
        Optional<Customer> customer = Optional.empty();
        //Session session = null;
        EntityManager em = null;
        //Transaction tx = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            customer = em
                    .createQuery("select c from Customer c where c.name = :name", Customer.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.CUSTOMER, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return customer;
    }

    @Override
    public Optional<Customer> findByNameSurnameCountry(String name, String surname, String countryName) {
        Optional<Customer> customer = Optional.empty();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            customer = em
                    .createQuery("select c from Customer c where c.name = :name and c.surname = :surname and c.country.name = :country", Customer.class)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .setParameter("country", countryName)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.CUSTOMER, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return customer;
        }



    @Override
    public Optional<Customer> findByNameSurnameAgeCountry(String name) {
        return Optional.empty();
    }
}

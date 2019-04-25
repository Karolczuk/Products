package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Producer;
import MK.model.Product;
import MK.repository.generic.AbstractGenericRepository;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl extends AbstractGenericRepository<Product> implements ProductRepository {

    @Override
    public Optional<Product> findByNameCategoryProducer(String name, String categoryName, String producerName) {
        Optional<Product> product = Optional.empty();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            product = em
                    .createQuery("select p from Product p where p.name = :name and p.category.name = :categoryName and p.producer.name = :producerName", Product.class)
                    .setParameter("name", name)
                    .setParameter("categoryName", categoryName)
                    .setParameter("producerName", producerName)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.PRODUCT, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return product;
    }

    @Override
    public Optional<Product> findByName(String name) {
        Optional<Product> product = Optional.empty();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            product = em
                    .createQuery("select p from Product p where p.name = :name", Product.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.PRODUCT, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return product;
    }

    @Override
    public List<Product> findAllWithGuarantees() {
        List<Product> products = new ArrayList<>();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            products = em.createQuery("select p from Product p ", Product.class).getResultList();
            for (Product p : products) {
                Hibernate.initialize(p.getGuarantees());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.PRODUCT, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return products;
    }


    @Override
    public List<Product> findAllWithStocks() {
        List<Product> products = new ArrayList<>();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            products = em.createQuery("select p from Product p ", Product.class).getResultList();
            for (Product p : products) {
                Hibernate.initialize(p.getStocks());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.PRODUCT, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return products;
    }


    @Override
    public List<Product> findAlllWithCategories() {
        List<Product> products = new ArrayList<>();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            products = em.createQuery("select p from Product p ", Product.class).getResultList();
            for (Product p : products) {
                Hibernate.initialize(p.getCategory());
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.PRODUCT, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return products;
    }
}

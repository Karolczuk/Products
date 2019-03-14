package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Product;
import MK.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
}

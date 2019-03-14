package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.Category;
import MK.repository.generic.AbstractGenericRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;


public class CategoryRepositoryImpl extends AbstractGenericRepository<Category> implements CategoryRepository {

    
    @Override
    public Optional<Category> findByName(String name) {
        Optional<Category> category = Optional.empty();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            category = em
                    .createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.CATEGORY, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return category;    }
}

package MK.repository.generic;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    protected final EntityManagerFactory emf = DbConnection.getInstance().getSessionFactory();

    private final Class<T> type
            = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public T saveOrUpdate(T t) {
        EntityManager em = null;
        EntityTransaction tx = null;
        T item = null;
        try {
            if (t == null) {
                throw new NullPointerException("ITEM IS NULL");
            }

            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            item = (T)em.merge(t);
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.CUSTOMER, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return item;
    }

    @Override
    public void delete(Long id) {
        EntityManager session = null;
        EntityTransaction tx = null;
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }

            session = emf.createEntityManager();
            tx = session.getTransaction();
            tx.begin();
            T item =session.getReference(type,id);
            session.remove(item);
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.CUSTOMER, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteAll() {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            List<T> items = em
                    // HQL - hibernate query language
                    // sql -> select * from teams;
                    // hql -> select t from Team t
                    .createQuery("select t from " + type.getCanonicalName() +" t", type)
                    .getResultList();
            for (T i : items) {
                em.remove(i);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.CUSTOMER, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Optional<T> findOne(Long id) {
        Optional<T> item = Optional.empty();
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            item = Optional.of(em.getReference(type, id));
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.CUSTOMER, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return item;
    }

    @Override
    public List<T> findAll() {
        List<T> items = null;
        //Session session = null;
        EntityManager em = null;
        //Transaction tx = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            items = em
                    // HQL - hibernate query language
                    // sql -> select * from teams;
                    // hql -> select t from Team t
                    .createQuery("select t from " + type.getCanonicalName() +" t", type)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.CUSTOMER, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return items;
    }
}

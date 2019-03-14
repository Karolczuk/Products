package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.EGuarantee;
import MK.model.GuaranteeComponents;
import MK.repository.generic.AbstractGenericRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class GuaranteeRepositoryImpl extends AbstractGenericRepository<GuaranteeComponents> implements GuaranteeRepository {
    @Override
    public Optional<GuaranteeComponents> findByName(EGuarantee name) {
        Optional<GuaranteeComponents> guaranteeComponents = Optional.empty();
        //Session session = null;
        EntityManager em = null;
        //Transaction tx = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            guaranteeComponents = em
                    .createQuery("select g from GuaranteeComponents g where g.eGuarantee = :name", GuaranteeComponents.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.GUARANTEECOMPONENTS, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return guaranteeComponents;    }
}

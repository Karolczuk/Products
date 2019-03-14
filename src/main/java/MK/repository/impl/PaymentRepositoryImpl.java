package MK.repository.impl;

import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.model.EPayment;
import MK.model.Payment;
import MK.repository.generic.AbstractGenericRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class PaymentRepositoryImpl extends AbstractGenericRepository<Payment> implements PaymentRepository {
    @Override
    public Optional<Payment> findByName(EPayment name) {
        Optional<Payment> payment = Optional.empty();
        //Session session = null;
        EntityManager em = null;
        //Transaction tx = null;
        EntityTransaction tx = null;
        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            payment = em
                    .createQuery("select p from Payment p where p.epayment = :name", Payment.class)
                    .setParameter("name", name)
                    .getResultList().stream().findFirst();

            tx.commit();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.PAYMENT, e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return payment;
    }
}

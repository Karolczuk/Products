package MK.repository.impl;

import MK.model.EPayment;
import MK.model.Payment;
import MK.repository.generic.GenericRepository;

import java.util.Optional;

public interface PaymentRepository extends GenericRepository<Payment> {
    Optional<Payment> findByName(EPayment name);

}

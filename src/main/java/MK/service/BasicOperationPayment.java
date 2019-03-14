package MK.service;

import MK.dto.CountryDto;
import MK.dto.PaymentDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.model.Payment;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.CustomerRepository;
import MK.repository.impl.PaymentRepository;
import MK.repository.impl.PaymentRepositoryImpl;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationPayment {

    private final PaymentRepository paymentRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationPayment(
            PaymentRepository paymentRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.paymentRepository =paymentRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }

    public void addPayment(PaymentDto paymentDto) {

        if (paymentDto== null) {
            throw new MyException(ExceptionCode.PAYMENT, "PAYMENT OBJECT IS NULL");
        }

        if (managmentProductsValidator.validatePaymentInsideDB(paymentDto)) {
            throw new MyException(ExceptionCode.PAYMENT, "PAYMENT ALREADY EXISTS");
        }

        Payment payment = modelMapper.fromPaymentDtoToPayment(paymentDto);

        paymentRepository.saveOrUpdate(payment);


    }
}

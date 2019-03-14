package MK.service;

import MK.dto.*;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.*;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationCustomerOrder {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ProducerRepository producerRepository;
    private final PaymentRepository paymentRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;
    private final CustomerOrderRepository customerOrderRepository;


    public BasicOperationCustomerOrder(
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            ProducerRepository producerRepository,
            PaymentRepository paymentRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper,
            CustomerOrderRepository customerOrderRepository
    ) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.producerRepository = producerRepository;
        this.paymentRepository = paymentRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
        this.customerOrderRepository = customerOrderRepository;
    }

    public void addCustomerOrder(CustomerOrderDto customerOrderDto) {

        if (customerOrderDto == null) {
            throw new MyException(ExceptionCode.CUSTOMER_ORDER, "CUSTOMER_ORDER OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateCustomerOrderFields(customerOrderDto)) {
            throw new MyException(ExceptionCode.CUSTOMER_ORDER, "CUSTOMER_ORDER FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validaterCustomerOrderInsideDB(customerOrderDto)) {
            throw new MyException(ExceptionCode.CUSTOMER_ORDER, "CUSTOMER_ORDER ALREADY EXISTS");
        }


        CustomerOrder customerOrder = modelMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto);


        // -----------------------------------------------------------------------------------
        // ------------------------------- PRODUCT VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        ProductDto productDto = customerOrderDto.getProductDto();

        if (productDto == null) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT OBJECT IS NULL");
        }

        if (productDto.getId() == null && productDto.getName() == null) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateProductFields(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateProductInsideDB(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT NOT FOUND");
        }

        // -----------------------------------------------------------------------------------
        // ------------------------------- PAYMENT VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        PaymentDto paymentDto = customerOrderDto.getPaymentDto();

        if (paymentDto == null) {
            throw new MyException(ExceptionCode.PAYMENT, "PAYMENT OBJECT IS NULL");
        }

        if (paymentDto.getId() == null && paymentDto.getEpayment() == null) {
            throw new MyException(ExceptionCode.PAYMENT, "PAYMENT WITHOUT ID AND NAME");
        }


        if (!managmentProductsValidator.validatePaymentInsideDB(paymentDto)) {
            throw new MyException(ExceptionCode.PAYMENT, "PAYMENT NOT FOUND");
        }


        // -----------------------------------------------------------------------------------
        // ------------------------------- CUSTOMER VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        CustomerDto customerDto = customerOrderDto.getCustomerDto();

        if (customerDto == null) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER OBJECT IS NULL");
        }

        if (customerDto.getId() == null && customerDto.getName() == null) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateCustomerFields(customerDto)) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateCustomerInsideDB(customerDto)) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER NOT FOUND");
        }

        // -----------------------------------------------------------------------------------
        // ----------------------------------- INSERT INTO DB --------------------------------
        // -----------------------------------------------------------------------------------

        Customer customer = null;

        if (customer.getId() != null) {
            customer = customerRepository
                    .findOne(customer.getId())
                    .orElse(null);
        }

        if (customer == null) {
            customer = customerRepository
                    .findByName(customerDto.getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.CUSTOMER, "CUSTOMER WITH GIVEN ID AND NAME NOT FOUND"));
        }


        Product product = null;

        if (productDto.getId() != null) {
            product = productRepository
                    .findOne(product.getId())
                    .orElse(null);
        }

        if (product == null) {
            product = productRepository
                    .findByNameCategoryProducer(productDto.getName(), productDto.getCategoryDto().getName(), productDto.getProducerDto().getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.PRODUCT, "PRODUCT WITH GIVEN ID AND NAME NOT FOUND"));
        }


        Payment payment = null;

        if (payment.getId() != null) {
            payment = paymentRepository
                    .findOne(payment.getId())
                    .orElse(null);
        }

        if (payment == null) {
            payment = paymentRepository
                    .findByName(customerOrderDto.getPaymentDto().getEpayment())
                    .orElseThrow(() -> new MyException(ExceptionCode.PAYMENT, "PAYMENT WITH GIVEN ID AND NAME NOT FOUND"));
        }


        customerOrder.setCustomer(customer);
        customerOrder.setProduct(product);
        customerOrder.setPayment(payment);
        customerOrderRepository.saveOrUpdate(customerOrder);

    }

}

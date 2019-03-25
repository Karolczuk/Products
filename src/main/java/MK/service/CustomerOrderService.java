package MK.service;

import MK.dto.*;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.*;
import MK.validator.impl.model.CustomerModelValidator;
import MK.validator.impl.model.CustomerOrderModelValidator;
import MK.validator.impl.model.ProductModelValidator;
import MK.validator.impl.persistence.CustomerOrderPersistenceValidator;
import MK.validator.impl.persistence.CustomerPersistenceValidator;
import MK.validator.impl.persistence.ProductPersistenceValidator;

public class CustomerOrderService {
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ProducerRepository producerRepository;
    private final CustomerModelValidator customerModelValidator;
    private final CustomerPersistenceValidator customerPersistenceValidator;
    private final CustomerOrderModelValidator customerOrderModelValidator;
    private final CustomerOrderPersistenceValidator customerOrderPersistenceValidator;
    private final ProductModelValidator productModelValidator;
    private final ProductPersistenceValidator productPersistenceValidator;
    private final ModelMappers modelMapper;
    private final CustomerOrderRepository customerOrderRepository;


    public CustomerOrderService(
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            ProducerRepository producerRepository,
            CustomerModelValidator customerModelValidator,
            CustomerPersistenceValidator customerPersistenceValidator,
            CustomerOrderModelValidator customerOrderModelValidator,
            CustomerOrderPersistenceValidator customerOrderPersistenceValidator,
            ProductModelValidator productModelValidator,
            ProductPersistenceValidator productPersistenceValidator,
            ModelMappers modelMapper,
            CustomerOrderRepository customerOrderRepository
    ) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.producerRepository = producerRepository;
        this.customerModelValidator = customerModelValidator;
        this.customerPersistenceValidator = customerPersistenceValidator;
        this.customerOrderModelValidator = customerOrderModelValidator;
        this.customerOrderPersistenceValidator = customerOrderPersistenceValidator;
        this.productModelValidator = productModelValidator;
        this.productPersistenceValidator = productPersistenceValidator;
        this.modelMapper = modelMapper;
        this.customerOrderRepository = customerOrderRepository;
    }

    public void addCustomerOrder(CustomerOrderDto customerOrderDto) {

        if (customerOrderDto == null) {
            throw new MyException(ExceptionCode.CUSTOMER_ORDER, "CUSTOMER_ORDER OBJECT IS NULL");
        }

        if (!customerOrderModelValidator.validateCustomerOrderFields(customerOrderDto)) {
            throw new MyException(ExceptionCode.CUSTOMER_ORDER, "CUSTOMER_ORDER FIELDS ARE NOT VALID");
        }

        if (customerOrderPersistenceValidator.validaterCustomerOrderInsideDB(customerOrderDto)) {
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

        if (!productModelValidator.validateProductFields(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT FIELDS ARE NOT VALID");
        }

        if (!productPersistenceValidator.validateProductInsideDB(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT NOT FOUND");
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

        if (!customerModelValidator.validateCustomerFields(customerDto)) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER FIELDS ARE NOT VALID");
        }

        if (!customerPersistenceValidator.validateCustomerInsideDB(customerDto)) {
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

        customerOrder.setCustomer(customer);
        customerOrder.setProduct(product);
        customerOrderRepository.saveOrUpdate(customerOrder);

    }

}

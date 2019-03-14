package MK.service;

import MK.dto.*;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.CategoryRepository;
import MK.repository.impl.GuaranteeRepository;
import MK.repository.impl.ProducerRepository;
import MK.repository.impl.ProductRepository;
import MK.validator.ManagmentProductsValidator;


public class BasicOperationProduct {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;
    private final GuaranteeRepository guaranteeRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationProduct(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProducerRepository producerRepository,
            GuaranteeRepository guaranteeRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
        this.guaranteeRepository = guaranteeRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }

    public void addProduct(ProductDto productDto) {

        if (productDto == null) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateProductFields(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateProductInsideDB(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT ALREADY EXISTS");
        }


        Product product = modelMapper.fromProductDtoToProduct(productDto);

        // -----------------------------------------------------------------------------------
        // ------------------------------- CATEGORY VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        CategoryDto categoryDto = productDto.getCategoryDto();

        if (categoryDto == null) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY OBJECT IS NULL");
        }

        if (categoryDto.getId() == null && categoryDto.getName() == null) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateCategoryFields(categoryDto)) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateCategoryInsideDB(categoryDto)) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY NOT FOUND");
        }

        // -----------------------------------------------------------------------------------
        // ------------------------------- PRODUCER VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        ProducerDto producerDto = productDto.getProducerDto();

        if (producerDto == null) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER OBJECT IS NULL");
        }

        if (producerDto.getId() == null && producerDto.getName() == null) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateProducerFields(producerDto)) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateProducerInsideDB(producerDto)) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER NOT FOUND");
        }

        // -----------------------------------------------------------------------------------
        // ------------------------------- GUARANTEE VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        GuaranteeComponentsDto guaranteeComponentsDto = productDto.getGuarnteeComponentsDto();

        if (guaranteeComponentsDto == null) {
            throw new MyException(ExceptionCode.GUARANTEECOMPONENTS, "GUARANTEE_COMPONENTS OBJECT IS NULL");
        }

        if (guaranteeComponentsDto.getId() == null && guaranteeComponentsDto.getEGuarantee() == null) {
            throw new MyException(ExceptionCode.GUARANTEECOMPONENTS, "GUARANTEE_COMPONENTS WITHOUT ID AND NAME");
        }


        if (!managmentProductsValidator.validateGuaranteeInsideDB(guaranteeComponentsDto)) {
            throw new MyException(ExceptionCode.GUARANTEECOMPONENTS, "GUARANTEE_COMPONENTS NOT FOUND");
        }
        // -----------------------------------------------------------------------------------
        // ----------------------------------- INSERT INTO DB --------------------------------
        // -----------------------------------------------------------------------------------

        Category category = null;

        if (categoryDto.getId() != null) {
            category = categoryRepository
                    .findOne(categoryDto.getId())
                    .orElse(null);
        }

        if (category == null) {
            category = categoryRepository
                    .findByName(categoryDto.getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.CATEGORY, "CATEGORY WITH GIVEN ID AND NAME NOT FOUND"));
        }



        Producer producer = null;

        if (producerDto.getId() != null) {
            producer = producerRepository
                    .findOne(producer.getId())
                    .orElse(null);
        }

        if (producer == null) {
            producer = producerRepository
                    .findByNameTradeCountry(producerDto.getName(),producerDto.getTradeDto().getName(),producerDto.getCountryDto().getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.PRODUCER, "PRODUCER WITH GIVEN ID AND NAME NOT FOUND"));
        }



        GuaranteeComponents guaranteeComponents = null;

        if (guaranteeComponentsDto.getId() != null) {
            guaranteeComponents = guaranteeRepository
                    .findOne(guaranteeComponents.getId())
                    .orElse(null);
        }

        if (guaranteeComponents== null) {
            guaranteeComponents = guaranteeRepository
                    .findByName(productDto.getGuarnteeComponentsDto().getEGuarantee())
                    .orElseThrow(() -> new MyException(ExceptionCode.GUARANTEECOMPONENTS, "GUARANTEE_COMPONENTS WITH GIVEN ID AND NAME NOT FOUND"));
        }

        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println("-----------------------------------------------------");
        System.out.println(category);
        System.out.println(producer);
        System.out.println(guaranteeComponents);
        product.setCategory(category);
        product.setProducer(producer);
        product.setGuaranteeComponents(guaranteeComponents);
        productRepository.saveOrUpdate(product);
    }

}


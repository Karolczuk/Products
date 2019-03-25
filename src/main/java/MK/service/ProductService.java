package MK.service;

import MK.dto.*;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.CategoryRepository;
import MK.repository.impl.ProducerRepository;
import MK.repository.impl.ProductRepository;
import MK.validator.impl.model.CategoryModelValidator;
import MK.validator.impl.model.ProducerModelValidator;
import MK.validator.impl.model.ProductModelValidator;
import MK.validator.impl.persistence.ProducerPersistenceValidator;
import MK.validator.impl.persistence.ProductPersistenceValidator;


public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;
    private final ProductModelValidator productModelValidator;
    private final ProductPersistenceValidator productPersistenceValidator;
    private final ProducerModelValidator producerModelValidator;
    private final ProducerPersistenceValidator producerPersistenceValidator;
    private final CategoryModelValidator categoryModelValidator;
    private final ModelMappers modelMapper;


    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProducerRepository producerRepository,
            ProductModelValidator productModelValidator,
            ProductPersistenceValidator productPersistenceValidator,
            ProducerModelValidator producerModelValidator,
            ProducerPersistenceValidator producerPersistenceValidator,
            CategoryModelValidator categoryModelValidator,
            ModelMappers modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
        this.productModelValidator = productModelValidator;
        this.producerModelValidator = producerModelValidator;
        this.productPersistenceValidator = productPersistenceValidator;
        this.producerPersistenceValidator = producerPersistenceValidator;
        this.categoryModelValidator = categoryModelValidator;
        this.modelMapper = modelMapper;
    }

    public void addProduct(ProductDto productDto) {

        if (productDto == null) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT OBJECT IS NULL");
        }

        if (!productModelValidator.validateProductFields(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT FIELDS ARE NOT VALID");
        }

        if (productPersistenceValidator.validateProductInsideDB(productDto)) {
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

        if (!categoryModelValidator.validateCategoryFields(categoryDto)) {
            throw new MyException(ExceptionCode.CATEGORY, "CATEGORY FIELDS ARE NOT VALID");
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

        if (!producerModelValidator.validateProducerFields(producerDto)) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER FIELDS ARE NOT VALID");
        }

        if (!producerPersistenceValidator.validateProducerInsideDB(producerDto)) {
            throw new MyException(ExceptionCode.PRODUCER, "PRODUCER NOT FOUND");
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


        product.setCategory(category);
        product.setProducer(producer);

        productRepository.saveOrUpdate(product);
    }

}


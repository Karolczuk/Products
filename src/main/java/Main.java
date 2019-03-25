import MK.dto.*;
import MK.mappers.ModelMappers;
import MK.repository.impl.*;
import MK.service.CountryService;
import MK.service.ProductService;
import MK.validator.impl.model.CategoryModelValidator;
import MK.validator.impl.model.CountryModelValidator;
import MK.validator.impl.model.ProducerModelValidator;
import MK.validator.impl.model.ProductModelValidator;
import MK.validator.impl.persistence.ProducerPersistenceValidator;
import MK.validator.impl.persistence.ProductPersistenceValidator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {

        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl();
        CountryRepository countryRepository = new CountryRepositoryImpl();
        TradeRepository tradeRepository = new TradeRepositoryImpl();
        //  PaymentRepository paymentRepository = new PaymentRepositoryImpl();
        ErrorRepositoryImpl errorRepository = new ErrorRepositoryImpl();
        ProducerRepository producerRepository = new ProducerRepositoryImpl();
        ModelMappers modelMappers = new ModelMappers();
        ProductRepository productRepository = new ProductRepositoryImpl();
        StockRepository stockRepository = new StockRepositoryImpl();
        ProductModelValidator productModelValidator = new ProductModelValidator();
        ProductPersistenceValidator productPersistenceValidator = new ProductPersistenceValidator(productRepository);
        CategoryModelValidator categoryModelValidator = new CategoryModelValidator();
        ProducerModelValidator producerModelValidator = new ProducerModelValidator();
        ProducerPersistenceValidator producerPersistenceValidator = new ProducerPersistenceValidator(producerRepository);
        CountryModelValidator countryModelValidator = new CountryModelValidator();

//        ProductService productService =
//                new ProductService(
//                        productRepository,
//                        categoryRepository,
//                        producerRepository,
//                        productModelValidator,
//                        productPersistenceValidator,
//                        producerModelValidator,
//                        producerPersistenceValidator,
//                        categoryModelValidator,
//                        modelMappers
//                );
//
//
//                ProductDto productDto = ProductDto.builder()
//                .name("PIZZA")
//                .categoryDto(CategoryDto.builder().name("A").build())
//                .producerDto(ProducerDto.builder()
//                        .name("MM")
//                        .tradeDto(TradeDto.builder().name("FOOD").build())
//                        .countryDto(CountryDto.builder().name("SPAIN").build())
//                        .build()
//                )
//                        .guarantees(new HashSet<>(Arrays.asList()))
//                .price(new BigDecimal(45))
//                .build();
//
//        productService.addProduct(productDto);


        CountryService countryService = new CountryService(
                countryRepository,
                countryModelValidator,
                modelMappers
        );

        CountryDto countryDto = CountryDto.builder().name("CHINA").build();

        countryService.addCountry(countryDto);
    }

}

package MK;

import MK.dto.*;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.model.EGuarantee;
import MK.model.Shop;
import MK.repository.impl.*;
import MK.service.*;
import MK.validator.ManagmentProductsValidator;

import java.math.BigDecimal;

public class AppKM {
    public static void main(String[] args) {

        CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl();
        CountryRepository countryRepository = new CountryRepositoryImpl();
        TradeRepository tradeRepository = new TradeRepositoryImpl();
        PaymentRepository paymentRepository = new PaymentRepositoryImpl();
        ErrorRepositoryImpl errorRepository = new ErrorRepositoryImpl();
        ProducerRepository producerRepository = new ProducerRepositoryImpl();
        GuaranteeRepository guaranteeRepository = new GuaranteeRepositoryImpl();
        ModelMappers modelMappers = new ModelMappers();
        ProductRepository productRepository = new ProductRepositoryImpl();
        StockRepository stockRepository = new StockRepositoryImpl();
        ShopRepository shopRepository = new ShopRepositoryImpl();

        ManagmentProductsValidator managmentProductsValidator
                = new ManagmentProductsValidator(
                customerRepository,
                categoryRepository,
                countryRepository,
                tradeRepository,
                paymentRepository,
                errorRepository,
                guaranteeRepository,
                producerRepository,
                productRepository,
                stockRepository,
                shopRepository
        );
        BasicOperation basicOperation
                = new BasicOperation(
                countryRepository,
                customerRepository,
                managmentProductsValidator,
                modelMappers
        );


        BasicOperationCountry basicOperationCountry
                = new BasicOperationCountry(
                countryRepository,
                customerRepository,
                shopRepository,
                managmentProductsValidator,
                modelMappers
        );


        BasicOperationPayment basicOperationPayment
                = new BasicOperationPayment(
                paymentRepository,
                managmentProductsValidator,
                modelMappers
        );


        BasicOperationTrade basicOperationTrade
                = new BasicOperationTrade(
                tradeRepository,
                managmentProductsValidator,
                modelMappers
        );


        BasicOperationProducer basicOperationProducer
                = new BasicOperationProducer(
                producerRepository,
                countryRepository,
                tradeRepository,
                managmentProductsValidator,
                modelMappers
        );


        BasicOperationGuarantee basicOperationGuarantee
                = new BasicOperationGuarantee(
                guaranteeRepository,
                productRepository,
                managmentProductsValidator,
                modelMappers
        );

        BasicOperationProduct basicOperationProduct =
                new BasicOperationProduct(
                        productRepository,
                        categoryRepository,
                        producerRepository,
                        guaranteeRepository,
                        managmentProductsValidator,
                        modelMappers
                );

        BasicOperationCategory basicOperationCategory =
                new BasicOperationCategory(
                        categoryRepository,
                        managmentProductsValidator,
                        modelMappers
                );


        BasicOperationStock basicOperationStock =
                new BasicOperationStock(
                        stockRepository,
                        shopRepository,
                        productRepository,
                        managmentProductsValidator,
                        modelMappers
                );

//
//        BasicOperationShop basicOperationShop =
//                new BasicOperationShop(
//                        shopRepository,
//                        countryRepository,
//                        managmentProductsValidator,
//                        modelMappers
//                );
//
//
//        ShopDto shopDto = ShopDto
//                .builder()
//                .countryDto(
//                        CountryDto
//                                .builder()
//                                .name("FRANCE")
//                                .build()
//                )
//                .name("CARREFOUR")
//                .build();
//
//        basicOperationShop.addShop(shopDto);


//        ProductDto productDto = ProductDto.builder()
//                .name("CHICKEN")
//                .categoryDto(CategoryDto.builder().name("A").build())
//                .producerDto(ProducerDto.builder()
//                        .name("MM")
//                        .tradeDto(TradeDto.builder().name("FOOD").build())
//                        .countryDto(CountryDto.builder().name("SPAIN").build())
//                        .build()
//                )
//                .guarnteeComponentsDto(GuaranteeComponentsDto.builder().eGuarantee(EGuarantee.MONEY_BACK).build())
//                .price(new BigDecimal(45))
//                .build();

//        basicOperationProduct.addProduct(productDto);
//

        StockDto stockDto = StockDto.builder()
                .shopDto(
                        ShopDto
                                .builder()
                                .name("BORKI")
                                .countryDto(CountryDto
                                        .builder()
                                        .name("SPAIN")
                                        .build())
                                .build()
                )
                .productDto(
                        ProductDto.builder()
                                .name("CHICKEN")
                                .categoryDto(CategoryDto.builder().name("A").build())
                                .producerDto(ProducerDto.builder()
                                        .name("MM")
                                        .tradeDto(TradeDto.builder().name("FOOD").build())
                                        .countryDto(CountryDto.builder().name("SPAIN").build())
                                        .build()
                                )
                                .guarnteeComponentsDto(GuaranteeComponentsDto.builder().eGuarantee(EGuarantee.MONEY_BACK).build())
                                .price(new BigDecimal(45))
                                .build()


                )
                .quantty(5)
                .build();
        basicOperationStock.addStock(stockDto);
        //DODAC SKLEP


//        CategoryDto categoryDto = CategoryDto.builder()
//                .name("A")
//                .build();
//
//        basicOperationCategory.addCategory(categoryDto);

//        ProductDto productDto = ProductDto.builder()
//                .name("CHICKEN")
//                .categoryDto(CategoryDto.builder().name("A").build())
//                .producerDto(ProducerDto.builder()
//                        .name("MM")
//                        .tradeDto(TradeDto.builder().name("FOOD").build())
//                        .countryDto(CountryDto.builder().name("SPAIN").build())
//                        .build()
//                )
//                .guarnteeComponentsDto(GuaranteeComponentsDto.builder().eGuarantee(EGuarantee.MONEY_BACK).build())
//                .price(new BigDecimal(45))
//                .build();
////
//
//           basicOperationProduct.addProduct(productDto);


//        ProducerDto producerDto = ProducerDto.builder()
//                .name("WW")
//                .tradeDto(TradeDto
//                        .builder()
//                        .name("FOOD")
//                        .build())
//                .countryDto(CountryDto
//                        .builder()
//                        .name("SPAIN")
//                        .build())
//                .build();
//
//        basicOperationProducer.addProducer(producerDto);


//        GuaranteeComponentsDto guaranteeComponentsDto = GuaranteeComponentsDto.builder()
//                .eGuarantee(EGuarantee.MONEY_BACK)
//                .build();
//
//        basicOperationGuarantee.addGuarantee(guaranteeComponentsDto);


//        TradeDto tradeDto = TradeDto.builder()
//                .name("TRANSPORT")
//                .build();
//        basicOperationTrade.addTrade(tradeDto);

//        CountryDto countryDto = CountryDto.builder()
//                .name("FRANCE")
//                .build();
//
//        basicOperationCountry.addCountry(countryDto);

//        PaymentDto paymentDto = PaymentDto.builder()
//                .epayment(EPayment.MONEY_TRANSFER)
//                .build();
//        basicOperationPayment.addPayment(paymentDto);

//        CustomerDto customerDto = CustomerDto.builder()
//                .name("IGA")
//                .surname("LAS")
//                .age(19)
//                .countryDto(
//                        CountryDto
//                                .builder()
//                                .name("GERMANY")
//                                .build()
//                )
//                .build();
//        basicOperation.addCustomer(customerDto);
    }
}

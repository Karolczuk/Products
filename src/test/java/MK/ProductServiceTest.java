package MK;

import MK.dto.*;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.CategoryRepository;
import MK.repository.impl.ProducerRepository;
import MK.repository.impl.ProductRepository;
import MK.service.ProductService;
import MK.validator.impl.model.CategoryModelValidator;
import MK.validator.impl.model.ProducerModelValidator;
import MK.validator.impl.model.ProductModelValidator;
import MK.validator.impl.persistence.ProducerPersistenceValidator;
import MK.validator.impl.persistence.ProductPersistenceValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProducerModelValidator producerModelValidator;

    @Mock
    private ProducerPersistenceValidator producerPersistenceValidator;

    @Mock
    private CategoryModelValidator categoryModelValidator;

    @Mock
    private ProductModelValidator productModelValidator;

    @Mock
    private ProductPersistenceValidator productPersistenceValidator;

    @Mock
    private ModelMappers modelMappers;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProducerRepository producerRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    public void test1() {

        ProducerDto producerDto = ProducerDto.builder()
                .id(2L)
                .name("ADAM")
                .tradeDto(TradeDto
                        .builder()
                        .id(1L)
                        .name("FOOD")
                        .build())
                .countryDto(CountryDto.
                        builder()
                        .id(1L)
                        .name("SPAIN").build())
                .build();

        Producer producer = Producer.builder()
                .id(2L)
                .name("ADAM")
                .trade(Trade
                        .builder()
                        .id(1L)
                        .name("FOOD")
                        .build())
                .country(Country.
                        builder()
                        .id(1L)
                        .name("SPAIN").build())
                .build();

        CategoryDto categoryDto = CategoryDto
                .builder()
                .id(1L)
                .name("A")
                .build();

        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("CH")
                .price(new BigDecimal(23))
                .categoryDto(categoryDto)
                .producerDto(producerDto)
                .guarantees(new HashSet<>(Arrays.asList(EGuarantee.EXCHANGE, EGuarantee.HELP_DESK)))
                .build();

        Category category = Category
                .builder()
                .id(1L)
                .name("A")
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("CH")
                .price(new BigDecimal(23))
                .producer(producer)
                .category(category)
                .guarantees(new HashSet<>(Arrays.asList(EGuarantee.EXCHANGE, EGuarantee.HELP_DESK)))
                .build();

        // given
        Mockito
                .when(productModelValidator.validateProductFields(productDto))
                .thenReturn(true);

        Mockito
                .when(producerModelValidator.validateProducerFields(producerDto))
                .thenReturn(true);
        Mockito
                .when(categoryModelValidator.validateCategoryFields(categoryDto))
                .thenReturn(true);

        Mockito.when(productModelValidator.validateProductFields(null))
                .thenReturn(false);
        Mockito
                .when(productPersistenceValidator.validateProductInsideDB(productDto))
                .thenReturn(true);

        Mockito.when(productPersistenceValidator.validateProductInsideDB(null))
                .thenReturn(false);

        Mockito.when(categoryRepository.findByName("AAAA")).thenThrow(new MyException(ExceptionCode.CATEGORY, "CATEGORY WITH GIVEN ID AND NAME NOT FOUND"));

        Mockito.when(categoryRepository.findByName("A")).thenReturn(Optional.ofNullable(category));

        Mockito.when(producerRepository.findByNameTradeCountry("ADAM", "FOOD", "SPAIN")).thenReturn(Optional.ofNullable(producer));

        Mockito.when(producerRepository.findOne(2L)).thenReturn(Optional.ofNullable(producer));

        Mockito
                .when(modelMappers.fromProductDtoToProduct(productDto))
                .thenReturn(product);

        Mockito.when(productRepository.saveOrUpdate(product))
                .thenReturn(product);

        Mockito.doThrow(new MyException(ExceptionCode.PRODUCT, "PRODUCT IS NULL"))
                .when(productRepository).saveOrUpdate(ArgumentMatchers.isNull());

        Mockito.when(producerPersistenceValidator.validateProducerInsideDB(producerDto))
                .thenReturn(true);


        // when
        Assertions
                .assertDoesNotThrow(() -> productService.addProduct(productDto));
    }
}

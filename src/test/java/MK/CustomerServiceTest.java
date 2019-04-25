package MK;

import MK.dto.CountryDto;
import MK.dto.CustomerDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.model.Customer;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.CustomerRepository;
import MK.service.CountryService;
import MK.service.CustomerService;
import MK.validator.impl.model.CountryModelValidator;
import MK.validator.impl.model.CustomerModelValidator;
import MK.validator.impl.persistence.CustomerPersistenceValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest {

    @Mock
    private CustomerModelValidator customerModelValidator;


    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CountryModelValidator countryModelValidator;

    @Mock
    private CustomerService customerService;
    @Mock
    private CountryService countryService;
    @Mock
    private ModelMappers modelMappers;

    @Mock
    private CustomerPersistenceValidator customerPersistenceValidator;

    @Test
    public void test1() {

        CountryDto countryDto = CountryDto.builder().name("CHINA").build();


        Country country = Country.builder()
                .name("CHINA")
                .build();


        Customer customer = Customer.builder()
                .surname("ALA")
                .surname("NOWAK")
                .age(22)
                .country(country)
                .build();

        CustomerDto customerDto = CustomerDto.builder()
                .surname("ALA")
                .surname("NOWAK")
                .age(22)
                .countryDto(countryDto)
                .build();

        // given
        Mockito
                .when(countryModelValidator.validateCountryFields(countryDto))
                .thenReturn(true);

        Mockito
                .when(customerModelValidator.validateCustomerFields(customerDto))
                .thenReturn(true);


        Mockito.when(customerPersistenceValidator.validateCustomerInsideDB(customerDto))
                .thenReturn(true);

        Mockito.when(countryRepository.findByName("CHINA")).thenThrow(new MyException(ExceptionCode.COUNTRY, "COUNTRY WITH GIVEN ID AND NAME NOT FOUND"));

        Mockito.when(customerRepository.findByName("ALA")).thenReturn(Optional.ofNullable(customer));


        Mockito
                .when(modelMappers.fromCustomerDtoToCustomer(customerDto))
                .thenReturn(customer);

        Mockito.when(customerRepository.saveOrUpdate(customer))
                .thenReturn(customer);

        Mockito.doThrow(new MyException(ExceptionCode.CUSTOMER, "CUSTOMER IS NULL"))
                .when(customerRepository).saveOrUpdate(ArgumentMatchers.isNull());



        // when
        Assertions
                .assertDoesNotThrow(() -> customerService.addCustomer(customerDto));
    }


}

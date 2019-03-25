package MK;

import MK.dto.CountryDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.repository.impl.CountryRepository;
import MK.service.CountryService;
import MK.validator.impl.model.CountryModelValidator;
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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)


public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryModelValidator countryModelValidator;

    @Mock
    private ModelMappers modelMappers;

    @InjectMocks
    private CountryService basicOperationCountry;

    @Test
    public void test1() {

        // given
        Mockito
                .when(countryModelValidator.validateCountryFields(CountryDto.builder().id(1L).name("POLAND").build()))
                .thenReturn(true);

        Mockito.when(countryModelValidator.validateCountryFields(null))
                .thenReturn(false);

        Mockito
                .when(modelMappers.fromCountryDtoToCountry(CountryDto.builder().id(1L).name("POLAND").build()))
                .thenReturn(Country.builder().id(1L).name("POLAND").build());

        Mockito.when(countryRepository.saveOrUpdate(Country.builder().name("POLAND").build()))
                .thenReturn(Country.builder().id(1L).name("POLAND").build());

        Mockito.doThrow(new MyException(ExceptionCode.COUNTRY, "COUNTRY IS NULL"))
                .when(countryRepository).saveOrUpdate(ArgumentMatchers.isNull());

        // when
        Assertions
                .assertDoesNotThrow(() -> basicOperationCountry.addCountry(CountryDto.builder().id(1L).name("POLAND").build()));
    }

}


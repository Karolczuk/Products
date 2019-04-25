import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.CountryRepositoryImpl;
import MK.service.CountryService;
import MK.service.StreamOperation;
import MK.validator.impl.model.CountryModelValidator;
import java.math.BigDecimal;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {

        CountryModelValidator countryModelValidator = new CountryModelValidator();
        ModelMappers modelMapper = new ModelMappers();
        CountryRepository countryRepository = new CountryRepositoryImpl();
        StreamOperation streamOperation = new StreamOperation();
        CountryService countryService = new CountryService(countryRepository, countryModelValidator, modelMapper);

        Country country = Country.builder().name("SPAIN").build();
        countryService.addCountry(modelMapper.fromCountryToCountryDto(country));


        System.out.println(streamOperation.createMapCategoryTheMostExpesiveProduct());


        System.out.println(" ///////////////////////");

        System.out.println(streamOperation.getOrdersBetweenDates(LocalDate.now().minusDays(22),LocalDate.now().plusDays(34), BigDecimal.ZERO));
    }
}

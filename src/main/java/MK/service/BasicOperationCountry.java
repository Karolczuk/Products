package MK.service;

import MK.dto.CountryDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.CustomerRepository;
import MK.repository.impl.ShopRepository;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationCountry {
    private final CountryRepository countryRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationCountry(
            CountryRepository countryRepository,
            CustomerRepository customerRepository,
            ShopRepository shopRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.countryRepository = countryRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }

    public void addCountry(CountryDto countryDto) {

        if (countryDto == null) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateCountryFields(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateCountryInsideDB(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY ALREADY EXISTS");
        }

        Country country = modelMapper.fromCountryDtoToCountry(countryDto);

        countryRepository.saveOrUpdate(country);


    }
}








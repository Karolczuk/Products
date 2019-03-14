package MK.service;

import MK.dto.CountryDto;
import MK.dto.CustomerDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.model.Customer;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.CountryRepositoryImpl;
import MK.repository.impl.CustomerRepository;
import MK.repository.impl.CustomerRepositoryImpl;
import MK.validator.ManagmentProductsValidator;

public class BasicOperation {
    private final CountryRepository countryRepository/* = new CountryRepositoryImpl()*/;
    private final CustomerRepository customerRepository/* = new CustomerRepositoryImpl()*/;
    private final ManagmentProductsValidator managmentProductsValidator/* = new ManagmentProductsValidator()*/;
    private final ModelMappers modelMapper/* = new ModelMappers()*/;

    public BasicOperation(
            CountryRepository countryRepository,
            CustomerRepository customerRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.countryRepository = countryRepository;
        this.customerRepository = customerRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }

    public void addCustomer(CustomerDto customerDto) {

        // -----------------------------------------------------------------------------------
        // ------------------------------ CUSTOMER VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------
        if (customerDto == null) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateCustomerFields(customerDto)) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateCustomerInsideDB(customerDto)) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER ALREADY EXISTS");
        }

        // -----------------------------------------------------------------------------------
        // ------------------------------- COUNTRY VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        CountryDto countryDto = customerDto.getCountryDto();

        if (countryDto == null) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY OBJECT IS NULL");
        }

        if (countryDto.getId() == null && countryDto.getName() == null) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateCountryFields(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateCountryInsideDB(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY NOT FOUND");
        }


        // -----------------------------------------------------------------------------------
        // ----------------------------------- INSERT INTO DB --------------------------------
        // -----------------------------------------------------------------------------------
        Customer customer = modelMapper.fromCustomerDtoToCustomer(customerDto);

        Country country = null;

        if (countryDto.getId() != null) {
            country = countryRepository
                    .findOne(countryDto.getId())
                    .orElse(null);
        }

        if (country == null) {
            country = countryRepository
                    .findByName(countryDto.getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.COUNTRY, "COUNTRY WITH GIVEN ID AND NAME NOT FOUND"));
        }

        customer.setCountry(country);
        customerRepository.saveOrUpdate(customer);
    }


}


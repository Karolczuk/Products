package MK.service;

import MK.dto.CountryDto;
import MK.dto.CustomerDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.Country;
import MK.model.Customer;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.CustomerRepository;
import MK.validator.impl.model.CountryModelValidator;
import MK.validator.impl.model.CustomerModelValidator;
import MK.validator.impl.persistence.CustomerPersistenceValidator;

public class CustomerService {
    private final CountryRepository countryRepository/* = new CountryRepositoryImpl()*/;
    private final CustomerRepository customerRepository/* = new CustomerRepositoryImpl()*/;
    private final ModelMappers modelMapper/* = new ModelMappers()*/;
    private final CustomerModelValidator customerModelValidator;
    private final CountryModelValidator countryModelValidator;
    private CustomerPersistenceValidator customerPersistenceValidator;

    public CustomerService(
            CountryRepository countryRepository,
            CustomerRepository customerRepository,
            ModelMappers modelMapper,
            CustomerModelValidator customerModelValidator,
            CustomerPersistenceValidator customerPersistenceValidator,
            CountryModelValidator countryModelValidator) {
        this.countryRepository = countryRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.customerModelValidator = customerModelValidator;
        this.customerPersistenceValidator = customerPersistenceValidator;
        this.countryModelValidator = countryModelValidator;

    }

    public void addCustomer(CustomerDto customerDto) {

        // -----------------------------------------------------------------------------------
        // ------------------------------ CUSTOMER VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------
        if (customerDto == null) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER OBJECT IS NULL");
        }

        if (!customerModelValidator.validateCustomerFields(customerDto)) {
            throw new MyException(ExceptionCode.CUSTOMER, "CUSTOMER FIELDS ARE NOT VALID");
        }

        if (customerPersistenceValidator.validateCustomerInsideDB(customerDto)) {
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

        if (!countryModelValidator.validateCountryFields(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY FIELDS ARE NOT VALID");
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


package MK.validator.impl.persistence;

import MK.dto.CustomerDto;
import MK.repository.impl.CustomerRepository;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class CustomerPersistenceValidator implements Validator<CustomerDto> {


    private CustomerRepository customerRepository;
    private Map<String, String> errors = new HashMap<>();


    public CustomerPersistenceValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public boolean validateCustomerInsideDB(CustomerDto customer) {
        return customerRepository.findByNameSurnameCountry(
                customer.getName(),
                customer.getSurname(),
                customer.getCountryDto().getName()
        ).isPresent();
    }

    @Override
    public Map<String, String> validate(CustomerDto customer) {
        errors.clear();

        if (customer == null) {
            errors.put("customer", "customer object is null");
        }

        if (!validateCustomerInsideDB(customer)) {
            errors.put("model", "customer model is not correct: " + customer);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

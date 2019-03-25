package MK.validator.impl.model;

import MK.dto.CustomerDto;
import MK.validator.Validator;
import java.util.HashMap;
import java.util.Map;

public class CustomerModelValidator implements Validator<CustomerDto> {

    private Map<String, String> errors = new HashMap<>();


    public boolean validateCustomerFields(CustomerDto customer) {
        final String REGEXP = "[A-Z ]+";

        return customer.getName() != null &&
                customer.getSurname() != null &&
                customer.getName().matches(REGEXP) &&
                customer.getSurname().matches(REGEXP) &&
                customer.getAge() >= 18;
    }


    @Override
    public Map<String, String> validate(CustomerDto customer) {
        errors.clear();

        if (customer == null) {
            errors.put("customer", "customer object is null");
        }

        if (!validateCustomerFields(customer)) {
            errors.put("model", "customer model is not correct: " + customer);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

package MK.validator.impl.model;

import MK.dto.CustomerOrderDto;
import MK.validator.Validator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CustomerOrderModelValidator implements Validator<CustomerOrderDto> {
    private Map<String, String> errors = new HashMap<>();


    public boolean validateCustomerOrderFields(CustomerOrderDto customerOrderDto) {


        return customerOrderDto.getDate().equals(LocalDate.now()) ||
                customerOrderDto.getDate().isAfter(LocalDate.now()) &&
                        customerOrderDto.getDiscount().compareTo(BigDecimal.ZERO) > 0 &&
                        customerOrderDto.getDiscount().compareTo(new BigDecimal(1)) < 1;
    }

    @Override
    public Map<String, String> validate(CustomerOrderDto customerOrder) {
        errors.clear();

        if (customerOrder== null) {
            errors.put("category", "customerOrder object is null");
        }

        if (!validateCustomerOrderFields(customerOrder)) {
            errors.put("model", "customerOrder model is not correct: " + customerOrder);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

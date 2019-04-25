package MK.validator.impl.model;

import MK.dto.CountryDto;
import MK.validator.Validator;
import java.util.HashMap;
import java.util.Map;

public class CountryModelValidator implements Validator<CountryDto> {


    private Map<String, String> errors = new HashMap<>();

    public boolean validateCountryFields(CountryDto country) {
        final String REGEXP = "[A-Z ]+";
        return country.getName() != null && country.getName().matches(REGEXP);
    }

    @Override
    public Map<String, String> validate(CountryDto country) {

        errors.clear();

        if (country == null) {
            errors.put("country", "country object is null");
        }

        if (!validateCountryFields(country)) {
            errors.put("model", "country model is not correct: " + country);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

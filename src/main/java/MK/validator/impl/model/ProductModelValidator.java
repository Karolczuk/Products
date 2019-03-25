package MK.validator.impl.model;

import MK.dto.ProductDto;
import MK.validator.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductModelValidator implements Validator<ProductDto> {

    private Map<String, String> errors = new HashMap<>();

    public boolean validateProductFields(ProductDto productDto) {
        final String REGEXP = "[A-Z ]+";
        return productDto.getName() != null &&
                productDto.getName().matches(REGEXP) &&
                productDto.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public Map<String, String> validate(ProductDto product) {
        if (product == null) {
            errors.put("product", "product object is null");
        }

        if (!validateProductFields(product)) {
            errors.put("model", "product model is not correct: " + product);
        }
        return errors;    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

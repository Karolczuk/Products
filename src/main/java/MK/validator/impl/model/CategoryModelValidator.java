package MK.validator.impl.model;

import MK.dto.CategoryDto;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class CategoryModelValidator implements Validator<CategoryDto> {
    private Map<String, String> errors = new HashMap<>();



    public boolean validateCategoryFields(CategoryDto categoryDto) {
        final String REGEXP = "[A-Z ]+";

        return categoryDto.getName() != null &&
                categoryDto.getName().matches(REGEXP);
    }

    @Override
    public Map<String, String> validate(CategoryDto category) {

        errors.clear();

        if (category== null) {
            errors.put("category", "category object is null");
        }

        if (!validateCategoryFields(category)) {
            errors.put("model", "category model is not correct: " + category);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

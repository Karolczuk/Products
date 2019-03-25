package MK.validator.impl.persistence;

import MK.dto.ProductDto;
import MK.repository.impl.ProductRepository;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class ProductPersistenceValidator implements Validator<ProductDto> {

    private Map<String, String> errors = new HashMap<>();
    private ProductRepository productRepository;

    public ProductPersistenceValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean validateProductInsideDB(ProductDto productDto) {
        return productRepository.findByNameCategoryProducer(
                productDto.getName(),
                productDto.getCategoryDto().getName(),
                productDto.getProducerDto().getName()
        ).isPresent();
    }

    @Override
    public Map<String, String> validate(ProductDto product) {
        errors.clear();

        if (product == null) {
            errors.put("customer", "product object is null");
        }

        if (!validateProductInsideDB(product)) {
            errors.put("model", "product model is not correct: " + product);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

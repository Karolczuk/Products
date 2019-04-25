package MK.converter;
import MK.dto.ProductDto;
import MK.model.Product;

import java.util.List;

public class ProductJsonConverter extends JsonConverter<List<ProductDto>>  {
    public ProductJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

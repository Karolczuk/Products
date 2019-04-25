package MK.converter;

import MK.dto.CategoryDto;

import java.util.List;

public class CategoryJsonConverter extends JsonConverter<List<CategoryDto>> {

        public CategoryJsonConverter(String jsonFilename) {
            super(jsonFilename);
        }
}

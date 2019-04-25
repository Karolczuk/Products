package MK.converter;

import MK.dto.CountryDto;
import MK.model.Country;

import java.util.List;

public class CountryJsonConverter extends JsonConverter<List<CountryDto>> {

    public CountryJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}


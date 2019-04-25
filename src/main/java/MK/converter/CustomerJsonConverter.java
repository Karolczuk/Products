package MK.converter;
import MK.dto.CustomerDto;
import MK.model.Customer;

import java.util.List;

public class CustomerJsonConverter extends JsonConverter<List<CustomerDto>>  {
    public CustomerJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

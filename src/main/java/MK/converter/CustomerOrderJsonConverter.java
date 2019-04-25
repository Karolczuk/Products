package MK.converter;
import MK.dto.CustomerOrderDto;

import java.util.List;

public class CustomerOrderJsonConverter extends JsonConverter<List<CustomerOrderDto>>  {
    public CustomerOrderJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

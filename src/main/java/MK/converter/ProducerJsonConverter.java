package MK.converter;
import MK.dto.ProducerDto;
import MK.model.Producer;

import java.util.List;

public class ProducerJsonConverter extends JsonConverter<List<ProducerDto>>  {
    public ProducerJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

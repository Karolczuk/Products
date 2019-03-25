package MK.validator.impl.model;

import MK.dto.ProducerDto;
import MK.validator.Validator;
import java.util.HashMap;
import java.util.Map;

public class ProducerModelValidator implements Validator<ProducerDto> {

    private Map<String, String> errors = new HashMap<>();


    public boolean validateProducerFields(ProducerDto producerDto) {
        final String REGEXP = "[A-Z ]+";
        return producerDto.getName() != null &&
                producerDto.getName().matches(REGEXP);
    }

    @Override
    public Map<String, String> validate(ProducerDto producer) {
        if (producer == null) {
            errors.put("producer", "producer object is null");
        }

        if (!validateProducerFields(producer)) {
            errors.put("model", "producer model is not correct: " + producer);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

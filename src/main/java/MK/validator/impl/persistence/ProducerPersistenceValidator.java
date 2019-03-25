package MK.validator.impl.persistence;

import MK.dto.ProducerDto;
import MK.repository.impl.ProducerRepository;
import MK.validator.Validator;
import java.util.HashMap;
import java.util.Map;

public class ProducerPersistenceValidator implements Validator<ProducerDto> {


    private ProducerRepository producerRepository;
    private Map<String, String> errors = new HashMap<>();


    public ProducerPersistenceValidator(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }


    public boolean validateProducerInsideDB(ProducerDto producerDto) {
        return producerRepository.findByNameTradeCountry(
                producerDto.getName(),
                producerDto.getTradeDto().getName(),
                producerDto.getCountryDto().getName()
        ).isPresent();
    }

    @Override
    public Map<String, String> validate(ProducerDto producer) {
        errors.clear();

        if (producer == null) {
            errors.put("customer", "customer object is null");
        }

        if (!validateProducerInsideDB(producer)) {
            errors.put("model", "customer model is not correct: " + producer);
        }
        return errors;

    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}



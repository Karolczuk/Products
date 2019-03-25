package MK.repository.impl;

import MK.model.Producer;
import MK.repository.generic.GenericRepository;

import java.util.Optional;

public interface ProducerRepository extends GenericRepository<Producer> {
    Optional<Producer> findByNameTradeCountry(String name, String tradeName, String countryName);
}

package MK.repository.impl;



import MK.model.Country;
import MK.repository.generic.GenericRepository;

import java.util.Optional;

public interface CountryRepository extends GenericRepository<Country> {
    Optional<Country> findByName(String name);
}

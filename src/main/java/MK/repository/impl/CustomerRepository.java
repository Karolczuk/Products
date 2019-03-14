package MK.repository.impl;

import MK.model.Country;
import MK.model.Customer;
import MK.repository.generic.GenericRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer> {
    Optional<Customer> findByName(String name);

    Optional<Customer> findByNameSurnameCountry(String name, String surname, String countryName);

    Optional<Customer> findByNameSurnameAgeCountry(String name);


}

package MK.repository.impl;
import MK.model.Shop;
import MK.repository.generic.GenericRepository;

import java.util.Optional;

public interface ShopRepository extends GenericRepository<Shop> {

    Optional<Shop> findByName(String shopName, String countryName);

}

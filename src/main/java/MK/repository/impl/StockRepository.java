package MK.repository.impl;
import MK.model.Stock;
import MK.repository.generic.GenericRepository;

import java.util.Optional;

public interface StockRepository extends GenericRepository<Stock> {
    Optional<Stock> findByName(String productName, String categoryName,String shopName, String countryName);

}

package MK.repository.impl;

import MK.model.Product;
import MK.repository.generic.GenericRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends GenericRepository<Product> {

    Optional<Product> findByNameCategoryProducer(String name, String categoryName, String producerName);

    Optional<Product> findByName(String name);

    List<Product> findAllWithGuarantees();

    List<Product> findAllWithStocks();

    public List<Product> findAlllWithCategories();

}

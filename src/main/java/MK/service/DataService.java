package MK.service;


import MK.model.Category;
import MK.model.Product;
import MK.repository.impl.CountryRepositoryImpl;
import MK.repository.impl.CustomerRepositoryImpl;
import MK.repository.impl.ProductRepositoryImpl;

import java.util.Map;
import java.util.stream.Collectors;

public class DataService {
    private CountryRepositoryImpl countryRepository = new CountryRepositoryImpl();
    private CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
    private ProductRepositoryImpl productRepository = new ProductRepositoryImpl();

    public void showTheMostExpensiveProduct() {
        Map<Category, Product> mapCategoryProduct = productRepository.findAll()
                .stream()
                .collect(Collectors.toMap(p->p.getCategory(), c->c));


    }


}

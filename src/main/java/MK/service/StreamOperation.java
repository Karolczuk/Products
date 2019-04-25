package MK.service;
import MK.dto.CategoryDto;
import MK.dto.ProductDto;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StreamOperation {

    ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
    private CustomerOrderRepositoryImpl customerOrderRepository = new CustomerOrderRepositoryImpl();
    private CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();
    private ModelMappers modelMappers = new ModelMappers();


    public Map<Category, Product> createMapCategoryTheMostExpesiveProduct() {
        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .collect(Collectors.groupingBy(s -> s.getCategory()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()
                        .stream()
                        .sorted(Comparator.comparing(s -> s.getPrice(), Comparator.reverseOrder()))
                        //.collect(Collectors.toList());
                        .findFirst()
                        .orElseGet(null)
                ));
    }


    public Map<CategoryDto, List<ProductDto>> productsWithGuaranteeServices(EGuarantee... guarantees) {
        return productRepository
                .findAllWithGuarantees()
                .stream()
                .filter(p -> p.getGuarantees().containsAll(Arrays.asList(guarantees)))
                .map(modelMappers::fromProductToProductDto)
                .collect(Collectors.groupingBy(ProductDto::getCategoryDto));
    }


    public long findQuantityOfManufacturedProducts(Producer producer) {

        List<Product> products = productRepository.findAllWithStocks()
                .stream()
                .filter(s -> s.getProducer().equals(producer))
                .collect(Collectors.toList());


        List<Stock> stocks = products.stream()
                .map(s -> s.getStocks())
                .flatMap(Set::stream)
                .collect(Collectors.toList());

        long suma = 0;
        for (Product p : products) {
            Long ile = stocks
                    .stream()
                    .filter(s -> s.getProduct().equals(p))
                    .map(s -> s.getQuantity())
                    .count();
            suma += ile;
        }
        return suma;

    }


    public List<Producer> getListOfProducersByParameters(String tradeName, int userQuantity) {
        List<Producer> producers = producerRepository.findAll();

        return producers
                .stream()
                .filter(p -> p.getTrade().getName().equals(tradeName) && findQuantityOfManufacturedProducts(p) >= userQuantity)
                .collect(Collectors.toList());

    }

    public Set<Product> getListOfProductByParameters(Country country, Integer ageBefore, Integer ageAfter) {

        return customerRepository.findAll()
                .stream()
                .filter(c -> c.getAge() <= ageAfter && c.getAge() >= ageBefore && c.getCountry().getName().equals(country.getName()))
                .map(Customer::getCustomerOrders)
                //    .map(c->c.getCustomerOrders())
                //.flatMap(x->x.stream())
                .flatMap(Set::stream)// x-> x.stream, flatMap
                //.map(c->c.getProduct())
                .map(CustomerOrder::getProduct)
                .sorted(Comparator.comparingDouble(p -> p.getPrice().doubleValue()))
                .collect(Collectors.toSet());


    }


    public List<CustomerOrder> getOrdersBetweenDates(LocalDate fromDate, LocalDate toDate, BigDecimal price){
      return   customerOrderRepository.findAll()
                .stream()
                .filter(s->s.getProduct().getPrice().subtract(s.getDiscount()).compareTo(price)>0
                && s.getDate().isAfter(fromDate) && s.getDate().isBefore(toDate))
                .collect(Collectors.toList());

    }


    public List<Customer> getListOfProductByParameters() {

        return customerRepository.findAll()
                .stream()
                .filter(s -> {
                            List<Long> customerOrdersId = s.getCustomerOrders()
                                    .stream()
                                    .map(customerOrder -> customerOrder.getProduct().getId())
                                    .collect(Collectors.toList());
                            List<Product> products = productRepository.findAllWithStocks()
                                    .stream()
                                    .filter(ss -> customerOrdersId.contains(ss.getId()))
                                    .collect(Collectors.toList());

                            Long count = products
                                    .stream()
                                    .filter(p -> p.getStocks()
                                            .stream()
                                            .filter(pp -> !pp.getShop().getCountry().getName().equals(s.getCountry().getName()))
                                            .findAny()
                                            .isPresent())
                                    .count();
                            s.setCountProductByCountry(count);

                            products = products
                                    .stream()
                                    .filter(p -> p.getStocks()
                                            .stream()
                                            .filter(pp -> pp.getShop().getCountry().getName().equals(s.getCountry().getName()))
                                            .findAny()
                                            .isPresent())
                                    .collect(Collectors.toList());

                            return !products.isEmpty();
                        }

                )
                .collect(Collectors.toList());
    }



}




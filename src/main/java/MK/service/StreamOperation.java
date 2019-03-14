package MK.service;

import MK.model.*;
import MK.repository.impl.*;
import MK.repository.impl.CustomerOrderRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StreamOperation {

    ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
    private CustomerOrderRepositoryImpl customerOrderRepository = new CustomerOrderRepositoryImpl();
    private GuaranteeRepositoryImpl guaranteeRepository = new GuaranteeRepositoryImpl();
    private CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();

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


    public void showOrderedProducts() {
        Scanner sc = new Scanner(System.in);
        String country = sc.nextLine();
        int ageMin = sc.nextInt();
        int ageMax = sc.nextInt();

        List<Product> products = productRepository.findAll();
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();

        List<MK.model.CustomerOrder> customerOrderList = products.stream()
                .filter(s -> s.getProducer().getCountry().equals(country))
                // .sorted(Comparator.comparing(s -> s.getPrice(), Comparator.reverseOrder()))
                .flatMap(s -> s.getCustomerOrders()
                        // .stream()
                        //    .collect(Collectors.toList())
                        .stream()
                        .filter(p -> p.getCustomer().getAge() >= ageMin && p.getCustomer().getAge() <= ageMax)
                )
                .collect(Collectors.toList());

        System.out.println(customerOrderList);
    }

    public void productsAboutAnothetCountryThanShop() {
        List<Shop> shops = shopRepository.findAll();

        shops.stream()
                .peek(s -> s.getStocks()
                        .stream()
                        .filter(ss -> !ss.getProduct().getProducer().getCountry().equals(s.getCountry())))
                .collect(Collectors.toList());
    }



    public void showProductsAboutSpecificName(String name, int number) {
        List<Producer> producers = producerRepository.findAll();

        producers
                .stream()
                .filter(t -> t.getTrade().getName().equals(name))
                .peek(s -> s.getProducts().stream().peek(sss -> sss.getStocks().stream().filter(ss -> ss.getQuantity() >= number)))
                .forEach(s -> System.out.println(s));


        List<Stock> stocks = stockRepository.findAll();

        stocks.stream()
                .filter(s -> s.getProduct().getProducer().equals(name))
                .filter((m1, m2) -> (Integer.sum(m1, m2)))
                .map(s -> s.getQuantity())
                .reduce(0, (s1, s2) -> s1 + s2);

    }


}

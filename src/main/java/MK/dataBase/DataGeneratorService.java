package MK.dataBase;

import MK.converter.*;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.service.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataGeneratorService {

    private final CountryService countryService;
    private final ShopService shopService;
    private final TradeService tradeService;
    private final ProductService productService;
    private final ProducerService producerService;
    private final CustomerService customerService;
    private final CategoryService categoryService;
    private final StockService stockService;
    private final CustomerOrderService customerOrderService;


    public void initializeData() {

        initializeCountries();
        initializeShops();
        initializeTrade();
        initializeProducer();
        initializeCustomer();
        initializeCategory();
        initializeProduct();
        initializeStock();
        initializeCustomerOrder();

    }

//    private void deleteAll() {
//        countryService.deleteAll();
//    }

    private void initializeCountries() {
        CountryJsonConverter countriesJsonConverter = new CountryJsonConverter("countries.json");
        countriesJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN COUNTRIES INITIALIZATION"))
                .forEach(countryService::addCountry);
    }


    private void initializeShops() {
        ShopJsonConverter shopJsonConverter = new ShopJsonConverter("shops.json");
        shopJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN SHOPS INITIALIZATION"))
                .forEach(shopService::addShop);
    }


    private void initializeTrade() {
        TradeJsonConverter tradeJsonConverter = new TradeJsonConverter("trades.json");
        tradeJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN TRADES INITIALIZATION"))
                .forEach(tradeService::addTrade);
    }


    private void initializeProducer() {
        ProducerJsonConverter producerJsonConverter = new ProducerJsonConverter("producers.json");
        producerJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN PRODUCERS INITIALIZATION"))
                .forEach(producerService::addProducer);
    }


    private void initializeCustomer() {
        CustomerJsonConverter customerJsonConverter = new CustomerJsonConverter("customers.json");
        customerJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN CUSTOMERS INITIALIZATION"))
                .forEach(customerService::addCustomer);
    }


    private void initializeCategory() {
        CategoryJsonConverter categoryJsonConverter = new CategoryJsonConverter("categories.json");
        categoryJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN CATEGORIES INITIALIZATION"))
                .forEach(categoryService::addCategory);
    }


    private void initializeProduct() {
        ProductJsonConverter productJsonConverter = new ProductJsonConverter("products.json");
        productJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN PRODUCTS INITIALIZATION"))
                .forEach(productService::addProduct);
    }


    private void initializeStock() {
        StockJsonConverter stockJsonConverter = new StockJsonConverter("stocks.json");
        stockJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN STOCKS INITIALIZATION"))
                .forEach(stockService::addStock);
    }


    private void initializeCustomerOrder() {
        CustomerOrderJsonConverter customerOrderJsonConverter = new CustomerOrderJsonConverter("customer_orders.json");
        customerOrderJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.CONVERTER, "EXCEPTION IN CUSTOMER_ORDERS INITIALIZATION"))
                .forEach(customerOrderService::addCustomerOrder);
    }
}



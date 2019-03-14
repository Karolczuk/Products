package MK.validator;

import MK.dto.*;
import MK.repository.impl.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ManagmentProductsValidator {

    private final CustomerRepositoryImpl customerRepository/* = new CustomerRepositoryImpl()*/;
    private final CategoryRepository categoryRepository/* = new CategoryRepositoryImpl()*/;
    private final CountryRepository countryRepository/* = new CountryRepositoryImpl()*/;
    private final TradeRepository tradeRepository/* = new TradeRepositoryImpl()*/;
    private final PaymentRepository paymentRepository/* = new PaymentRepositoryImpl()*/;
    private final ErrorRepositoryImpl errorRepository/* = new ErrorRepositoryImpl()*/;
    private final ProducerRepository producerRepository;
    private final GuaranteeRepository guaranteeRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;

    public ManagmentProductsValidator(
            CustomerRepositoryImpl customerRepository,
            CategoryRepository categoryRepository,
            CountryRepository countryRepository,
            TradeRepository tradeRepository,
            PaymentRepository paymentRepository,
            ErrorRepositoryImpl errorRepository,
            GuaranteeRepository guaranteeRepository,
            ProducerRepository producerRepository,
            ProductRepository productRepository,
            StockRepository stockRepository,
            ShopRepository shopRepository
    ) {
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.countryRepository = countryRepository;
        this.tradeRepository = tradeRepository;
        this.paymentRepository = paymentRepository;
        this.errorRepository = errorRepository;
        this.producerRepository = producerRepository;
        this.guaranteeRepository = guaranteeRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.shopRepository = shopRepository;
    }

    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // ---------------------------------- CUSTOMER VALIDATION -------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    public boolean validateCustomerFields(CustomerDto customer) {
        final String REGEXP = "[A-Z ]+";

        return customer.getName() != null &&
                customer.getSurname() != null &&
                customer.getName().matches(REGEXP) &&
                customer.getSurname().matches(REGEXP) &&
                customer.getAge() >= 18;
    }

    public boolean validateCustomerInsideDB(CustomerDto customer) {
        return customerRepository.findByNameSurnameCountry(
                customer.getName(),
                customer.getSurname(),
                customer.getCountryDto().getName()
        ).isPresent();
    }

    public boolean validateCustomer(CustomerDto customerDto) {
        return validateCustomerFields(customerDto) && validateCustomerInsideDB(customerDto);
    }

    public boolean validatePaymentInsideDB(PaymentDto paymentDto) {
        return paymentRepository.findByName(
                paymentDto.getEpayment()).isPresent();
    }


    public boolean validateTradeFields(TradeDto tradeDto) {
        final String REGEXP = "[A-Z ]+";

        return tradeDto.getName() != null &&
                tradeDto.getName().matches(REGEXP);
    }

    public boolean validateTradeInsideDB(TradeDto tradeDto) {
        return customerRepository.findByName(
                tradeDto.getName()
        ).isPresent();
    }

    public boolean validateTrade(TradeDto tradeDto) {
        return validateTradeFields(tradeDto) && validateTradeInsideDB(tradeDto);
    }

    public boolean validateGuaranteeInsideDB(GuaranteeComponentsDto guaranteeComponentsDto) {
        return guaranteeRepository.findByName(
                guaranteeComponentsDto.getEGuarantee()).isPresent();
    }


    public boolean validateProducerFields(ProducerDto producerDto) {
        final String REGEXP = "[A-Z ]+";
        return producerDto.getName() != null &&
                producerDto.getName().matches(REGEXP);
    }

    public boolean validateProducerInsideDB(ProducerDto producerDto) {
        return producerRepository.findByNameTradeCountry(
                producerDto.getName(),
                producerDto.getTradeDto().getName(),
                producerDto.getCountryDto().getName()
        ).isPresent();
    }

    public boolean validateProducer(ProducerDto producerDto) {
        return validateProducerFields(producerDto) && validateProducerInsideDB(producerDto);
    }


    public boolean validateProductFields(ProductDto productDto) {
        final String REGEXP = "[A-Z ]+";
        return productDto.getName() != null &&
                productDto.getName().matches(REGEXP) &&
                productDto.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean validateProductInsideDB(ProductDto productDto) {
        return productRepository.findByNameCategoryProducer(
                productDto.getName(),
                productDto.getCategoryDto().getName(),
                productDto.getProducerDto().getName()
        ).isPresent();
    }

    public boolean validateProduct(ProductDto productDto) {
        return validateProductFields(productDto) && validateProductInsideDB(productDto);
    }

    public boolean validateCategoryFields(CategoryDto categoryDto) {
        final String REGEXP = "[A-Z ]+";

        return categoryDto.getName() != null &&
                categoryDto.getName().matches(REGEXP);
    }

    public boolean validateCategoryInsideDB(CategoryDto categoryDto) {
        return categoryRepository.findByName(
                categoryDto.getName()
        ).isPresent();
    }

    public boolean validateCategory(CategoryDto categoryDto) {
        return validateCategoryFields(categoryDto) && validateCategoryInsideDB(categoryDto);
    }


    public boolean validateStockFields(StockDto stockDto) {
        return
                stockDto.getQuantty() >= 0;
    }

    public boolean validateStockInsideDB(StockDto stockDto) {
        return stockRepository.findByName(
                stockDto.getProductDto().getName(),
                stockDto.getProductDto().getCategoryDto().getName(),
                stockDto.getShopDto().getName(),
                stockDto.getShopDto().getCountryDto().getName()
        ).isPresent();
    }

    public boolean validateStock(StockDto stockDto) {
        return validateStockFields(stockDto) && validateStockInsideDB(stockDto);
    }


    public boolean validateShopFields(ShopDto shopDto) {
        final String REGEXP = "[A-Z ]+";

        return shopDto.getName() != null &&
                shopDto.getName().matches(REGEXP);
    }

    public boolean validateShopInsideDB(ShopDto shopDto) {
        return shopRepository.findByName(
                shopDto.getName(),
                shopDto.getCountryDto().getName()
        ).isPresent();
    }

    public boolean validateShop(ShopDto shopDto) {
        return validateShopFields(shopDto) && validateShopInsideDB(shopDto);
    }


    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------
    // ----------------------------------- COUNTRY VALIDATION -------------------------------------
    // --------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------

    public boolean validateCountryFields(CountryDto country) {
        final String REGEXP = "[A-Z ]+";
        return country.getName() != null && country.getName().matches(REGEXP);
    }

    public boolean validateCountryInsideDB(CountryDto country) {
        return countryRepository.findByName(country.getName()).isPresent();
    }

    public boolean validateCountry(CountryDto country) {
        return validateCountryFields(country) && validateCountryInsideDB(country);
    }


    public boolean validateCustomerOrderFields(CustomerOrderDto customerOrderDto) {


        return customerOrderDto.getDate().equals(LocalDate.now()) ||
                customerOrderDto.getDate().isAfter(LocalDate.now()) &&
                        customerOrderDto.getDiscount() >= 0 &&
                        customerOrderDto.getDiscount() <= 1;
    }

    public boolean validaterCustomerOrderInsideDB(CustomerOrderDto customerOrderDto) {

        return stockRepository.findAll()
                .stream()
                .filter(s -> s.getProduct().getName().equals(customerOrderDto.getProductDto().getName()))
                .filter(s -> s.getQuantity() >= customerOrderDto.getQuantity())
                .findFirst()
                .isPresent();
    }

    public boolean validateCustomerOrde(CustomerOrderDto customerOrderDto) {
        return validateCustomerOrderFields(customerOrderDto) && validaterCustomerOrderInsideDB(customerOrderDto);
    }


}

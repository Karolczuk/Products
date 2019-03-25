    package MK.mappers;

    import MK.dto.*;
    import MK.model.*;

    import java.util.HashSet;

    public class ModelMappers {

        public CountryDto fromCountryToCountryDto(Country country) {
            return country == null ? null : CountryDto.builder()
                    .id(country.getId())
                    .name(country.getName())
                    .build();
        }

        public Country fromCountryDtoToCountry(CountryDto countryDto) {
            return countryDto == null ? null : Country.builder()
                    .id(countryDto.getId())
                    .name(countryDto.getName())
                    .producers(new HashSet<>())
                    .customers(new HashSet<>())
                    .shops(new HashSet<>())
                    .build();
        }

        public ShopDto fromShopToShopDto(Shop shop) {
            return shop == null ? null : ShopDto.builder()
                    .id(shop.getId())
                    .name(shop.getName())
                    .countryDto(shop.getCountry() == null ? null : fromCountryToCountryDto(shop.getCountry()))
                    .build();
        }

        public Shop fromShopDtoToShop(ShopDto shopDto) {
            return shopDto == null ? null : Shop.builder()
                    .id(shopDto.getId())
                    .name(shopDto.getName())
                    .stocks(new HashSet<>())
                    .country(shopDto.getCountryDto() == null ? null : fromCountryDtoToCountry(shopDto.getCountryDto()))
                    .build();
        }

        public CategoryDto fromCategoryToCategoryDto(Category category) {
            return category == null ? null : CategoryDto.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
        }

        public Category fromCategoryDtoToCategory(CategoryDto categoryDto) {
            return categoryDto == null ? null : Category.builder()
                    .id(categoryDto.getId())
                    .name(categoryDto.getName())
                    .products(new HashSet<>())
                    .build();
        }

        public CustomerDto fromCustomerToCustomerDto(Customer customer) {
            return customer == null ? null : CustomerDto.builder()
                    .id(customer.getId())
                    .age(customer.getAge())
                    .name(customer.getName())
                    .surname(customer.getSurname())
                    .countryDto(customer.getCountry() == null ? null : fromCountryToCountryDto(customer.getCountry()))
                    .build();
        }

        public Customer fromCustomerDtoToCustomer(CustomerDto customerDto) {
            return customerDto == null ? null : Customer.builder()
                    .id(customerDto.getId())
                    .age(customerDto.getAge())
                    .name(customerDto.getName())
                    .surname(customerDto.getSurname())
                    .customerOrders(new HashSet<>())
                    .country(customerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(customerDto.getCountryDto()))
                    .build();
        }

        public CustomerOrderDto fromCustomerOrderToCustomerOrderDto(CustomerOrder customerOrder) {
            return customerOrder == null ? null : CustomerOrderDto.builder()
                    .id(customerOrder.getId())
                    .name(customerOrder.getName())
                    .date(customerOrder.getDate())
                    .discount(customerOrder.getDiscount())
                    .quantity(customerOrder.getQuantity())
                    .customerDto(customerOrder.getCustomer() == null ? null : fromCustomerToCustomerDto(customerOrder.getCustomer()))
                    .payments(customerOrder.getPayments() == null ? null : customerOrder.getPayments())
                    .productDto(customerOrder.getProduct() == null ? null : fromProductToProductDto(customerOrder.getProduct()))
                    .build();
        }

        public CustomerOrder fromCustomerOrderDtoToCustomerOrder(CustomerOrderDto customerOrderDto) {
            return customerOrderDto == null ? null : CustomerOrder.builder()
                    .id(customerOrderDto.getId())
                    .name(customerOrderDto.getName())
                    .customer(customerOrderDto.getCustomerDto() == null ? null : fromCustomerDtoToCustomer(customerOrderDto.getCustomerDto()))
                    .date(customerOrderDto.getDate())
                    .discount(customerOrderDto.getDiscount())
                    .payments(customerOrderDto.getPayments() == null ? null : customerOrderDto.getPayments())

                    .quantity(customerOrderDto.getQuantity())
                    .product(customerOrderDto.getProductDto() == null ? null : fromProductDtoToProduct(customerOrderDto.getProductDto()))
                    .build();
        }

        public ProductDto fromProductToProductDto(Product product) {
            return product == null ? null : ProductDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .producerDto(product.getProducer() == null ? null : fromProducerToProducerDto(product.getProducer()))
                    .categoryDto(product.getCategory() == null ? null : fromCategoryToCategoryDto(product.getCategory()))
                    .guarantees(product.getGuarantees() == null ? null : product.getGuarantees())
                    .build();
        }

        public Product fromProductDtoToProduct(ProductDto productDto) {
            return productDto == null ? null : Product.builder()
                    .id(productDto.getId())
                    .price(productDto.getPrice())
                    .name(productDto.getName())
                    .stocks(new HashSet<>())
                    .customerOrders(new HashSet<>())
                    .guarantees(productDto.getGuarantees() == null ? null : productDto.getGuarantees())
                    .category(productDto.getCategoryDto() == null ? null : fromCategoryDtoToCategory(productDto.getCategoryDto()))
                    .producer(productDto.getProducerDto() == null ? null : fromProducerDtoToProducer(productDto.getProducerDto()))
                    .build();
        }

        public Producer fromProducerDtoToProducer(ProducerDto producerDto) {
            return producerDto == null ? null : Producer.builder()
                    .id(producerDto.getId())
                    .name(producerDto.getName())
                    .country(producerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(producerDto.getCountryDto()))
                    .trade(producerDto.getTradeDto() == null ? null : fromTradeDtoToTrade(producerDto.getTradeDto()))
                    .build();
        }

        public ProducerDto fromProducerToProducerDto(Producer producer) {
            return producer == null ? null : ProducerDto.builder()
                    .id(producer.getId())
                    .name(producer.getName())
                    .countryDto(producer.getCountry() == null ? null : fromCountryToCountryDto(producer.getCountry()))
                    .tradeDto(producer.getTrade() == null ? null : fromTradeToTradeDto(producer.getTrade()))
                    .build();
        }

        public TradeDto fromTradeToTradeDto(Trade trade) {
            return trade == null ? null : TradeDto.builder()
                    .id(trade.getId())
                    .name(trade.getName())
                    .build();
        }

        public Trade fromTradeDtoToTrade(TradeDto tradeDto) {
            return tradeDto == null ? null : Trade.builder()
                    .id(tradeDto.getId())
                    .name(tradeDto.getName())
                    .producers(new HashSet<>())
                    .build();
        }


        public StockDto fromStockToStockDto(Stock stock) {
            return stock == null ? null : StockDto.builder()
                    .id(stock.getId())
                    .quantty(stock.getQuantity())
                    .productDto(stock.getProduct() == null ? null : fromProductToProductDto(stock.getProduct()))
                    .shopDto(stock.getShop() == null ? null : fromShopToShopDto(stock.getShop()))
                    .build();
        }

        public Stock fromStockDtoToStock(StockDto stockDto) {
            return stockDto == null ? null : Stock.builder()
                    .id(stockDto.getId())
                    .product(stockDto.getProductDto() == null ? null : fromProductDtoToProduct(stockDto.getProductDto()))
                    .shop(stockDto.getShopDto() == null ? null : fromShopDtoToShop(stockDto.getShopDto()))
                    .quantity(stockDto.getQuantty())
                    .build();
        }


    }

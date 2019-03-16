package MK.service;

import MK.dto.ProductDto;
import MK.dto.ShopDto;
import MK.dto.StockDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.ProductRepository;
import MK.repository.impl.ShopRepository;
import MK.repository.impl.StockRepository;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationStock {

    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationStock(
            StockRepository stockRepository,
            ShopRepository shopRepository,
            ProductRepository productRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.stockRepository = stockRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }


    public void addStock(StockDto stockDto) {

        if (stockDto == null) {
            throw new MyException(ExceptionCode.STOCK, "STOCK OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateStockFields(stockDto)) {
            stockDto.setQuantty(stockDto.getQuantty() + 1);
//            throw new MyException(ExceptionCode.STOCK, "STOCK FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateStockInsideDB(stockDto)) {
            throw new MyException(ExceptionCode.STOCK, "STOCK ALREADY EXISTS");
        }


        // -----------------------------------------------------------------------------------
        // ------------------------------- SHOP VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        ShopDto shopDto = stockDto.getShopDto();

        if (shopDto == null) {
            throw new MyException(ExceptionCode.SHOP, "SHOP OBJECT IS NULL");
        }

        if (shopDto.getId() == null && shopDto.getName() == null) {
            throw new MyException(ExceptionCode.SHOP, "SHOP WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateShopFields(shopDto)) {
            throw new MyException(ExceptionCode.SHOP, "SHOP FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateShopInsideDB(shopDto)) {
            throw new MyException(ExceptionCode.SHOP, "SHOP NOT FOUND");
        }


        // -----------------------------------------------------------------------------------
        // ------------------------------- PRODUCT VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        ProductDto productDto = stockDto.getProductDto();

        if (productDto == null) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT OBJECT IS NULL");
        }

        if (productDto.getId() == null && productDto.getName() == null) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateProductFields(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateProductInsideDB(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT NOT FOUND");
        }


        // -----------------------------------------------------------------------------------
        // ----------------------------------- INSERT INTO DB --------------------------------
        // -----------------------------------------------------------------------------------
        Stock stock = modelMapper.fromStockDtoToStock(stockDto);


        Shop shop = null;

        if (shopDto.getId() != null) {
            shop = shopRepository
                    .findOne(shopDto.getId())
                    .orElse(null);
        }

        if (shop == null) {
            shop = shopRepository
                    .findByName(shopDto.getName(), shopDto.getCountryDto().getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.COUNTRY, "COUNTRY WITH GIVEN ID AND NAME NOT FOUND"));
        }
        stock.setShop(shop);

        Product product = null;

        if (productDto.getId() != null) {
            product = productRepository
                    .findOne(productDto.getId())
                    .orElse(null);
        }


        if (product == null) {
            product = productRepository
                    .findByNameCategoryProducer(productDto.getName(), product.getCategory().getName(), productDto.getProducerDto().getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.COUNTRY, "COUNTRY WITH GIVEN ID AND NAME NOT FOUND"));
        }


        stock.setProduct(product);

        stockRepository.saveOrUpdate(stock);
    }


}


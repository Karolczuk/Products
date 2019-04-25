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
import MK.validator.impl.model.ProductModelValidator;
import MK.validator.impl.model.ShopModelValidator;
import MK.validator.impl.model.StockModelValidator;
import MK.validator.impl.persistence.ProductPersistenceValidator;
import MK.validator.impl.persistence.ShopPersistenceValidator;
import MK.validator.impl.persistence.StockPersistanceValidator;

public class StockService {

    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final StockModelValidator stockModelValidator;
    private final StockPersistanceValidator stockPersistanceValidator;
    private final ShopModelValidator shopModelValidator;
    private final ShopPersistenceValidator shopPersistenceValidator;
    private final ProductModelValidator productModelValidator;
    private final ProductPersistenceValidator productPersistenceValidator;
    private final ModelMappers modelMapper;


    public StockService(
            StockRepository stockRepository,
            ShopRepository shopRepository,
            ProductRepository productRepository,
            StockModelValidator stockModelValidator,
            StockPersistanceValidator stockPersistanceValidator,
            ShopModelValidator shopModelValidator,
            ShopPersistenceValidator shopPersistenceValidator,
            ProductModelValidator productModelValidator,
            ProductPersistenceValidator productPersistenceValidator,
            ModelMappers modelMapper) {
        this.stockRepository = stockRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.stockModelValidator = stockModelValidator;
        this.stockPersistanceValidator = stockPersistanceValidator;
        this.shopModelValidator = shopModelValidator;
        this.shopPersistenceValidator = shopPersistenceValidator;
        this.productModelValidator = productModelValidator;
        this.productPersistenceValidator = productPersistenceValidator;
        this.modelMapper = modelMapper;
    }


    public void addStock(StockDto stockDto) {

        if (stockDto == null) {
            throw new MyException(ExceptionCode.STOCK, "STOCK OBJECT IS NULL");
        }

        if (!stockModelValidator.validateStockFields(stockDto)) {
            stockDto.setQuantity(stockDto.getQuantity() + 1);
           throw new MyException(ExceptionCode.STOCK, "STOCK FIELDS ARE NOT VALID");
        }

        if (stockPersistanceValidator.validateStockInsideDB(stockDto)) {
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

        if (!shopModelValidator.validateShopFields(shopDto)) {
            throw new MyException(ExceptionCode.SHOP, "SHOP FIELDS ARE NOT VALID");
        }

        if (!shopPersistenceValidator.validateShopInsideDB(shopDto)) {
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

        if (!productModelValidator.validateProductFields(productDto)) {
            throw new MyException(ExceptionCode.PRODUCT, "PRODUCT FIELDS ARE NOT VALID");
        }

        if (!productPersistenceValidator.validateProductInsideDB(productDto)) {
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
                    .findByName(productDto.getName())
                    .orElse(null);
        }

        if (product == null) {
            product = productRepository
                    .findByNameCategoryProducer(productDto.getName(), productDto.getCategoryDto().getName(), productDto.getProducerDto().getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.COUNTRY, "COUNTRY WITH GIVEN ID AND NAME NOT FOUND"));
        }


        stock.setProduct(product);

        stockRepository.saveOrUpdate(stock);
    }


}


package MK.service;

import MK.dto.CountryDto;
import MK.dto.ShopDto;
import MK.dto.StockDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.ShopRepository;
import MK.repository.impl.StockRepository;
import MK.validator.ManagmentProductsValidator;

public class BasicOperationShop {

    private final ShopRepository shopRepository;
    private final CountryRepository countryRepository;
    private final ManagmentProductsValidator managmentProductsValidator;
    private final ModelMappers modelMapper;


    public BasicOperationShop(
            ShopRepository shopRepository,
            CountryRepository countryRepository,
            ManagmentProductsValidator managmentProductsValidator,
            ModelMappers modelMapper) {
        this.shopRepository = shopRepository;
        this.countryRepository = countryRepository;
        this.managmentProductsValidator = managmentProductsValidator;
        this.modelMapper = modelMapper;
    }


    public void addShop(ShopDto shopDto) {

        if (shopDto == null) {
            throw new MyException(ExceptionCode.SHOP, "SHOP OBJECT IS NULL");
        }

        if (!managmentProductsValidator.validateShopFields(shopDto)) {
            throw new MyException(ExceptionCode.SHOP, "SHOP FIELDS ARE NOT VALID");
        }

        if (managmentProductsValidator.validateShopInsideDB(shopDto)) {
            throw new MyException(ExceptionCode.SHOP, "SHOP ALREADY EXISTS");
        }



        // -----------------------------------------------------------------------------------
        // ------------------------------- COUNTRY VALIDATION --------------------------------
        // -----------------------------------------------------------------------------------

        CountryDto countryDto = shopDto.getCountryDto();

        if (countryDto == null) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY OBJECT IS NULL");
        }

        if (countryDto.getId() == null && countryDto.getName() == null) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY WITHOUT ID AND NAME");
        }

        if (!managmentProductsValidator.validateCountryFields(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY FIELDS ARE NOT VALID");
        }

        if (!managmentProductsValidator.validateCountryInsideDB(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY NOT FOUND");
        }


        // -----------------------------------------------------------------------------------
        // ----------------------------------- INSERT INTO DB --------------------------------
        // -----------------------------------------------------------------------------------
        Shop shop = modelMapper.fromShopDtoToShop(shopDto);

        Country country = null;

        if (countryDto.getId() != null) {
            country = countryRepository
                    .findOne(countryDto.getId())
                    .orElse(null);
        }

        if (country == null) {
            country = countryRepository
                    .findByName(countryDto.getName())
                    .orElseThrow(() -> new MyException(ExceptionCode.COUNTRY, "COUNTRY WITH GIVEN ID AND NAME NOT FOUND"));
        }

        shop.setCountry(country);
        shopRepository.saveOrUpdate(shop);
    }

}

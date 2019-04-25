package MK.service;

import MK.dto.CountryDto;
import MK.dto.ShopDto;
import MK.exceptions.ExceptionCode;
import MK.exceptions.MyException;
import MK.mappers.ModelMappers;
import MK.model.*;
import MK.repository.impl.CountryRepository;
import MK.repository.impl.ShopRepository;
import MK.validator.impl.model.CountryModelValidator;
import MK.validator.impl.model.ShopModelValidator;
import MK.validator.impl.persistence.ShopPersistenceValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final CountryRepository countryRepository;
    private final ShopModelValidator shopModelValidator;
    private final ShopPersistenceValidator shopPersistenceValidator;
    private final CountryModelValidator countryModelValidator;
    private final ModelMappers modelMapper;

    public ShopService(
            ShopRepository shopRepository,
            CountryRepository countryRepository,
            ModelMappers modelMapper,
            ShopModelValidator shopModelValidator,
            ShopPersistenceValidator shopPersistenceValidator,
            CountryModelValidator countryModelValidator) {
        this.shopRepository = shopRepository;
        this.countryRepository = countryRepository;
        this.shopModelValidator = shopModelValidator;
        this.shopPersistenceValidator = shopPersistenceValidator;
        this.countryModelValidator = countryModelValidator;
        this.modelMapper = modelMapper;
    }


    public void addShop(ShopDto shopDto) {

        if (shopDto == null) {
            throw new MyException(ExceptionCode.SHOP, "SHOP OBJECT IS NULL");
        }

        if (!shopModelValidator.validateShopFields(shopDto)) {
            throw new MyException(ExceptionCode.SHOP, "SHOP FIELDS ARE NOT VALID");
        }

        if (shopPersistenceValidator.validateShopInsideDB(shopDto)) {
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

        if (!countryModelValidator.validateCountryFields(countryDto)) {
            throw new MyException(ExceptionCode.COUNTRY, "COUNTRY FIELDS ARE NOT VALID");
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

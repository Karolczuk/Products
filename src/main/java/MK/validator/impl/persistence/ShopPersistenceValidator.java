package MK.validator.impl.persistence;

import MK.dto.ShopDto;
import MK.repository.impl.CustomerRepository;
import MK.repository.impl.ShopRepository;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class ShopPersistenceValidator implements Validator<ShopDto> {

    private ShopRepository shopRepository;
    private Map<String, String> errors = new HashMap<>();


    public ShopPersistenceValidator(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public boolean validateShopInsideDB(ShopDto shopDto) {
        return shopRepository.findByName(
                shopDto.getName(),
                shopDto.getCountryDto().getName()
        ).isPresent();
    }

    @Override
    public Map<String, String> validate(ShopDto shop) {
        errors.clear();
        if (shop == null) {
            errors.put("customer", "customer object is null");
        }

        if (!validateShopInsideDB(shop)) {
            errors.put("model", "shop model is not correct: " + shop);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

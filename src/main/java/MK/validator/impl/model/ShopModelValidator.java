package MK.validator.impl.model;

import MK.dto.ShopDto;
import MK.model.Shop;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class ShopModelValidator implements Validator<ShopDto> {

    private Map<String, String> errors = new HashMap<>();

    public boolean validateShopFields(ShopDto shopDto) {
        final String REGEXP = "[A-Z ]+";

        return shopDto.getName() != null &&
                shopDto.getName().matches(REGEXP);
    }

    @Override
    public Map<String, String> validate(ShopDto shop) {

        if (shop == null) {
            errors.put("shop", "shop object is null");
        }

        if (!validateShopFields(shop)) {
            errors.put("model", "shop model is not correct: " + shop);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

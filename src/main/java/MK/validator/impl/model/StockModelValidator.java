package MK.validator.impl.model;

import MK.dto.StockDto;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class StockModelValidator implements Validator<StockDto> {
    private Map<String, String> errors = new HashMap<>();



    public boolean validateStockFields(StockDto stockDto) {
        return stockDto.getQuantty() >= 0;
    }


    @Override
    public Map<String, String> validate(StockDto stock){

        if (stock == null) {
        errors.put("stock", "stock object is null");
    }

        if (!validateStockFields(stock)) {
        errors.put("model", "stock model is not correct: " + stock);
    }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

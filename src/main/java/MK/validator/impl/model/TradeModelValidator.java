package MK.validator.impl.model;

import MK.dto.TradeDto;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class TradeModelValidator implements Validator<TradeDto> {

    private Map<String, String> errors = new HashMap<>();


    public boolean validateTradeFields(TradeDto tradeDto) {
        final String REGEXP = "[A-Z ]+";

        return tradeDto.getName() != null &&
                tradeDto.getName().matches(REGEXP);
    }


    @Override
    public Map<String, String> validate(TradeDto trade) {
        errors.clear();

        if (trade == null) {
            errors.put("trade", "trade object is null");
        }

        if (!validateTradeFields(trade)) {
            errors.put("model", "trade model is not correct: " + trade);
        }
        return errors;
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

package MK.validator.impl.persistence;

import MK.dto.StockDto;
import MK.repository.impl.CustomerRepository;
import MK.repository.impl.StockRepository;
import MK.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class StockPersistanceValidator implements Validator<StockDto> {

    private StockRepository stockRepository;
    private Map<String, String> errors = new HashMap<>();


    public StockPersistanceValidator(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public boolean validateStockInsideDB(StockDto stockDto) {
        return stockRepository.findByName(
                stockDto.getProductDto().getName(),
                stockDto.getProductDto().getCategoryDto().getName(),
                stockDto.getShopDto().getName(),
                stockDto.getShopDto().getCountryDto().getName()
        ).isPresent();
    }

    @Override
    public Map<String, String> validate(StockDto stock) {
        errors.clear();

        if (stock == null) {
            errors.put("stock", "stock object is null");
        }

        if (!validateStockInsideDB(stock)) {
            errors.put("model", "stock model is not correct: " + stock);
        }
        return errors;    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

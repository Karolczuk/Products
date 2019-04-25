package MK.converter;
import MK.dto.StockDto;
import MK.model.Stock;

import java.util.List;

public class StockJsonConverter extends JsonConverter<List<StockDto>>  {
    public StockJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

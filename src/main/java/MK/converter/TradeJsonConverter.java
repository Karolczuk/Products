package MK.converter;
import MK.dto.TradeDto;
import MK.model.Trade;

import java.util.List;

public class TradeJsonConverter extends JsonConverter<List<TradeDto>>  {
    public TradeJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

package MK.converter;
import MK.dto.ShopDto;
import MK.model.Shop;
import java.util.List;

public class ShopJsonConverter extends JsonConverter<List<ShopDto>>  {
    public ShopJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

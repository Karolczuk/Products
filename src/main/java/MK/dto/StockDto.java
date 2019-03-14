package MK.dto;

import MK.model.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDto {
    private Long id;
    private Integer quantty;
    private ShopDto shopDto;
    private ProductDto productDto;
}

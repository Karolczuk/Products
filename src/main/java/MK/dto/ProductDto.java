package MK.dto;

import MK.model.EGuarantee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProducerDto producerDto;
    private CategoryDto categoryDto;
    private Set<EGuarantee> guarantees;

}

package MK.dto;

import MK.model.EPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderDto {
    private Long id; // d
    private String name;
    private double discount;
    private int quantity;
    private LocalDate date;
    private CustomerDto customerDto;
    private ProductDto productDto;
    private Set<EPayment> payments;

}

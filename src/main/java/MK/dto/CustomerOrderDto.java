package MK.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

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
    private PaymentDto paymentDto;
}

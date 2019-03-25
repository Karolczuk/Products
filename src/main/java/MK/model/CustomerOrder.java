package MK.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_order")
public class CustomerOrder {


    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double discount;
    private int quantity;
    private LocalDate date;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "payment_id")
//    private Payment payment;

    @ElementCollection
    @CollectionTable(
            name = "payments",
            joinColumns = @JoinColumn(name = "customer_order_id")
    )
    @Column(name = "payment")
    @Enumerated(EnumType.STRING)
    private Set<EPayment> payments;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}

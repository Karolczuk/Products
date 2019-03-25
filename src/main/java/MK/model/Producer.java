package MK.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "producer")
public class Producer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "producer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Product> products;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trade_id")
    private Trade trade;

}

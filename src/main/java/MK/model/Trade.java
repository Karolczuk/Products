package MK.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany( mappedBy = "trade")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Producer> producers;
}

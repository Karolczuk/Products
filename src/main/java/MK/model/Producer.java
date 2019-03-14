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
@Table(name = "producer")
public class Producer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    // TODO
    // wszystkie takie dziwne id powyrzucaj bo one sie generuje automatycznie!
    //private BigInteger countryId;
    //private BigInteger tradeId;

    // TODO
    // po stronie aktywnej PERSIST jest ok bo mozesz kaskadowo wstawic np producer z country
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private Country country;

    // TODO
    // zadana kolekcja ma nie byc EAGER
    // nie rob PERSIST po stronie pasywnej bo to pozniej powoduje ze np nie mozesz usunac
    // wybranego produktu bo ta relacja go "trzyma"
    @OneToMany(/*cascade = CascadeType.PERSIST, */mappedBy = "producer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Product> products;

    @ManyToOne(cascade = CascadeType.PERSIST) // zachowuje sie jak trazakcyjnosc
    @JoinColumn(name = "trade_id")
    private Trade trade;

}

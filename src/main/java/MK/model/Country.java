package MK.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany( mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Producer> producers;

    @OneToMany( mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Shop> shops;

    @OneToMany( mappedBy = "country")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Customer> customers;


}

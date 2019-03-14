package MK.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    //private BigInteger categoryId;
    //private BigInteger producerId;


    // STRONA AKTYWNA RELACJI
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "category_id")
//    private Category category;


    // TODO
    // @ELementCollection trzeba zrobic

//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "guarantee_components", unique = true)
//    @EqualsAndHashCode.Exclude
//    @ToString.Exclude
//    private EGuarantee guaranteeComponents;


    //////////////////////////////////
//    @ElementCollection
//    @CollectionTable(
//            name = "guarantee_components",
//            joinColumns = @JoinColumn(name = "product_id")
//    )
//    @Column(name = "guarantee_components")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @Enumerated(EnumType.STRING)
//    private Set<EGuarantee> guaranteeComponents;
///////////////////////////////////////

    @ManyToOne(cascade = CascadeType.PERSIST) // zachowuje sie jak trazakcyjnosc
    @JoinColumn(name = "guaranteeComponents_id")
    private GuaranteeComponents guaranteeComponents;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    //to było many simple
//    @ManyToMany(mappedBy = "products")
//    private Set<CustomerOrderRepository> customerOrders;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CustomerOrder> customerOrders;

    // TODO
    // TO MA BYC Many To One, dlaczego !!!!!!! przeciez kazdy produkt ma jedna kateforie, chyba ze jeden produkt moze nalezec do kilku kategorii
    //@OneToOne(mappedBy = "product")
    //private Category category;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;



    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Stock> stocks;

}

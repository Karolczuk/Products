package MK.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "guaranteeComponents")

public class GuaranteeComponents {

        @Id
        @GeneratedValue
        private Long id;

        @Enumerated(EnumType.STRING)
        private EGuarantee eGuarantee;

        @OneToMany(mappedBy = "guaranteeComponents")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private Set<Product> products;
}

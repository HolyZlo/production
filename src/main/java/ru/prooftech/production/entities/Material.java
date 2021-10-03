package ru.prooftech.production.entities;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Collection;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "materials")
public class Material extends RepresentationModel<Material> {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String materialName;
    @Column(name = "price")
    private int materialPrice;
    @Column(name = "quantity")
    private long materialQuantity;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<CompositionProduct> compositionProduct;

}

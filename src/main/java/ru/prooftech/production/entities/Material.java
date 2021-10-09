package ru.prooftech.production.entities;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import ru.prooftech.production.resources.MaterialResource;

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
    private Long id;

    @Column(name = "name")
    private String materialName;

    @Column(name = "price")
    private int materialPrice;

    @Column(name = "quantity")
    private long materialQuantity;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<CompositionProduct> compositionProduct;

    public Material updateFromMaterialResource(MaterialResource materialResource) {
        this.setMaterialQuantity(materialResource.getMaterialQuantity());
        this.setMaterialName(materialResource.getMaterialName());
        this.setMaterialPrice(materialResource.getMaterialPrice());
        return this;
    }
}

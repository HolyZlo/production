package ru.prooftech.production.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class Product extends RepresentationModel<Product> {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String productName;


    @Column(name = "description")
    private String productDescription;

    @Column(name = "quantity")

    private int productQuantity;
    @Column(name = "price")

    private double productPrice;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompositionProduct> composition;

}

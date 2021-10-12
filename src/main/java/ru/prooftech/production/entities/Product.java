package ru.prooftech.production.entities;

import lombok.*;
import ru.prooftech.production.resources.ProductResource;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class Product{
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

    @Column(name = "weight")
    private int weight;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompositionProduct> composition;

    public Product updateFromProductResource(ProductResource productResource) {
        this.setProductName(productResource.getProductName());
        this.setProductPrice(productResource.getProductPrice());
        this.setProductQuantity(productResource.getProductQuantity());
        this.setProductDescription(productResource.getProductDescription());
        this.setWeight(productResource.getWeight());
        return this;
    }

    public static Product createFromProductResource(ProductResource productResource) {
        return Product.builder()
                .productName(productResource.getProductName())
                .productDescription(productResource.getProductDescription())
                .productQuantity(productResource.getProductQuantity())
                .weight(productResource.getWeight())
                .productPrice(productResource.getProductPrice())
                .build();
    }

}

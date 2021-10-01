package ru.prooftech.production.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Map;

@Builder
@Data
@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
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

    @ElementCollection
    private Map<Material, Integer> materialMap;

    @JsonCreator
    public Product(@JsonProperty("name") String productName){
        this.setProductName(productName);
    }
}

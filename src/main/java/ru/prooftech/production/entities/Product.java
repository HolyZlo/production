package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Entity(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

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

    public Product(String productName, String productDescription, int productQuantity, double productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }
}

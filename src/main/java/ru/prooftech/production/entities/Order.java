package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Entity(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order{
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String nameOrder;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @ElementCollection
    private Map<Product, Integer> productMap;

    private double inTotal;

    public void calculatePriceOrder() {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        productMap.forEach((product, count) -> total.set(total.get() + product.getProductPrice() * count));
        this.inTotal = total.get();
    }

    public boolean checkMaterialLeftovers() {
        Map<Material, Integer> materialMap = new HashMap<>();

        productMap.forEach((product, countProduct) -> product.getMaterialMap().forEach((material, countMaterial) -> {
            if (materialMap.containsKey(material)) {
                materialMap.put(material, (materialMap.get(material) + (countMaterial * countProduct)));
            } else {
                materialMap.put(material, countMaterial * countProduct);
            }
        }));
        AtomicBoolean littleResidue = new AtomicBoolean(true);
        materialMap.forEach((material, count) -> {
            if (material.getMaterialQuantity() < count) {
                littleResidue.set(false);
            }
        });
        System.out.println(materialMap);
        return littleResidue.get();


    }
}

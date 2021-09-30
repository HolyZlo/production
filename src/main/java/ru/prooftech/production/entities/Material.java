package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;

@Entity(name = "materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
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

    public Material(String materialName, int materialPrice, long materialQuantity) {
        this.materialName = materialName;
        this.materialPrice = materialPrice;
        this.materialQuantity = materialQuantity;
    }
}

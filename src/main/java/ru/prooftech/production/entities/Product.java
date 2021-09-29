package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
@Id
@GeneratedValue
    private Long id;
    private String name;
    private String description;
    private long quantity;

//    HashMap<Material, Integer> materialHashMap;

}

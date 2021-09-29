package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String description;
    private long quantity;
    HashMap<Material, Integer> materialHashMap;

}

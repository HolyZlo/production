package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class Material {
    private long id;
    private String name;
    private String price;
    private long quantity;


}

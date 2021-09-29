package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Product {

private Long id;
private String name;
private String description;
private long quantity;

}

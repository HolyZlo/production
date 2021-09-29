package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String name;
    private Person person;
    private Map<Product, Integer> products;
}

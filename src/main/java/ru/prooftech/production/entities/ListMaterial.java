package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListMaterial {
    @Id
    @GeneratedValue
    private Long id;
    private int count;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Material material;

}

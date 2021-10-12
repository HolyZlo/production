package ru.prooftech.production.entities;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "product_compositions")
public class CompositionProduct{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "countMaterial")
    private int countMaterial;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    private Material material;


}

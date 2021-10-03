package ru.prooftech.production.entities;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "order_compositions")
public class CompositionOrder extends RepresentationModel<CompositionProduct> {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "priceComposition")
    private double priceComposition;

    @Column(name = "countProduct")
    private int countProduct;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;

}

package ru.prooftech.production.entities;

import lombok.*;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "order_compositions")
public class CompositionOrder {
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

    public double calculatePriceComposition() {
       return this.priceComposition = product.getProductPrice() * countProduct;
    }

    public boolean checkProductInventoryAndProductionAbility() {
        AtomicBoolean productionAbility = new AtomicBoolean(true);
        if (product.getProductQuantity() < countProduct) {
            product.getComposition().forEach(compositionProduct -> {
                if (compositionProduct.getMaterial().getMaterialQuantity() < (long) compositionProduct.getCountMaterial() * countProduct) {
                    productionAbility.set(false);
                }
            });
        } else {
            return true;
        }
        return productionAbility.get();
    }
}

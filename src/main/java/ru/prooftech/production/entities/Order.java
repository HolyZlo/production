package ru.prooftech.production.entities;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends RepresentationModel<Order> {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String orderName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdOn")
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "closedOn")
    private Date closedOn;

    private double inTotal;

    @ManyToOne(fetch = FetchType.EAGER)
    private Person person;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompositionOrder> composition;


    public void calculatePriceOrder() {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        composition.forEach((compositionOrder -> total.set(total.get() + compositionOrder.getPriceComposition())));
        this.inTotal = total.get();
    }

    public boolean checkThePossibilityOfCompletingTheOrder() {
        AtomicBoolean possibilityOrder = new AtomicBoolean(true);
        composition.forEach(compositionOrder -> {
            if (!compositionOrder.checkProductInventoryAndProductionAbility()) {
                possibilityOrder.set(false);
            }
        });
        return possibilityOrder.get();
    }

    public void closeOrder(){
        this.setClosedOn(new Timestamp(System.currentTimeMillis()));
    }

//    public boolean checkMaterialLeftovers() {
//        productMap.forEach((product, countProduct) -> product.getMaterialMap().forEach((material, countMaterial) -> {
//            if (materialMap.containsKey(material)) {
//                materialMap.put(material, (materialMap.get(material) + (countMaterial * countProduct)));
//            } else {
//                materialMap.put(material, countMaterial * countProduct);
//            }
//        }));
//        AtomicBoolean littleResidue = new AtomicBoolean(true);
//        materialMap.forEach((material, count) -> {
//            if (material.getMaterialQuantity() < count) {
//                littleResidue.set(false);
//            }
//        });
//        System.out.println(materialMap);
//        return littleResidue.get();
//
//
//    }
}

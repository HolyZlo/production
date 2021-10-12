package ru.prooftech.production.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

//    @Column(name = "name")
//    private String orderName;

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
        this.inTotal = composition.stream().mapToDouble(CompositionOrder::calculatePriceComposition).sum();
    }

    public boolean checkThePossibilityOfCompletingTheOrder() {
//        return composition.stream().allMatch(CompositionOrder::checkProductInventoryAndProductionAbility);
        return !composition.stream().anyMatch(compositionOrder -> !compositionOrder.checkProductInventoryAndProductionAbility());
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

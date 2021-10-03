package ru.prooftech.production.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.OrderController;
import ru.prooftech.production.controllers.ProductController;
import ru.prooftech.production.entities.CompositionOrder;
import ru.prooftech.production.entities.Person;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "compositionOrder", collectionRelation = "compositionOrder")
public class CompositionOrderResource extends RepresentationModel<CompositionOrderResource> {
    private Long idCompositionOrder;
    private double priceComposition;
    private int countProduct;
    private String productName;

    public CompositionOrderResource(CompositionOrder compositionOrder) {
        this.idCompositionOrder = compositionOrder.getId();
        this.priceComposition = compositionOrder.getPriceComposition();
        this.countProduct = compositionOrder.getCountProduct();
        this.productName = compositionOrder.getProduct().getProductName();
        add(linkTo(methodOn(OrderController.class).getOrderById(compositionOrder.getProduct().getId())).withRel("product"));
        add(linkTo(methodOn(ProductController.class).getComposition(compositionOrder.getProduct().getId())).withSelfRel());

    }
}

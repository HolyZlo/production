package ru.prooftech.production.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.OrderController;
import ru.prooftech.production.entities.CompositionOrder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "compositionOrder", collectionRelation = "compositionOrder")
@Schema(description = "Состав заказа")
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

        add(linkTo(methodOn(OrderController.class).getOrderById(compositionOrder.getOrder().getId())).withSelfRel());
//        add(linkTo(methodOn(OrderController.class).getOrderById(compositionOrder.getOrder().getId())).withSelfRel());

    }
}

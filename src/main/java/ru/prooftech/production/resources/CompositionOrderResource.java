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
@Schema(name = "CompositionOrder", description = "Состав продукта")
public class CompositionOrderResource extends RepresentationModel<CompositionOrderResource> {

    @Schema(description = "Идентификатор состава продуктов", example = "10")
    private Long idCompositionOrder;

    @Schema(description = "Стоимость продуктов", example = "2560.0")
    private double priceComposition;

    @Schema(description = "Ключ продукта", example = "15", required = true)
    private Long productId;

    @Schema(description = "Количество продукта в заказе", example = "100", required = true)
    private int countProduct;

    @Schema(description = "Наименование продукта",
            example = "Влажный корм Pro Plan® для взрослых кошек с чувствительным пищеварением или " +
                    "особыми предпочтениями в еде, с высоким содержанием индейки")
    private String productName;

    public CompositionOrderResource(CompositionOrder compositionOrder) {
        this.idCompositionOrder = compositionOrder.getId();
        this.priceComposition = compositionOrder.getPriceComposition();
        this.countProduct = compositionOrder.getCountProduct();
        this.productName = compositionOrder.getProduct().getProductName();
        this.productId = compositionOrder.getProduct().getId();
        add(linkTo(methodOn(OrderController.class).getOrderById(compositionOrder.getOrder().getId())).withSelfRel());
//        add(linkTo(methodOn(OrderController.class).getOrderById(compositionOrder.getOrder().getId())).withSelfRel());

    }
}

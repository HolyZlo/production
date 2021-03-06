package ru.prooftech.production.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.MaterialController;
import ru.prooftech.production.controllers.ProductController;
import ru.prooftech.production.entities.CompositionProduct;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "compositionProduct", collectionRelation = "compositionProduct")
@Schema(name = "CompositionProduct",description = "Состав продукта")
public class CompositionProductResource extends RepresentationModel<CompositionProductResource> {

    @Schema(description = "Идентификатор состава продукта",example = "125")
    private Long idCompositionProduct;

    @Schema(required = true,description = "Количество материала",example = "85")
    private int countMaterial;

    @Schema(description = "Имя материала",example = "мясо и продукты переработки мяса")
    private String materialName;

    @Schema(required = true,description = "Ключ материала",example = "1")
    private Long idMaterial;

    public CompositionProductResource(CompositionProduct compositionProduct) {
        this.idCompositionProduct = compositionProduct.getId();
        this.countMaterial = compositionProduct.getCountMaterial();
        this.materialName = compositionProduct.getMaterial().getMaterialName();
        this.idMaterial = compositionProduct.getMaterial().getId();
        add(linkTo(methodOn(ProductController.class).getProductById(compositionProduct.getProduct().getId())).withRel("product"));
        add(linkTo(methodOn(ProductController.class).getComposition(compositionProduct.getProduct().getId())).withSelfRel());
        add(linkTo(methodOn(MaterialController.class).getMaterialById(compositionProduct.getMaterial().getId())).withRel("material"));

    }
}

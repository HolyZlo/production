package ru.prooftech.production.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.ProductController;
import ru.prooftech.production.entities.Product;

import javax.persistence.Column;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "product", collectionRelation = "products")
@Schema(name = "Product",description = "Сущность \"Продукт\"", defaultValue = "Product")
public class ProductResource extends RepresentationModel<ProductResource>{
    @Schema(hidden = true)
    private Long idProduct;
    @Schema(description = "Название продукта",required = true,
            example = "Влажный корм Pro Plan® для взрослых кошек с чувствительным пищеварением или особыми " +
                    "предпочтениями в еде, с высоким содержанием индейки")
    private String productName;

    @Schema(description = "Описание продукта",required = true,
            example = "Корм консервированный полнорационный для взрослых кошек с чувствительным пищеварением или " +
                    "особыми предпочтениями в еде, с высоким содержанием индейки")
    private String productDescription;

    @Schema(description = "Остаток продукта, шт.",required = true,
            example = "1500")
    private int productQuantity;

    @Schema(description = "Цена продукта, руб.",required = true,
            example = "83.0")
    private double productPrice;
    @Schema(description = "Вес продукта, гр.",required = true,
            example = "85")
    private int weight;
    @Schema(description = "Состав продукта")
    private Collection<CompositionProductResource> compositionProduct;

    public ProductResource (Product product){
        this.idProduct = product.getId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productQuantity = product.getProductQuantity();
        this.productPrice = product.getProductPrice();

        add(linkTo(methodOn(ProductController.class).getProducts()).withRel("root"));
        add(linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel());
        add(linkTo(methodOn(ProductController.class).getComposition(product.getId())).withRel("composition"));
        // Добавить order
        // Отчеты об остатках
    }

}

package ru.prooftech.production.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.ProductController;
import ru.prooftech.production.entities.Product;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "product", collectionRelation = "products")
public class ProductResource extends RepresentationModel<ProductResource> {
    private Long idProduct;
    private String productName;
    private String productDescription;
    private int productQuantity;
    private double productPrice;
    private Collection<CompositionProductResource> compositionProduct;
    public ProductResource (Product product){
        this.idProduct = product.getId();
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productQuantity = product.getProductQuantity();
        this.productPrice = product.getProductPrice();


        add(linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel());
        add(linkTo(methodOn(ProductController.class).getComposition(product.getId())).withRel("composition"));
        // Добавить order
        // Отчеты об остатках
    }

}

package ru.prooftech.production.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.MaterialController;
import ru.prooftech.production.controllers.ProductController;
import ru.prooftech.production.entities.Material;

import javax.persistence.Column;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "material", collectionRelation = "material")
public class MaterialResource extends RepresentationModel<MaterialResource> {

    private long idMaterial;
    private String materialName;
    private int materialPrice;
    private long materialQuantity;

    public MaterialResource(Material material) {
        this.idMaterial = material.getId();
        this.materialName = material.getMaterialName();
        this.materialPrice = material.getMaterialPrice();
        this.materialQuantity = material.getMaterialQuantity();
        add(linkTo(methodOn(MaterialController.class).getMaterialById(material.getId())).withSelfRel());
    }
}

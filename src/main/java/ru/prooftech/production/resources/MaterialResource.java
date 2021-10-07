package ru.prooftech.production.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.MaterialController;
import ru.prooftech.production.entities.Material;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "material", collectionRelation = "material")
public class MaterialResource extends RepresentationModel<MaterialResource> {

    private long idMaterial;
    @NotNull(message = "Material name cannot be null")
    private String materialName;
    @NotNull(message = "Material price cannot be null")
    private int materialPrice;
    @NotNull(message = "Quantity material  cannot be null")
    private long materialQuantity;

    public MaterialResource(Material material) {
        this.idMaterial = material.getId();
        this.materialName = material.getMaterialName();
        this.materialPrice = material.getMaterialPrice();
        this.materialQuantity = material.getMaterialQuantity();
        add(linkTo(methodOn(MaterialController.class).getMaterialById(material.getId())).withSelfRel());
    }
}

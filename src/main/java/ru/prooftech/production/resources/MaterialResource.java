package ru.prooftech.production.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Getter
@Relation(value = "material", collectionRelation = "material")
@Schema(description = "Материал", name = "Material")
public class MaterialResource extends RepresentationModel<MaterialResource> {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Ключ материала",example = "15")
    private long idMaterial;

    @Schema(required = true,description = "Название материала",example = "мясо и продукты переработки мяса")
    @NotNull(message = "Название материала не может быть null")
    private String materialName;

    @NotNull(message = "Цена материала не может быть null")
    @Schema(required = true,description = "Стоимость материала",example = "85")
    private int materialPrice;

    @Schema(description = "Остаток материала",example = "1000")
    private long materialQuantity;

    public MaterialResource(Material material) {
        this.idMaterial = material.getId();
        this.materialName = material.getMaterialName();
        this.materialPrice = material.getMaterialPrice();
        this.materialQuantity = material.getMaterialQuantity();
        add(linkTo(methodOn(MaterialController.class).getAllMaterials()).withRel("parent"));
        add(linkTo(methodOn(MaterialController.class).getMaterialById(material.getId())).withSelfRel());
    }
}

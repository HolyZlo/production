package ru.prooftech.production.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftech.production.entities.Material;
import ru.prooftech.production.resources.MaterialResource;
import ru.prooftech.production.services.MaterialService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.prooftech.production.configuration.SpringFoxConfig.MATERIAL_TAG;


@RestController
@RequestMapping("/materials")
@Tag(name=MATERIAL_TAG,description = "Материалы используемые в производстве")
public class MaterialController {
    private MaterialService materialService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @Operation(summary = "Получение материала", description = "Получение материала по идентификатору id",tags = {MATERIAL_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialById(@PathVariable @Parameter(description = "Идентификатор пользователя") Long id) {
        return materialService.findById(id)
                .map(material -> ResponseEntity.ok(new MaterialResource(material)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Удаление материала", description = "Удаление материала по идентификатору id",tags = {MATERIAL_TAG})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaterialById(@PathVariable @Parameter(description = "Идентификатор пользователя") Long id) {
        return materialService.deleteById(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Обновление материала", description = "Обновление материала по идентификатору id",tags = {MATERIAL_TAG})
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMaterialById(@PathVariable
                                                @Parameter(description = "Идентификатор пользователя",required = true)
                                                        Long id,
                                                @RequestBody
                                                @Parameter(description = "JSON материала",required = true)
                                                        MaterialResource materialResource) {
        Material material = materialService.findById(id).orElse(new Material());
        if (material.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(materialService.save(material.updateFromMaterialResource(materialResource)), HttpStatus.OK);
        }
    }

    @GetMapping("/")
    @Operation(summary = "Получение списка материалов", description = "Получение списка всех имеющихся материалов",tags = {MATERIAL_TAG})
    public ResponseEntity<?> getAllMaterials() {
        List<MaterialResource> materialResourceList = new ArrayList<>();
        materialService.findAll().forEach(material -> materialResourceList.add(new MaterialResource(material)));
        if (materialResourceList.size() > 0) {
            return new ResponseEntity<>(materialResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Добавление материала", description = "Добавление нового материала")
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMaterial(@RequestBody
                                            @Parameter(description = "JSON Материал", name = "material", required = true)
                                                    MaterialResource materialResource) {
        Material material = Material.builder()
                .materialPrice(materialResource.getMaterialPrice())
                .materialName(materialResource.getMaterialName())
                .materialQuantity(materialResource.getMaterialQuantity())
                .build();
        return new ResponseEntity<>(new MaterialResource(materialService.save(material)), HttpStatus.CREATED);
    }
}

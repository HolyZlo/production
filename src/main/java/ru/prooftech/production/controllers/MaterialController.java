package ru.prooftech.production.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Material;
import ru.prooftech.production.resources.MaterialResource;
import ru.prooftech.production.services.MaterialService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.prooftech.production.configuration.SpringFoxConfig.MATERIAL_TAG;

@AllArgsConstructor
@RestController
@RequestMapping("/materials")
@Tag(name = MATERIAL_TAG, description = "Материалы используемые в производстве")
public class MaterialController {
    private MaterialService materialService;

    @Operation(summary = "Получение материала", description = "Получение материала по идентификатору id", tags = {MATERIAL_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialById(@PathVariable @Parameter(description = "Идентификатор материала") Long id) {
        return ResponseEntity.ok(new MaterialResource(materialService.findById(id)));
    }

    @Operation(summary = "Удаление материала", description = "Удаление материала по идентификатору id", tags = {MATERIAL_TAG})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaterialById(@PathVariable @Parameter(description = "Идентификатор материала") Long id) {
        return materialService.deleteById(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Обновление материала", description = "Обновление материала по идентификатору id", tags = {MATERIAL_TAG})
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMaterialById(@PathVariable
                                                @Parameter(description = "Идентификатор материала", required = true)
                                                        Long id,
                                                @RequestBody
                                                @Parameter(description = "JSON материала", required = true)
                                                        MaterialResource materialResource) {
        return new ResponseEntity<>(materialService.save(materialService.findById(id).updateFromMaterialResource(materialResource)), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Получение списка материалов", description = "Получение списка всех имеющихся материалов", tags = {MATERIAL_TAG})
    public ResponseEntity<?> getAllMaterials() {
        List<MaterialResource> materialResourceList = new ArrayList<>();
        materialService.findAll().forEach(material -> materialResourceList.add(new MaterialResource(material)));
        if (materialResourceList.size() > 0) {
            return new ResponseEntity<>(materialResourceList, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found materials");
        }
    }

    @Operation(summary = "Добавление материала", description = "Добавление нового материала", tags = {MATERIAL_TAG})
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

    @Operation(summary = "Возможность поставки материала", description = "Возможность поставки материала", tags = {MATERIAL_TAG})
    @GetMapping(name = "/{id}/possibilityOfDelivery")
    public ResponseEntity<?> possibilityOfDeliveryMaterial(@PathVariable
                                                           @Parameter(description = "Идентификатор материала", required = true)
                                                                   Long id) {
        Material material = materialService.findById(id);
        //хардкод плохо, нужно вынести в переменные, или сделать умный анализ:
        // необходимое количество материала для поддержания беспрерывного производства
        if (material.getMaterialQuantity() < 1000) {
            return new ResponseEntity<>("need " + (1000 - material.getMaterialQuantity()) + " material", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}

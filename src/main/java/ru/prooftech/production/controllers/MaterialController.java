package ru.prooftech.production.controllers;

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

@RestController
@RequestMapping("/materials")
public class MaterialController {
    private MaterialService materialService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialById(@PathVariable Long id) {
        return materialService.findById(id)
                .map(material -> ResponseEntity.ok(new MaterialResource(material)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllMaterials() {
        List<MaterialResource> materialResourceList = new ArrayList<>();
        materialService.findAll().forEach(material -> materialResourceList.add(new MaterialResource(material)));
        if (materialResourceList.size() > 0) {
            return new ResponseEntity<>(materialResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMaterial(@RequestBody MaterialResource materialResource) {
        Material material = Material.builder()
                .materialPrice(materialResource.getMaterialPrice())
                .materialName(materialResource.getMaterialName())
                .materialQuantity(materialResource.getMaterialQuantity())
                .build();
        materialService.save(material);
        return new ResponseEntity<>(getMaterialById(material.getId()).getBody(), HttpStatus.CREATED);
    }
}
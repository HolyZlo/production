package ru.prooftech.production.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.CompositionProduct;
import ru.prooftech.production.entities.Material;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.resources.CompositionProductResource;
import ru.prooftech.production.resources.ProductResource;
import ru.prooftech.production.services.MaterialService;
import ru.prooftech.production.services.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.prooftech.production.configuration.SpringFoxConfig.PRODUCT_TAG;

@RestController
@RequestMapping("/products")
@Tag(name = PRODUCT_TAG, description = "Продукты производимые компанией")
public class ProductController {

    private ProductService productService;
    private MaterialService materialService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Получить все продукты", description = "Получить список всех продуктов JSON", tags = {PRODUCT_TAG})
    @GetMapping("/")
    public ResponseEntity<?> getProducts() {
        List<ProductResource> productResourceList = new ArrayList<>();
        productService.findAll().forEach(product -> productResourceList.add(new ProductResource(product)));
        if (productResourceList.size() > 0) {
            return new ResponseEntity<>(productResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "Получить продукт", description = "Получить продукт по ключу id, возврат JSON", tags = {PRODUCT_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable
                                            @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        return new ResponseEntity<>(new ProductResource(productService.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Обновить продукт", description = "Обновить продукт по ключу id", tags = {PRODUCT_TAG})
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProductById(@PathVariable
                                               @Parameter(description = "Идентификатор продукта", required = true) Long id,
                                               @RequestBody
                                               @Parameter(description = "JSON продукта", required = true)
                                                       ProductResource productResource) {
        return new ResponseEntity<>(productService
                .save(productService.findById(id).updateFromProductResource(productResource)), HttpStatus.OK);
    }

    @Operation(summary = "Создать продукт", description = "Создать новый продукт", tags = {PRODUCT_TAG})
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody
                                           @Parameter(description = "JSON продукта", required = true)
                                                   ProductResource productResource) {
        return new ResponseEntity<>(getProductById(productService
                .save(Product.createFromProductResource(productResource))
                .getId())
                .getBody(), HttpStatus.CREATED);
    }

    @Operation(summary = "Получить состав продукта", description = "Получить состав продукта по ключу", tags = {PRODUCT_TAG})
    @GetMapping(value = "/{id}/composition")
    public ResponseEntity<?> getComposition(@PathVariable
                                            @Parameter(description = "Ключ продукта - id", required = true) Long id) {
        Product product = productService.findById(id);
        if (product.getComposition().size() == 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Composition product is empty");
        }
        List<CompositionProductResource> compositionProductResources = new ArrayList<>();
        product.getComposition()
                .forEach(compositionProduct -> compositionProductResources.add(new CompositionProductResource(compositionProduct)));
        return new ResponseEntity<>(compositionProductResources, HttpStatus.OK);
    }

    @Operation(summary = "Добавить материалы в состав продукта",
            description = "Добавить материалы в состав продукта по ключу, список материалов должен быть пуст",
            tags = {PRODUCT_TAG})
    @PostMapping(value = "/{id}/composition", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComposition(@PathVariable
                                               @Parameter(description = "Ключ продукта - id", required = true)
                                                       Long id,
                                               @RequestBody
                                               @Parameter(description = "JSON Array материалов", required = true)
                                                       List<CompositionProductResource> compositionProductList) {
        Product product = productService.findById(id);
        if (product.getComposition().size() > 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Composition product already field");
        }
        compositionProductList.forEach(compositionProductResource -> product.getComposition().add(CompositionProduct.builder()
                .countMaterial(compositionProductResource.getCountMaterial())
                .material(materialService.findById(compositionProductResource.getIdMaterial()))
                .product(product)
                .build()));
        productService.save(product);

        return new ResponseEntity<>(getComposition(product.getId()).getBody(), HttpStatus.CREATED);
    }

    @Operation(summary = "Добавить материалы в состав продукта", description = "Добавить материалы в состав продукта по ключу", tags = {PRODUCT_TAG})
    @PutMapping(value = "/{id}/composition", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateComposition(@PathVariable
                                               @Parameter(description = "Ключ продукта - id", required = true)
                                                       Long id,
                                               @RequestBody
                                               @Parameter(description = "JSON Array материалов", required = true)
                                                       List<CompositionProductResource> compositionProductList) {
        Product product = productService.findById(id);
        compositionProductList.forEach(compositionProductResource -> {
            Material material = materialService.findById(compositionProductResource.getIdMaterial());
            product.getComposition().forEach(compositionProduct -> {
                if (compositionProduct.getMaterial().getId().equals(material.getId())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Material with ID - " + material.getId() + " already exists.");
                }
            });

            product.getComposition().add(CompositionProduct.builder()
                    .countMaterial(compositionProductResource.getCountMaterial())
                    .material(material)
                    .product(product)
                    .build());
        });

        productService.save(product);
        return new ResponseEntity<>(getComposition(product.getId()).getBody(), HttpStatus.OK);
    }

}

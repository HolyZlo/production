package ru.prooftech.production.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return productService.findById(id)
                .map(product -> ResponseEntity.ok(new ProductResource(product)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Обновить продукт", description = "Обновить продукт по ключу id", tags = {PRODUCT_TAG})
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProductById(@PathVariable
                                               @Parameter(description = "Идентификатор продукта",required = true) Long id,
                                               @RequestBody
                                               @Parameter(description = "JSON продукта",required = true)
                                                       ProductResource productResource) {
        Product product = productService.findById(id).orElse(new Product());
        if (product.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productService
                    .save(product.updateFromProductResource(productResource)), HttpStatus.OK);
        }
    }


    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody ProductResource productResource) {
        Product product = Product.builder()
                .productPrice(productResource.getProductPrice())
                .productName(productResource.getProductName())
                .productDescription(productResource.getProductDescription())
                .productQuantity(productResource.getProductQuantity())
                .build();
        productService.save(product);
        return new ResponseEntity<>(getProductById(product.getId()).getBody(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/composition")
    public ResponseEntity<?> getComposition(@PathVariable Long id) {
        Product product = productService.findById(id).orElse(new Product());
        if (product.getId() == null || product.getComposition().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CompositionProductResource> compositionProductResources = new ArrayList<>();
        product.getComposition()
                .forEach(compositionProduct -> compositionProductResources.add(new CompositionProductResource(compositionProduct)));
        return new ResponseEntity<>(compositionProductResources, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/composition", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createComposition(@PathVariable Long id, @RequestBody List<CompositionProductResource> compositionProductList) {
        Product product = productService.findById(id).orElse(new Product());

        if (product.getId() == null || product.getComposition().size() > 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        compositionProductList.forEach(compositionProductResource -> product.getComposition().add(CompositionProduct.builder()
                .countMaterial(compositionProductResource.getCountMaterial())
                .material(materialService.findById(compositionProductResource.getIdMaterial()).orElse(new Material()))
                .product(product)
                .build()));
        productService.save(product);
        return new ResponseEntity<>(getComposition(product.getId()).getBody(), HttpStatus.CREATED);
    }
}

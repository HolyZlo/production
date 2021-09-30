package ru.prooftech.production.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.services.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("")
    public Product product(@RequestParam(value = "id") Optional<Long> idProduct) {
        return productService.getById(idProduct.orElse(0L));
    }

    @GetMapping("/")
    public List<Product> productList() {
        return productService.findAll();
    }
}

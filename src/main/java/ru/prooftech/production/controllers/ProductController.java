package ru.prooftech.production.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.services.ProductService;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> product(@PathVariable Long id) {
        Product product = productService.findById(id);
        product.add(linkTo(methodOn(ProductController.class).product(id)).withSelfRel());
       return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @GetMapping("/")
    public List<Product> productList() {
        return productService.findAll();
    }
}

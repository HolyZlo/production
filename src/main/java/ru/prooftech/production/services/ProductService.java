package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service("productService")
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
        return product.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found product by id - " + id);
        }

    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void saveAll(Iterable<Product> iterable) {
        productRepository.saveAll(iterable);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

}

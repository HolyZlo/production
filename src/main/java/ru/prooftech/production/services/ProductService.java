package ru.prooftech.production.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.repositories.ProductRepository;

import java.util.List;

@AllArgsConstructor
@Service("productService")
public class ProductService {

    private ProductRepository productRepository;

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found product by id - " + id);
        });
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

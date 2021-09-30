package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.repositories.ProductRepository;

import java.util.List;

@Service("productService")
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(Long id) {
        return productRepository.getById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void saveAll(Iterable<Product> iterable){
        productRepository.saveAll(iterable);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

}

package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prooftech.production.entities.CompositionProduct;
import ru.prooftech.production.repositories.CompositionProductRepository;

@Service("compositionProductService")
public class CompositionProductService {
    private CompositionProductRepository compositionProductRepository;

    @Autowired
    public void setCompositionProductRepository(CompositionProductRepository compositionProductRepository) {
        this.compositionProductRepository = compositionProductRepository;
    }

    public void save(CompositionProduct compositionProduct){
        compositionProductRepository.save(compositionProduct);
    }
    public void saveAll(Iterable<CompositionProduct> iterable) {
        compositionProductRepository.saveAll(iterable);
    }
}

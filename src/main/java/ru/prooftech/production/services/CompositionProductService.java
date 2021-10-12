package ru.prooftech.production.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prooftech.production.entities.CompositionProduct;
import ru.prooftech.production.repositories.CompositionProductRepository;

@AllArgsConstructor
@Service("compositionProductService")
public class CompositionProductService {
    private CompositionProductRepository compositionProductRepository;

    public void save(CompositionProduct compositionProduct){
        compositionProductRepository.save(compositionProduct);
    }
    public void saveAll(Iterable<CompositionProduct> iterable) {
        compositionProductRepository.saveAll(iterable);
    }
}

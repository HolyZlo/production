package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.CompositionOrder;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.repositories.CompositionOrderRepository;

import java.util.Optional;

@Service("compositionOrderService")
public class CompositionOrderService {
    private CompositionOrderRepository compositionOrderRepository;

    @Autowired
    public void setCompositionOrderRepository(CompositionOrderRepository compositionOrderRepository) {
        this.compositionOrderRepository = compositionOrderRepository;
    }

    public void save(CompositionOrder compositionOrder){
        compositionOrder.calculatePriceComposition();
        compositionOrderRepository.save(compositionOrder);
    }
    public void saveAll(Iterable<CompositionOrder> iterable) {
        iterable.forEach(CompositionOrder::calculatePriceComposition);
        compositionOrderRepository.saveAll(iterable);
    }
    public CompositionOrder findById(Long id) {
        Optional<CompositionOrder> compositionOrder = compositionOrderRepository.findById(id);
        if (compositionOrder.isPresent()) {
            return compositionOrder.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found composition order by id - " + id);
        }
    }

    public void deleteById(Long idComposition) {
        compositionOrderRepository.deleteById(idComposition);
    }
}

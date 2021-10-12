package ru.prooftech.production.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.CompositionOrder;
import ru.prooftech.production.repositories.CompositionOrderRepository;

@AllArgsConstructor
@Service("compositionOrderService")
public class CompositionOrderService {
    private CompositionOrderRepository compositionOrderRepository;

    public void save(CompositionOrder compositionOrder) {
        compositionOrder.calculatePriceComposition();
        compositionOrderRepository.save(compositionOrder);
    }

    public void saveAll(Iterable<CompositionOrder> iterable) {
        iterable.forEach(CompositionOrder::calculatePriceComposition);
        compositionOrderRepository.saveAll(iterable);
    }

    public CompositionOrder findById(Long id) {
        return compositionOrderRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found composition order by id - " + id);
                });
    }

    public void deleteById(Long idComposition) {
        compositionOrderRepository.deleteById(idComposition);
    }
}

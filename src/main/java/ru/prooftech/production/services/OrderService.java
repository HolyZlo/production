package ru.prooftech.production.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service("orderService")
public class OrderService {
    private OrderRepository orderRepository;
    private CompositionOrderService compositionOrderService;

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found order by id - " + id);
        });
    }

    public Order save(Order order) {
        checkCompositionIsNull(order);
        return orderRepository.save(order);
    }

    public void saveAll(Iterable<Order> iterable) {
        iterable.forEach(this::checkCompositionIsNull);
        orderRepository.saveAll(iterable);
    }


    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllByPerson(Person person) {
        return orderRepository.findAllByPerson(person);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(findById(id).getId());
    }

    public void deleteCompositionById(Long id, Long idComposition) {
        findById(id);
        compositionOrderService.findById(idComposition);
        compositionOrderService.deleteById(idComposition);
    }

    private void checkCompositionIsNull(Order order) {
        if (order.getComposition() == null) {
            order.setComposition(new ArrayList<>());
        }
        order.calculatePriceOrder();
    }
}

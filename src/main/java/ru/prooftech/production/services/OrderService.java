package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("orderService")
public class OrderService {
    private OrderRepository orderRepository;
    private CompositionOrderService compositionOrderService;

    @Autowired
    public void setCompositionOrderService(CompositionOrderService compositionOrderService) {
        this.compositionOrderService = compositionOrderService;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found order by id - " + id);
        }
    }

    public Order save(Order order) {
        checkCompositionIsNull(order);
        checkOrderNameIsNull(order);
        order.calculatePriceOrder();
        if (order.getOrderName().isEmpty()) {
            orderRepository.save(order).setOrderName("Заказ №" + order.getId() + ",для клиента - " + order.getPerson().getSurname() + " от " + order.getCreatedOn());
        }
        return orderRepository.save(order);
    }

    public void saveAll(Iterable<Order> iterable) {
        iterable.forEach(order -> {
            checkCompositionIsNull(order);
            checkOrderNameIsNull(order);
        });
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

    private void checkOrderNameIsNull(Order order) {
        if (order.getOrderName() == null) {
            order.setOrderName("");
        }
    }

}

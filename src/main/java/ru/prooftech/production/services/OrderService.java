package ru.prooftech.production.services;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service("orderService")
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void saveAll(Iterable<Order> iterable) {
        orderRepository.saveAll(iterable);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

}

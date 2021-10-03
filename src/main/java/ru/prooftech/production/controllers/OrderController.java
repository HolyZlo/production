package ru.prooftech.production.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.resources.OrderResource;
import ru.prooftech.production.services.OrderService;
import ru.prooftech.production.services.PersonService;
import ru.prooftech.production.services.ProductService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private ProductService productService;
    private PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(order -> ResponseEntity.ok(new OrderResource(order)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public ResponseEntity<?> getOrders() {
        List<OrderResource> orderResourceList = new ArrayList<>();
        orderService.findAll().forEach(order -> orderResourceList.add(new OrderResource(order)));
        if (orderResourceList.size() > 0) {
            return new ResponseEntity<>(orderResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping(value = "/{id}//create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(@PathVariable Long id, @RequestBody OrderResource orderResource) {
        Person person = personService.findById(id).orElse(new Person());
        if (person.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Order order = Order.builder()
                .createdOn(new Timestamp(System.currentTimeMillis()))
                .person(person)
                .build();
        orderService.save(order);
        order.setOrderName("Заказ №" + order.getId() + ",для клиента - " + order.getPerson().getSurname() + " от " + order.getCreatedOn());
        orderService.save(order);
        return new ResponseEntity<>(getOrderById(order.getId()).getBody(), HttpStatus.CREATED);
    }
}

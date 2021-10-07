package ru.prooftech.production.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Optional;

import static ru.prooftech.production.configuration.SpringFoxConfig.ORDER_TAG;

@RestController
@RequestMapping("/orders")
//@Api(tags = {SpringFoxConfig.ORDER_TAG})
@Tag(name= ORDER_TAG, description = "Заказы клиентов")
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

    @Operation(summary = "Получение заказа", description = "Получение заказа по идентификатору id",tags = {ORDER_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable @Parameter(description = "Идентификатор заказа") Long id) {
        return orderService.findById(id)
                .map(order -> ResponseEntity.ok(new OrderResource(order)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Удаление заказа", description = "Удаление заказа по идентификатору id",tags = {ORDER_TAG})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable @Parameter(description = "Идентификатор заказа") Long id) {
        return orderService.deleteById(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Создание заказа", description = "Создание заказа с параметром - идентификатор клиента id",tags = {ORDER_TAG})
    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrder(@RequestParam(name = "personId")
                                         @Parameter(description = "Идентификатор клиента", required = true) Optional<Long> id)    {
        Person person = personService.findById(id.orElse(0L)).orElse(new Person());
        if (person.getId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @Operation(summary = "Получение  всех заказов", description = "Получение всех заказов всех клиентов",tags = {ORDER_TAG})
    @GetMapping("/")
    public ResponseEntity<?> getOrders(@RequestParam(required = false, name = "personId") Optional<Long> id) {
        List<OrderResource> orderResourceList = new ArrayList<>();
        if (id.isEmpty()) {
            orderService.findAll().forEach(order -> orderResourceList.add(new OrderResource(order)));
        } else {
            Optional<Person> person = personService.findById(id.orElse(0L));
            if (person.isPresent()) {
                orderService.findAllByPerson(person.orElse(new Person()))
                        .forEach(order -> orderResourceList.add(new OrderResource(order)));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        if (orderResourceList.size() > 0) {
            return new ResponseEntity<>(orderResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}

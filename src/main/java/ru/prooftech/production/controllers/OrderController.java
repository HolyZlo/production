package ru.prooftech.production.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.*;
import ru.prooftech.production.resources.CompositionOrderResource;
import ru.prooftech.production.resources.OrderResource;
import ru.prooftech.production.services.OrderService;
import ru.prooftech.production.services.PersonService;
import ru.prooftech.production.services.ProductService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static ru.prooftech.production.configuration.SpringFoxConfig.ORDER_TAG;
import static ru.prooftech.production.configuration.SpringFoxConfig.PERSON_TAG;

@RestController
@RequestMapping("/orders")
@Tag(name = ORDER_TAG, description = "Заказы клиентов")
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

    @Operation(summary = "Получение заказа",
            description = "Получение заказа по идентификатору id", tags = {ORDER_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable @Parameter(description = "Идентификатор заказа") Long id) {
        return orderService.findById(id)
                .map(order -> ResponseEntity.ok(new OrderResource(order)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Удаление заказа",
            description = "Удаление заказа по идентификатору id", tags = {ORDER_TAG})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable @Parameter(description = "Идентификатор заказа") Long id) {
        return orderService.deleteById(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Добавить продукты в состав заказа",
            description = "Добавить продукты в состав заказа по ключу, список продуктов должен быть пуст",
            tags = {ORDER_TAG, PERSON_TAG})
    @PostMapping("/{id}/composition")
    public ResponseEntity<?> createCompositionOrderById(@PathVariable
                                                        @Parameter(description = "Идентификатор заказа", required = true)
                                                                Long id,
                                                        @RequestBody
                                                        @Parameter(description = "JSON Array материалов", required = true)
                                                                List<CompositionOrderResource> compositionOrderList) {

        Order order = orderService.findById(id).orElse(new Order());
        if (order.getId() == null || order.getComposition().size() > 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AtomicBoolean errorForEachCompositionOrder = new AtomicBoolean(false);
        compositionOrderList.forEach(compositionOrderResource -> {
            Product product = productService.findById(compositionOrderResource.getProductId()).orElse(new Product());
            if (product.getId() == null) {
                errorForEachCompositionOrder.set(true);
            } else {
                order.getComposition().add(CompositionOrder.builder()
                        .countProduct(compositionOrderResource.getCountProduct())
                        .product(product)
                        .build());
            }
        });
        if (!errorForEachCompositionOrder.get()) {
            orderService.save(order);
            return new ResponseEntity<>(getOrderById(order.getId()).getBody(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Изменить состав заказа",
            description = "Изменить количество продуктов и добавить новые в составе заказа по ключу",
            tags = {ORDER_TAG, PERSON_TAG})
    @PutMapping("/{id}/composition")
    public ResponseEntity<?> updateCompositionOrderById(@PathVariable
                                                        @Parameter(description = "Идентификатор заказа", required = true)
                                                                Long id,
                                                        @RequestBody
                                                        @Parameter(description = "JSON Array материалов", required = true)
                                                                List<CompositionOrderResource> compositionOrderList) {

        Order order = orderService.findById(id).orElse(new Order());
        if (order.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found order by id - " + id);
        }
        compositionOrderList.forEach(compositionOrderResource -> {
            Product product = productService.findById(compositionOrderResource.getProductId()).orElse(new Product());
            if (product.getId() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found product by id - " + compositionOrderResource.getProductId());
            } else {
                order.getComposition().forEach(compositionOrder -> {
                    if (compositionOrder.getProduct().getId().equals(compositionOrderResource.getProductId())) {
                        compositionOrder.setCountProduct(compositionOrderResource.getCountProduct());
                    } else {
                        order.getComposition().add(CompositionOrder.builder()
                                .countProduct(compositionOrderResource.getCountProduct())
                                .product(product)
                                .build());
                    }
                });
            }
        });
        orderService.save(order);
        return new ResponseEntity<>(getOrderById(order.getId()).getBody(), HttpStatus.CREATED);
    }

    @Operation(summary = "Создание заказа", description = "Создание заказа с параметром - идентификатор клиента id", tags = {ORDER_TAG})
    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrderByPersonId(@RequestParam(name = "personId")
                                                   @Parameter(description = "Идентификатор клиента", required = true) Long id) {
        Person person = personService.findById(id).orElse(new Person());
        if (person.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found person by id - " + id);
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

    @Operation(summary = "Получение  всех заказов", description = "Получение всех заказов всех клиентов", tags = {ORDER_TAG})
    @GetMapping("/")
    public ResponseEntity<?> getAllOrders(@RequestParam(required = false, name = "personId") Long id) {
        List<OrderResource> orderResourceList = new ArrayList<>();
        if (id == null) {
            orderService.findAll().forEach(order -> orderResourceList.add(new OrderResource(order)));
        } else {
            Person person = personService.findById(id).orElse(new Person());
            if (person.getId() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found person by id - " + id);
            }
            orderService.findAllByPerson(person)
                    .forEach(order -> orderResourceList.add(new OrderResource(order)));
        }
        if (orderResourceList.size() > 0) {
            return new ResponseEntity<>(orderResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}

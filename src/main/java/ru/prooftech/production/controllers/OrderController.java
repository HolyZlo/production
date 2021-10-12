package ru.prooftech.production.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.CompositionOrder;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.resources.CompositionOrderResource;
import ru.prooftech.production.resources.OrderResource;
import ru.prooftech.production.services.OrderService;
import ru.prooftech.production.services.PersonService;
import ru.prooftech.production.services.ProductService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static ru.prooftech.production.configuration.SpringFoxConfig.ORDER_TAG;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Tag(name = ORDER_TAG, description = "Заказы клиентов")
public class OrderController {
    private OrderService orderService;
    private ProductService productService;
    private PersonService personService;

    @Operation(summary = "Получение заказа",
            description = "Получение заказа по идентификатору id", tags = {ORDER_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable @Parameter(description = "Идентификатор заказа") Long id) {
        return ResponseEntity.ok(new OrderResource(orderService.findById(id)));
    }

    @Operation(summary = "Удаление заказа",
            description = "Удаление заказа по идентификатору id", tags = {ORDER_TAG})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable @Parameter(description = "Идентификатор заказа") Long id) {
        orderService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Получить состав заказа",
            description = "Получить состав заказа по ключу заказа",
            tags = {ORDER_TAG})
    @GetMapping("/{id}/composition/")
    public ResponseEntity<?> getCompositionOrderById(@PathVariable
                                                     @Parameter(description = "Идентификатор заказа", required = true)
                                                             Long id) {
        List<CompositionOrderResource> compositionOrderResources = new ArrayList<>();
        orderService.findById(id).getComposition().forEach(compositionOrder -> compositionOrderResources.add(new CompositionOrderResource(compositionOrder)));
        return new ResponseEntity<>(compositionOrderResources, HttpStatus.OK);
    }

    @Operation(summary = "Добавить продукты в состав заказа",
            description = "Добавить продукты в состав заказа по ключу, список продуктов должен быть пуст",
            tags = {ORDER_TAG})
    @PostMapping("/{id}/composition")
    public ResponseEntity<?> createCompositionOrderById(@PathVariable
                                                        @Parameter(description = "Идентификатор заказа", required = true)
                                                                Long id,
                                                        @RequestBody
                                                        @Parameter(description = "JSON Array материалов", required = true)
                                                                List<CompositionOrderResource> compositionOrderList) {
        Order order = orderService.findById(id);
        if (order.getComposition().size() > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Composition order full");
        }
        compositionOrderList.forEach(compositionOrderResource -> order.getComposition().add(CompositionOrder.builder()
                .countProduct(compositionOrderResource.getCountProduct())
                .product(productService.findById(compositionOrderResource.getProductId()))
                .build()));

        return new ResponseEntity<>(new OrderResource(orderService.save(order)), HttpStatus.CREATED);
    }

    @Operation(summary = "Изменить состав заказа",
            description = "Изменить количество продуктов и добавить новые в составе заказа по ключу",
            tags = {ORDER_TAG})
    @PutMapping("/{id}/composition")
    public ResponseEntity<?> updateCompositionOrderById(@PathVariable
                                                        @Parameter(description = "Идентификатор заказа", required = true)
                                                                Long id,
                                                        @RequestBody
                                                        @Parameter(description = "JSON Array материалов", required = true)
                                                                List<CompositionOrderResource> compositionOrderList) {
        Order order = orderService.findById(id);
        compositionOrderList.forEach(compositionOrderResource -> {
            Product product = productService.findById(compositionOrderResource.getProductId());
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
        });
        return new ResponseEntity<>(new OrderResource(orderService.save(order)), HttpStatus.CREATED);
    }

    @Operation(summary = "Удалить единицу состав заказа",
            description = "Удалить единицу в составе заказа по ключу",
            tags = {ORDER_TAG})
    @DeleteMapping("/{id}/composition/{idComposition}")
    public ResponseEntity<?> deleteCompositionOrderById(@PathVariable
                                                        @Parameter(description = "Идентификатор заказа", required = true)
                                                                Long id,
                                                        @PathVariable
                                                        @Parameter(description = "Идентификатор единицы состава", required = true)
                                                                Long idComposition) {
        orderService.deleteCompositionById(id, idComposition);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Создание заказа",
            description = "Создание заказа с параметром - идентификатор клиента id",
            tags = {ORDER_TAG})
    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrderByPersonId(@RequestParam(name = "personId")
                                                   @Parameter(description = "Идентификатор клиента", required = true) Long id) {

        return new ResponseEntity<>(new OrderResource(orderService
                .save(Order.builder()
                        .createdOn(new Timestamp(System.currentTimeMillis()))
                        .person(personService.findById(id))
                        .build())), HttpStatus.CREATED);
    }

    @Operation(summary = "Получение  всех заказов",
            description = "Получение всех заказов всех клиентов",
            tags = {ORDER_TAG})
    @GetMapping("/")
    public ResponseEntity<?> getAllOrders() {
        List<OrderResource> orderResourceList = new ArrayList<>();
        orderService.findAll().forEach(order -> orderResourceList.add(new OrderResource(order)));
        if (orderResourceList.size() > 0) {
            return new ResponseEntity<>(orderResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @Operation(summary = "Подтверждение заказа",
            description = "Подтверждение заказа, расчет возможности заказа",
            tags = {ORDER_TAG})
    @GetMapping("/{id}/confirm")
    public ResponseEntity<?> confirmOrder(@PathVariable
                                          @Parameter(description = "Идентификатор заказа", required = true)
                                                  Long id) {
        Order order = orderService.findById(id);

        if (order.checkThePossibilityOfCompletingTheOrder()) {
            order.closeOrder();
            return new ResponseEntity<>(new OrderResource(orderService.save(order)), HttpStatus.OK);
        } else {
            // По идее, нужно вернуть возможный заказ в соответсвии с остатками
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    //    @Operation(summary = "Получение  всех заказов", description = "Получение всех заказов клиента", tags = {ORDER_TAG})
//    @GetMapping(value = "/",params = {"personId"})
    public ResponseEntity<?> getAllOrdersById(@RequestParam(name = "personId") Long id) {
        List<OrderResource> orderResourceList = new ArrayList<>();
        if (id == null) {
            orderService.findAll().forEach(order -> orderResourceList.add(new OrderResource(order)));
        } else {
            orderService.findAllByPerson(personService.findById(id))
                    .forEach(order -> orderResourceList.add(new OrderResource(order)));
        }
        if (orderResourceList.size() > 0) {
            return new ResponseEntity<>(orderResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}

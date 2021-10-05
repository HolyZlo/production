package ru.prooftech.production.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.resources.OrderResource;
import ru.prooftech.production.resources.PersonResource;
import ru.prooftech.production.services.OrderService;
import ru.prooftech.production.services.PersonService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private PersonService personService;
    private OrderService orderService;
    private OrderController orderController;

    @Autowired
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable Long id) {
        return personService.findById(id)
                .map(person -> ResponseEntity.ok(new PersonResource(person)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/")
    public ResponseEntity<?> getPersons() {
        List<PersonResource> personResourceList = new ArrayList<>();
        personService.findAll().forEach(person -> personResourceList.add(new PersonResource(person)));
        if (personResourceList.size() > 0) {
            return new ResponseEntity<>(personResourceList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPerson(@RequestBody PersonResource personResource) {
        Person person = Person.builder()
                .personName(personResource.getNamePerson())
                .surname(personResource.getSurname())
                .age(personResource.getAge())
                .balance(personResource.getBalance())
                .phoneNumber(personResource.getPhoneNumber())
                .build();
        personService.save(person);
        return new ResponseEntity<>(getPersonById(person.getId()).getBody(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/orders/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(@PathVariable Optional<Long> id, @RequestBody OrderResource orderResource) {
        return orderController.createOrder(id, orderResource);
    }
    @GetMapping("/{id}/orders")
    public ResponseEntity<?> getOrdersByIdPerson(@PathVariable Long id) {
        return orderController.getOrders(Optional.of(id));
    }
    @GetMapping("/{id}/orders/{idOrder}")
    public ResponseEntity<?> getOrdersByIdPersonAndIdOrder(@PathVariable Long id,@PathVariable Long idOrder) {
        return orderController.getOrderById(id);
    }

}

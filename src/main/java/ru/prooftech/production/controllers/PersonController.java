package ru.prooftech.production.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.resources.PersonResource;
import ru.prooftech.production.services.OrderService;
import ru.prooftech.production.services.PersonService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.prooftech.production.configuration.SpringFoxConfig.ORDER_TAG;
import static ru.prooftech.production.configuration.SpringFoxConfig.PERSON_TAG;

@AllArgsConstructor
@RestController
@RequestMapping("/persons")
@Tag(name = PERSON_TAG, description = "Клиенты")
public class PersonController {
    private PersonService personService;
    private OrderService orderService;
    private OrderController orderController;

    @Operation(summary = "Получить клиента", description = "Получить клиента по идентификатору id", tags = {PERSON_TAG})
    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable
                                           @Parameter(description = "Идентификатор клиента", required = true)
                                                   Long id) {
        return ResponseEntity.ok(new PersonResource(personService.findById(id)));
    }

//    @Operation(summary = "Получить клиентов по фамилии",
//            description = "Получить список всех клиентов по фамилии", tags = {PERSON_TAG})
//    @GetMapping(value = "/", params = {"surname"})
//    public ResponseEntity<?> getPersonsBySurname(@RequestParam(name = "surname")
//                                                 @Parameter(description = "Фамилия клиента")
//                                                         String surname) {
//        List<PersonResource> personResourceList = new ArrayList<>();
//        personService.findBySurnameLikeIgnoreCase(surname).forEach(person -> personResourceList.add(new PersonResource(person)));
//
//        if (personResourceList.size() > 0) {
//            return new ResponseEntity<>(personResourceList, HttpStatus.OK);
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found persons");
//        }
//    }

    @Operation(summary = "Получить клиентов", description = "Получить список всех клиентов", tags = {PERSON_TAG})
    @GetMapping(value = "/")
    public ResponseEntity<?> getAllPersons() {
        List<PersonResource> personResourceList = new ArrayList<>();
        personService.findAll().forEach(person -> personResourceList.add(new PersonResource(person)));
        if (personResourceList.size() > 0) {
            return new ResponseEntity<>(personResourceList, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found persons");
        }
    }

    @Operation(summary = "Создать клиента", description = "Создать нового клиентов", tags = {PERSON_TAG})
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPerson(@RequestBody
                                          @Parameter(description = "JSON клиента", required = true)
                                                  PersonResource personResource) {

        return new ResponseEntity<>(getPersonById(personService
                .save(Person.createPersonFromPersonResource(personResource))
                .getId()).getBody(), HttpStatus.CREATED);
    }

    @Operation(summary = "Создать заказ для клиента", description = "Создать новый заказ для клиента", tags = {PERSON_TAG, ORDER_TAG})
    @PostMapping(value = "/{id}/orders/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(@PathVariable
                                         @Parameter(description = "Идентификатор клиента", required = true)
                                                 Long id) {
        return orderController.createOrderByPersonId(id);
    }

    @Operation(summary = "Получить заказы клиента", description = "Получить список всех заказов у клиента", tags = {PERSON_TAG, ORDER_TAG})
    @GetMapping("/{id}/orders")
    public ResponseEntity<?> getOrdersByIdPerson(@PathVariable
                                                 @Parameter(description = "Идентификатор клиента", required = true)
                                                         Long id) {
        return orderController.getAllOrdersById(id);
    }

    @Operation(summary = "Получить заказ клиента", description = "Получить заказ у клиента по идентификатору клиента и заказа", tags = {PERSON_TAG, ORDER_TAG})
    @GetMapping("/{id}/orders/{idOrder}")
    public ResponseEntity<?> getOrdersByIdPersonAndIdOrder(@PathVariable
                                                           @Parameter(description = "Идентификатор клиента", required = true)
                                                                   Long id,
                                                           @PathVariable
                                                           @Parameter(description = "Идентификатор заказа", required = true)
                                                                   Long idOrder) {
        return orderController.getOrderById(idOrder);
    }
}

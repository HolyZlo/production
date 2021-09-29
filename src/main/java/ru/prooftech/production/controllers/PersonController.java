package ru.prooftech.production.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.services.PersonService;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class PersonController {
    private PersonService personService;
    private final AtomicLong counterId = new AtomicLong();

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @PutMapping("/greeting")
    public Person person(@RequestParam(value = "name", defaultValue = "user") String name,
                         @RequestParam(value = "surname", defaultValue = "noname") String surname,
                         @RequestParam(value = "age") Optional<Integer> age,
                         @RequestParam(value = "phoneNumber", defaultValue = "none") String phoneNumber,
                         @RequestParam(value = "balance") Optional<Long> balance) {
        return new Person(counterId.incrementAndGet(), name, surname, age.orElse(0), phoneNumber, balance.orElse(0L));
    }


}

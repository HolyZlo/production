package ru.prooftech.production.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftech.production.entities.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController()
public class PersonController {
    private final AtomicLong counterId = new AtomicLong();
    Map<Long, Person> personMap = new HashMap<>();

    @GetMapping("/persons")
    public Person person(@RequestParam(value = "id") Optional<Long> idPerson) {
        return personMap.get(idPerson.orElse(0L));
    }


    @PutMapping("/persons")
    public Person person(@RequestParam(value = "name", defaultValue = "user") String name,
                         @RequestParam(value = "surname", defaultValue = "noname") String surname,
                         @RequestParam(value = "age") Optional<Integer> age,
                         @RequestParam(value = "phone", defaultValue = "none") String phoneNumber,
                         @RequestParam(value = "balance") Optional<Long> balance) {
        Person personAdd = new Person(counterId.incrementAndGet(), name, surname, age.orElse(0), phoneNumber, balance.orElse(0L));
        personMap.put(counterId.get(), personAdd);
        return personAdd;
    }

}

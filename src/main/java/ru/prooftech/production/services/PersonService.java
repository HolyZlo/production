package ru.prooftech.production.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.repositories.PersonRepository;

import java.util.List;

@AllArgsConstructor
@Service("personService")
public class PersonService {
    private PersonRepository personRepository;

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void saveAll(Iterable<Person> persons) {
        personRepository.saveAll(persons);
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found person by id - " + id);
        });
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Person> findBySurnameLikeIgnoreCase(String surname) {
        return personRepository.findBySurnameLikeIgnoreCase(surname);
    }

}

package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service("personService")
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void saveAll(Iterable<Person> persons) {
        personRepository.saveAll(persons);
    }

    public Person findById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            return person.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found person by id - " + id);
        }
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Person> findBySurnameLikeIgnoreCase(String surname) {
        return personRepository.findBySurnameLikeIgnoreCase(surname);
    }

}

package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.repositories.PersonRepository;

@Service("personService")
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person){
        return personRepository.save(person);
    }
    public void saveAll(Iterable<Person> persons){
        personRepository.saveAll(persons);
    }

    public Person getById(Long id) {
        return personRepository.getById(id);
    }
}

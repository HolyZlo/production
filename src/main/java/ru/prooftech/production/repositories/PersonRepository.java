package ru.prooftech.production.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prooftech.production.entities.Person;

import java.util.List;

@Repository
public interface PersonRepository
        extends CrudRepository<Person, Long>,
        JpaRepository<Person, Long> {
    public List<Person> findBySurnameLikeIgnoreCase(String surname);
}

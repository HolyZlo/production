package ru.prooftech.production.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Person;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>,
        JpaRepository<Order, Long> {
    List<Order> findAllByPerson(Person person);
}

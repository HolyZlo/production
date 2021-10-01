package ru.prooftech.production.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prooftech.production.entities.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>,
        JpaRepository<Order, Long> {
}
package ru.prooftech.production.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prooftech.production.entities.CompositionOrder;

@Repository
public interface CompositionOrderRepository extends
        JpaRepository<CompositionOrder, Long>,
        CrudRepository<CompositionOrder, Long> {
}

package ru.prooftech.production.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prooftech.production.entities.Material;

@Repository
public interface MaterialRepository extends
        CrudRepository<Material, Long>,
        JpaRepository<Material, Long> {
}

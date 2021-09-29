package ru.prooftech.production.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.prooftech.production.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long>,
        JpaRepository<Product,Long> {

}

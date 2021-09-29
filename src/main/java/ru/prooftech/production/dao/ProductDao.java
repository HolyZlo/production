package ru.prooftech.production.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.prooftech.production.entities.Product;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
@Scope("")
public class ProductDao implements Dao<Product> {
    private List<Product> productList = new ArrayList<Product>();
    private final AtomicLong counterId = new AtomicLong();

    @Override
    public Optional<Product> get(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<Product> getAll() {
        return productList.stream().filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public int save(Product product) {
        return 0;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(Product product) {

    }
}

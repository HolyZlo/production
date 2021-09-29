package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.prooftech.production.dao.ProductDao;
import ru.prooftech.production.entities.Product;

import java.util.Collection;

@Service("productService")
public class ProductService {

    private ProductDao productDao;
    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Collection<Product> getAll(){
      return productDao.getAll();
    }

}

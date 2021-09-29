package ru.prooftech.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.services.ProductService;

@Component
public class DataInit implements ApplicationRunner {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Product product1 = new Product();
        product1.setName("Подставка для телефона");
        product1.setQuantity(150L);
        product1.setDescription("Подставка для телефона из PLA пластика");
        productService.saveProduct(product1);
        Product product2 = new Product();
        product2.setName("Зажим для пакетов");
        product2.setQuantity(150L);
        product2.setDescription("Зажим для пакетов из PETG пластика");
        productService.saveProduct(product2);

    }
}

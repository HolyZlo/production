package ru.prooftech.production;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.prooftech.production.entities.Material;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Person;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.services.MaterialService;
import ru.prooftech.production.services.PersonService;
import ru.prooftech.production.services.ProductService;

import java.util.*;

@Component
public class DataInit implements ApplicationRunner {
    private ProductService productService;

    private MaterialService materialService;

    private PersonService personService;

    @Autowired
    public void setMaterialService(MaterialService materialService) {
        this.materialService = materialService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void run(ApplicationArguments args) {
//-----------        Материалы
        Material material1 = new Material("Пластик PLA", 500, 5000);
        Material material2 = new Material("Пластик PETG", 300, 15000);
        Material material3 = new Material("Дихлорметан", 1000, 1000);
        materialService.saveAll(List.of(material1, material2, material3));
//-----------        Продукты
        Product product1 = new Product("Подставка для телефона",
                "Подставка для телефона из PLA пластика", 150, 100.0);
        product1.setMaterialMap(new HashMap<>(Map.of(material1, 50, material2, 30, material3, 15)));
        Product product2 = new Product("Зажим для пакетов",
                "Зажим для пакетов из PETG пластика", 150, 20.0);
        product2.setMaterialMap(new HashMap<>(Map.of(material1, 20, material2, 10, material3, 5)));
        productService.saveAll(List.of(product1, product2));
//-----------       Клиенты
        Person person1 = new Person("Иван", "Иванов", 25, "+79021111111", 50000L);
        Person person2 = new Person("Максим", "Максимов", 35, "+79021111112", 150000L);
        Person person3 = new Person("Сергей", "Сергеев", 45, "+79021111122", 1500L);
        personService.saveAll(List.of(person1, person2, person3));
//-----------       Заказы

        Order order = new Order();
        order.setNameOrder("Тестовый заказ");
        order.setCreatedOn(new Date());
        order.setProductMap(Map.of(product1, 50, product2, 15));
        System.out.println(order);
//        order.calculateOrder();
//        System.out.println(order.getInTotal());
//        System.out.println(order.checkMaterialLeftovers());
//        System.out.println(order);
    }
}

package ru.prooftech.production;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.prooftech.production.entities.*;
import ru.prooftech.production.services.*;

import java.util.*;

@Component
public class DataInit implements ApplicationRunner {
    private ProductService productService;

    private MaterialService materialService;

    private PersonService personService;

    private OrderService orderService;

    private CompositionProductService compositionProductService;

    @Autowired
    public void setCompositionProductService(CompositionProductService compositionProductService) {
        this.compositionProductService = compositionProductService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

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
        Material material1 = Material.builder()
                .materialName("Пластик PLA").materialPrice(500).materialQuantity(5000).build();
        Material material2 = Material.builder()
                .materialName("Пластик PETG").materialPrice(300).materialQuantity(15000).build();
        Material material3 = Material.builder()
                .materialName("Дихлорметан").materialPrice(1000).materialQuantity(1000).build();
        materialService.saveAll(List.of(material1, material2, material3));
//-----------        Продукты
        Product product1 = Product.builder()
                .productName("Подставка для телефона")
                .productDescription("Подставка для телефона из PLA пластика")
                .productQuantity(150)
                .productPrice(100.0)
//                .materialMap(new HashMap<>(Map.of(material1, 50, material2, 30, material3, 15)))
                .build();
        Product product2 = Product.builder()
                .productName("Зажим для пакетов")
                .productDescription("Зажим для пакетов из PETG пластика")
                .productQuantity(150)
                .productPrice(20.0)
//                .materialMap(new HashMap<>(Map.of(material1, 20, material2, 10, material3, 5)))
                .build();
//        product1.setComposition(List.of(CompositionProduct.builder().product(product1).countMaterial(50).material(material1).build(),
//                CompositionProduct.builder().product(product1).countMaterial(50).material(material2).build()));

        productService.saveAll(List.of(product1, product2));


        //-----------      Состав продуктов
        CompositionProduct compositionProduct1 = CompositionProduct.builder().product(product1).material(material1).countMaterial(20).build();
        CompositionProduct compositionProduct2 = CompositionProduct.builder().product(product1).material(material2).countMaterial(10).build();
        CompositionProduct compositionProduct3 = CompositionProduct.builder().product(product1).material(material3).countMaterial(30).build();
        CompositionProduct compositionProduct4 = CompositionProduct.builder().product(product2).material(material1).countMaterial(5).build();
        CompositionProduct compositionProduct5 = CompositionProduct.builder().product(product2).material(material2).countMaterial(15).build();
        CompositionProduct compositionProduct6 = CompositionProduct.builder().product(product2).material(material3).countMaterial(24).build();
        compositionProductService.saveAll(List.of(compositionProduct1, compositionProduct2, compositionProduct3, compositionProduct4, compositionProduct5, compositionProduct6));

//-----------       Клиенты

        Person person1 = Person.builder().personName("Михаил").surname("Иванов").age(45).balance(20_000)
                .phoneNumber("+79021933277").build();
        Person person2 = Person.builder().personName("Максим").surname("Максимов").age(34).balance(100_000)
                .phoneNumber("+79021933277").build();
        Person person3 = Person.builder().personName("Сергей").surname("Винюков").age(35).balance(200_000)
                .phoneNumber("+79021933277").build();
        personService.saveAll(List.of(person1, person2, person3));
//-----------       Заказы

//        Order order = new Order();
//        order.setNameOrder("Тестовый заказ");
//        order.setCreatedOn(new Date());
//        order.setProductMap(Map.of(product1, 50, product2, 15));
//        System.out.println(order);

//        order.calculateOrder();
//        System.out.println(order.getInTotal());
//        System.out.println(order.checkMaterialLeftovers());
//        System.out.println(order);

//        orderService.save(order);

        //-----------       Заказы

    }
}

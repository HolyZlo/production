package ru.prooftech.production;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.prooftech.production.entities.*;
import ru.prooftech.production.services.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Component
public class DataInit implements ApplicationRunner {
    private ProductService productService;

    private MaterialService materialService;

    private PersonService personService;

    private OrderService orderService;

    private CompositionProductService compositionProductService;

    private CompositionOrderService compositionOrderService;

    @Override
    public void run(ApplicationArguments args) {
//-----------        Материалы
        Material material1 = Material.builder()
                .materialName("мясо и продукты переработки мяса").materialPrice(100).materialQuantity(5000).build();
        Material material2 = Material.builder()
                .materialName("рыба и продукты переработки рыбы").materialPrice(140).materialQuantity(5000).build();
        Material material3 = Material.builder()
                .materialName("масла и жиры").materialPrice(200).materialQuantity(3000).build();
        Material material4 = Material.builder()
                .materialName("продукты переработки растительного сырья").materialPrice(50).materialQuantity(2000).build();
        Material material5 = Material.builder()
                .materialName("сухой белок птицы").materialPrice(10).materialQuantity(4000).build();
        materialService.saveAll(List.of(material1, material2, material3, material4, material5));
//-----------        Продукты
        Product product1 = Product.builder()
                .productName("Влажный корм Pro Plan® для взрослых кошек с чувствительным пищеварением или особыми " +
                        "предпочтениями в еде, с высоким содержанием индейки")
                .productDescription("Корм консервированный полнорационный для взрослых кошек с чувствительным " +
                        "пищеварением или особыми предпочтениями в еде, с высоким содержанием индейки")
                .weight(85)
                .productQuantity(150)
                .productPrice(83.0)
                .build();
        Product product2 = Product.builder()
                .productName("Влажный корм Pro Plan® для стерилизованных кошек и кастрированных котов, с" +
                        " тунцом и лососем")
                .productDescription("Корм консервированный полнорационный для стерилизованных кошек и " +
                        "кастрированных котов, с тунцом и лососем, паштет")
                .productQuantity(200)
                .weight(85)
                .productPrice(85.0)
                .build();
        Product product3 = Product.builder()
                .productName("Сухой корм Pro Plan® для стерилизованных кошек и кастрированных котов, лосось")
                .productDescription("Для поддержания здоровья стерилизованных кошек. PRO PLAN Sterilised сочетает все" +
                        "основные питательные вещества, включая витамины A, C и E и жирные кислоты омега-3 и омега-6")
                .productQuantity(200)
                .weight(85)
                .productPrice(85.0)
                .build();
        productService.saveAll(List.of(product1, product2, product3));

        //-----------      Состав продуктов
        CompositionProduct compositionProduct1 = CompositionProduct.builder().product(product1).material(material1).countMaterial(14).build();
        CompositionProduct compositionProduct2 = CompositionProduct.builder().product(product1).material(material3).countMaterial(10).build();
        CompositionProduct compositionProduct3 = CompositionProduct.builder().product(product1).material(material4).countMaterial(30).build();
        CompositionProduct compositionProduct4 = CompositionProduct.builder().product(product2).material(material2).countMaterial(5).build();
        CompositionProduct compositionProduct5 = CompositionProduct.builder().product(product2).material(material3).countMaterial(15).build();
        CompositionProduct compositionProduct6 = CompositionProduct.builder().product(product2).material(material4).countMaterial(24).build();
        CompositionProduct compositionProduct7 = CompositionProduct.builder().product(product3).material(material5).countMaterial(10).build();
        CompositionProduct compositionProduct8 = CompositionProduct.builder().product(product3).material(material3).countMaterial(25).build();
        CompositionProduct compositionProduct9 = CompositionProduct.builder().product(product3).material(material4).countMaterial(34).build();

        compositionProductService.saveAll(List.of(compositionProduct1, compositionProduct2, compositionProduct3,
                compositionProduct4, compositionProduct5, compositionProduct6, compositionProduct7, compositionProduct8, compositionProduct9));

//-----------       Клиенты
        Person person1 = Person.builder().personName("Михаил").surname("Иванов").age(45).balance(20_000)
                .phoneNumber("+79021933277").build();
        Person person2 = Person.builder().personName("Максим").surname("Максимов").age(34).balance(100_000)
                .phoneNumber("+79021933277").build();
        Person person3 = Person.builder().personName("Сергей").surname("Винюков").age(35).balance(200_000)
                .phoneNumber("+79021933277").build();
        personService.saveAll(List.of(person1, person2, person3));
//-----------       Заказы
        Order order = Order.builder().createdOn(new Timestamp(System.currentTimeMillis()))
                .person(person1)
                .build();

        CompositionOrder compositionOrder = CompositionOrder.builder().product(product1)
                .order(order).countProduct(20).build();
        CompositionOrder compositionOrder1 = CompositionOrder.builder().product(product2)
                .order(order).countProduct(30).build();
        CompositionOrder compositionOrder3 = CompositionOrder.builder().product(product3)
                .order(order).countProduct(10).build();

//        compositionOrderService.saveAll(List.of(compositionOrder, compositionOrder1, compositionOrder3));
        order.setComposition(List.of(compositionOrder, compositionOrder1, compositionOrder3));
        orderService.save(order);
    }
}

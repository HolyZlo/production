package ru.prooftech.production.entities;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "persons")
public class Person  extends RepresentationModel<Person> {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String personName;

    @Column(name = "surname")
    private String surname;
    @Column(name = "age")
    private int age;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "balance")
    private long balance;

    @OneToMany
    private List<Order> listOrders;
}

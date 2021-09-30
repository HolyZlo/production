package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.List;

@Entity(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long idPerson;

    @Column(name = "name")
    private String namePerson;

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

    public Person(String namePerson, String surname, int age, String phoneNumber, long balance) {
        this.namePerson = namePerson;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }
}

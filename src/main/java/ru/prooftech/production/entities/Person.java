package ru.prooftech.production.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long idPerson;

    private String name;
    private String surname;
    private int age;
    private String phoneNumber;
    private long balance;

}

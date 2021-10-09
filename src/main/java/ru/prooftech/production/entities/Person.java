package ru.prooftech.production.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import ru.prooftech.production.resources.PersonResource;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "persons")
@Schema(name = "PersonEntity", description = "Внутренняя сушность клиента")
public class Person extends RepresentationModel<Person> {
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


    public static Person createPersonFromPersonResource(PersonResource personResource) {
        return Person.builder()
                .personName(personResource.getPersonName())
                .surname(personResource.getSurname())
                .phoneNumber(personResource.getPhoneNumber())
                .balance(personResource.getBalance())
                .age(personResource.getAge())
                .build();
    }
}

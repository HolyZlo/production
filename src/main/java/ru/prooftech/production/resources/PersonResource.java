package ru.prooftech.production.resources;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.PersonController;
import ru.prooftech.production.controllers.ProductController;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.entities.Person;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "person", collectionRelation = "persons")
public class PersonResource extends RepresentationModel<PersonResource> {
    private Long idPerson;


    private String namePerson;
    private String surname;
    private int age;
    private String phoneNumber;
    private long balance;

    public PersonResource(Person person) {
        this.idPerson = person.getId();
        this.namePerson = person.getNamePerson();
        this.surname = person.getSurname();
        this.age = person.getAge();
        this.phoneNumber = person.getPhoneNumber();
        this.balance = person.getBalance();

        add(linkTo(methodOn(PersonController.class).getPersonById(person.getId())).withSelfRel());

        // добавить order
    }
}

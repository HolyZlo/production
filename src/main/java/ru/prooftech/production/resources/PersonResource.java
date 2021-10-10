package ru.prooftech.production.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.OrderController;
import ru.prooftech.production.controllers.PersonController;
import ru.prooftech.production.entities.Person;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "person", collectionRelation = "persons")
@Schema(name = "Person", description = "Сущность Клиент")
public class PersonResource extends RepresentationModel<PersonResource> {
    @Schema(description = "Идентификатор клиента - id", example = "5")
    private Long idPerson;

    @Schema(description = "Имя клиента", example = "Иван", required = true)
    private String personName;

    @Schema(description = "Фамилия клиента", example = "Иванов", required = true)
    private String surname;

    @Schema(description = "Возраст клиента, лет", example = "25", minimum = "18", required = true)
    private int age;

    @Schema(description = "Контактный номер клиента", example = "+79011234567", required = true)
    private String phoneNumber;

    @Schema(description = "Баланс счета клиента, руб.", example = "17000", required = true)
    private long balance;

    public PersonResource(Person person) {
        this.idPerson = person.getId();
        this.personName = person.getPersonName();
        this.surname = person.getSurname();
        this.age = person.getAge();
        this.phoneNumber = person.getPhoneNumber();
        this.balance = person.getBalance();
        add(linkTo(methodOn(PersonController.class).getAllPersons()).withRel("parent"));
        add(linkTo(methodOn(PersonController.class).getPersonById(person.getId())).withSelfRel());
        add(linkTo(methodOn(PersonController.class).getOrdersByIdPerson(person.getId())).withRel("orders"));
        // добавить order
    }
}

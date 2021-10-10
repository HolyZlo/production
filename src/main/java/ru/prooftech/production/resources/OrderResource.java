package ru.prooftech.production.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.OrderController;
import ru.prooftech.production.controllers.PersonController;
import ru.prooftech.production.entities.CompositionOrder;
import ru.prooftech.production.entities.Order;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "order", collectionRelation = "orders")
@Schema(name = "Order", description = "Сущность заказа")
public class OrderResource extends RepresentationModel<Order> {
    @Schema(description = "Идентификатор заказа")
    private Long id;

    @Schema(description = "Название заказа, формируется автоматически", example = "Заказ №25,для клиента - Иванов от 2021-10-09 09:42:41.358")
    private String orderName;

    @Schema(description = "Дата открытия(создания) заказа, формируется автоматически")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Schema(description = "Дата закрытия(выполнения) заказа")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedOn;

    @Schema(description = "Сумма заказа, руб.(рассчитывается автоматически)")
    private double inTotal;
    @Schema(description = " Идентификатор клиента",required = true)
    private Long idPerson;
    @Schema(description = "Фамилия клиента")
    private String surname;

    @Schema(description = "Состав заказа")
    private Collection<CompositionOrderResource> composition;

    public OrderResource(Order order) {
        List<CompositionOrderResource> compositionOrderResources = new ArrayList<>();
        order.getComposition().forEach(compositionOrder -> compositionOrderResources.add(new CompositionOrderResource(compositionOrder)));

        this.id = order.getId();
        this.orderName = order.getOrderName();
        this.createdOn = order.getCreatedOn();
        this.closedOn = order.getClosedOn();
        this.inTotal = order.getInTotal();
        this.idPerson = order.getPerson().getId();
        this.surname = order.getPerson().getSurname();
        this.composition = compositionOrderResources;
        add(linkTo(methodOn(OrderController.class).getAllOrders()).withRel("root"));
        add(linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());
        add(linkTo(methodOn(OrderController.class).getCompositionOrderById(order.getId())).withRel("composition"));
        add(linkTo(methodOn(PersonController.class).getPersonById(order.getPerson().getId())).withRel("persons"));


    }


}

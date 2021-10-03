package ru.prooftech.production.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import ru.prooftech.production.controllers.OrderController;
import ru.prooftech.production.controllers.ProductController;
import ru.prooftech.production.entities.CompositionOrder;
import ru.prooftech.production.entities.Order;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Relation(value = "order", collectionRelation = "orders")
public class OrderResource extends RepresentationModel<Order> {
    private Long id;
    private String orderName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Temporal(TemporalType.TIMESTAMP)
    private Date closedOn;
    private double inTotal;
    private Collection<CompositionOrder> composition;

    public OrderResource(Order order) {
        this.id = order.getId();
        this.orderName = order.getOrderName();
        this.createdOn = order.getCreatedOn();
        this.closedOn = order.getClosedOn();
        this.inTotal = order.getInTotal();

        add(linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel());

    }


}

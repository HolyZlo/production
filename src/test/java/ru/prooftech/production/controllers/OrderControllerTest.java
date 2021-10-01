package ru.prooftech.production.controllers;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.prooftech.production.entities.Order;
import ru.prooftech.production.repositories.OrderRepository;
import ru.prooftech.production.repositories.ProductRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @After
    public void tearDown() {
//        orderRepository.deleteAll();
    }

    @Test
    public void testGetOrders() throws Exception {
        Order order = Order.builder().nameOrder("Test1").build();
        orderRepository.save(order);

        ResultActions resultActions = mockMvc.perform(get("/orders/{id}", order.getId())).andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("nameOrder", is("Test1")));

        resultActions.andDo(MockMvcRestDocumentation.document("{class-name}/{method-name}"));
    }


}
package ru.prooftech.production;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import ru.prooftech.production.entities.Product;
import ru.prooftech.production.services.ProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ManufacturingFirmApplicationTests {

    private static ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public static void setProductService(ProductService productService) {
        ManufacturingFirmApplicationTests.productService = productService;
    }

//    @BeforeTestClass
//    public void setUp(){
//         productService.saveProduct(new Product(1L,"Name","Описание",10L));
//         productService.saveProduct(new Product(2L,"Name1","Описание1",100L));
//    }

//@Test
//public void ProductAddRecord() throws Exception {
//    productService.saveProduct(new Product(1L,"Name","Описание",10L));
//    productService.saveProduct(new Product(2L,"Name1","Описание1",100L));
//}

    @Test
    public void noParamProductShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk());
    }
}

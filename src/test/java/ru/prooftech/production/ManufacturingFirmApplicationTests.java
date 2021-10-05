package ru.prooftech.production;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.prooftech.production.services.ProductService;

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


    @Test
    public void noParamProductShouldReturnDefaultMessage() throws Exception {
    }
}

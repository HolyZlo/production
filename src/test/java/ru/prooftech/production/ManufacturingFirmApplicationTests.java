package ru.prooftech.production;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ManufacturingFirmApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void noParamProductShouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk());
    }
}

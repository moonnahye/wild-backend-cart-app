package com.example.demo.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /cart")
    void detail() throws Exception {
        mockMvc.perform(get("/cart"))
                .andExpect(content().string(
                        containsString("lineItems")
                ))
                .andExpect(status().isOk());
    }
}

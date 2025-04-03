package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.model.LineItemId;
import com.example.demo.model.ProductId;
import com.example.demo.model.ProductOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LineItemController.class)
class LineItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    @DisplayName("POST /carts/{cartId}/line-items")
    void addProduct() throws Exception {
        Long cartId = 1L;
        String json = """
                {
                    "productId": "product-1",
                    "color": "blue",
                    "size": "M",
                    "quantity": 2
                }
                """;

        mockMvc.perform(post("/carts/{cartId}/line-items", cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(cartService).addItemToCart(
                eq(cartId),
                eq(new ProductId("product-1")),
                eq(new ProductOption("blue", "M")),
                eq(2)
        );
    }

    @Test
    @DisplayName("DELETE /carts/{cartId}/line-items/{lineItemId}")
    void removeProduct() throws Exception {
        Long cartId = 1L;
        String lineItemId = "lineItem-1";

        mockMvc.perform(delete("/carts/{cartId}/line-items/{lineItemId}", cartId, lineItemId))
                .andExpect(status().isNoContent());

        verify(cartService).removeItemFromCart(
                eq(cartId),
                eq(new LineItemId(lineItemId))
        );
    }
}

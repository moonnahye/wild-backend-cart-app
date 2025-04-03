package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.model.Cart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;


    @Test
    @DisplayName("GET /carts/{cartId}")
    void getCart() throws Exception {
        Long cartId = 1L;
        Cart cart = new Cart();
        when(cartService.getCart(cartId)).thenReturn(cart);

        mockMvc.perform(get("/carts/{cartId}", cartId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /carts/{cartId}")
    void deleteCart() throws Exception {
        Long cartId = 1L;

        mockMvc.perform(delete("/carts/{cartId}", cartId))
                .andExpect(status().isNoContent());

        verify(cartService).clearCart(cartId);
    }
}

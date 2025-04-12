package com.example.demo.controllers;

import com.example.demo.application.CartService;
import com.example.demo.exception.CartNotFoundException;
import com.example.demo.model.Cart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    @DisplayName("GET /cart")
    void getCart() throws Exception {
        String userId = "userA";
        Cart cart = new Cart();

        when(cartService.getCart(userId)).thenReturn(cart);

        mockMvc.perform(get("/cart")
                        .header("Authorization", "Bearer " + userId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /cart - 인증 헤더가 없으면 401 응답")
    void getCart_unauthorized() throws Exception {
        String userId = "userA";
        Cart cart = new Cart();

        when(cartService.getCart(userId)).thenReturn(cart);

        String json = """
                {
                    "code": "UNAUTHORIZED",
                    "message": "인증이 필요합니다."
                }
                """;

        mockMvc.perform(get("/cart"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /cart - 장바구니가 없으면 404를 응답한다")
    void getCart_notFound() throws Exception {
        String userId = "userA";

        when(cartService.getCart(userId))
                .thenThrow(new CartNotFoundException());

        String json = """
                {
                    "code": "CART_NOT_FOUND",
                    "message": "장바구니를 찾을 수 없습니다."
                }
                """;


        mockMvc.perform(get("/cart")
                        .header("Authorization", "Bearer " + userId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /cart")
    void deleteCart() throws Exception {
        String userId = "userA";

        mockMvc.perform(delete("/cart")
                        .header("Authorization", "Bearer " + userId))
                .andExpect(status().isNoContent());

        verify(cartService).clearCart(userId);
    }
}

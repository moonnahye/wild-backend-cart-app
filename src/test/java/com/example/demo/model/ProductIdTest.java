package com.example.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    @Test
    @DisplayName("같은 값이면 같은 객체로 취급된다.")
    void sameProductId() {
        ProductId id1 = new ProductId("productId-1");
        ProductId id2 = new ProductId("productId-1");

        assertThat(id1).isEqualTo(id2);
    }

}

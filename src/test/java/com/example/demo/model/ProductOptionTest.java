package com.example.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductOptionTest {

    @Test
    @DisplayName("값이 같으면 같은 객체로 취급된다.")
    void sameValue() {
        ProductOption option1 = new ProductOption("red", "M");
        ProductOption option2 = new ProductOption("red", "M");

        assertThat(option1).isEqualTo(option2);
    }

    @Test
    @DisplayName("값이 다르면 다른 객체로 취급된다.")
    void differentValue() {
        ProductOption option1 = new ProductOption("red", "M");
        ProductOption option2 = new ProductOption("black", "M");

        assertThat(option1).isNotEqualTo(option2);
    }

}

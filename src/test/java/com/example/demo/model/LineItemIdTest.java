package com.example.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LineItemIdTest {

    @Test
    @DisplayName("generate()를 사용하면 서로다른 아이디가 생성된다.")
    void lineItemIdIsUnique() {
        LineItemId id1 = LineItemId.generate();
        LineItemId id2 = LineItemId.generate();

        assertThat(id1).isNotEqualTo(id2);
    }


}

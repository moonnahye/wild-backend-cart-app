package com.example.demo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LineItemIdTest {

    @Test
    @DisplayName("같은 값이면 같은 객체로 취급된다.")
    void sameLineItemId() {
        LineItemId id1 = new LineItemId("LineItem-1");
        LineItemId id2 = new LineItemId("LineItem-1");

        assertThat(id1).isEqualTo(id2);
    }

    @Test
    @DisplayName("generate()를 사용하면 서로다른 아이디가 생성된다.")
    void lineItemIdIsUnique() {
        LineItemId id1 = LineItemId.generate();
        LineItemId id2 = LineItemId.generate();

        assertThat(id1).isNotEqualTo(id2);
    }


}

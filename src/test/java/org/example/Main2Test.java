package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Main2Test {
    @Test
    void testAddMe() {
        assertEquals(16, Main2.addMe(12, 4));
    }
}
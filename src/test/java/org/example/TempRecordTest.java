package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TempRecordTest {

    @Test
    @DisplayName("Getterit ja setterit toimivat oikein")
    public void testGettersAndSetters() {
        TempRecord record = new TempRecord(1, 20.0, "C", 68.0, "F");

        assertEquals(1, record.getId());
        assertEquals(20.0, record.getInputValue(), 0.001);
        assertEquals("C", record.getInputUnit());
        assertEquals(68.0, record.getConvertedValue(), 0.001);
        assertEquals("F", record.getConvertedUnit());

        record.setId(2);
        record.setInputValue(100.0);
        record.setInputUnit("C");
        record.setConvertedValue(212.0);
        record.setConvertedUnit("F");

        assertEquals(2, record.getId());
        assertEquals(100.0, record.getInputValue(), 0.001);
        assertEquals(212.0, record.getConvertedValue(), 0.001);
    }

    @Test
    @DisplayName("toString muotoilee merkkijonon kahden desimaalin tarkkuudella")
    public void testToString() {
        TempRecord record = new TempRecord(1, 25.5, "C", 77.9, "F");
        assertEquals("25,50 C = 77,90 F", record.toString().replace('.', ',')); // Riippuen lokaalista
    }
}
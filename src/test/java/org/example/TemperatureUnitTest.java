package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TemperatureUnitTest {

    @Test
    @DisplayName("Getterit palauttavat oikeat arvot")
    public void testGetters() {
        TemperatureUnit unit = new TemperatureUnit(1, "C", "Celsius");

        assertEquals(1, unit.getId());
        assertEquals("C", unit.getUnitCode());
        assertEquals("Celsius", unit.getUnitName());
    }

    @Test
    @DisplayName("Setter päivittää yksikön nimen")
    public void testSetUnitName() {
        TemperatureUnit unit = new TemperatureUnit(1, "C", "Celsius");
        unit.setUnitName("Celsius Degree");

        assertEquals("Celsius Degree", unit.getUnitName());
    }

    @Test
    @DisplayName("toString palauttaa unitNamen kun se on määritelty")
    public void testToStringWithName() {
        TemperatureUnit unit = new TemperatureUnit(1, "C", "Celsius");
        assertEquals("Celsius", unit.toString());
    }

    @Test
    @DisplayName("toString palauttaa unitCoden kun unitName on null")
    public void testToStringNullName() {
        TemperatureUnit unit = new TemperatureUnit(1, "C", null);
        assertEquals("C", unit.toString());
    }
}
import org.example.TemperatureConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TemperatureTest {
    @Test
    public void testCelsiusToFahrenheit() {
        assertEquals(32, TemperatureConverter.celsiusToFahrenheit(0), 0.01);
        assertEquals(212, TemperatureConverter.celsiusToFahrenheit(100), 0.01);
    }

    @Test
    public void testFahrenheitToCelsius() {
        assertEquals(0, TemperatureConverter.fahrenheitToCelsius(32), 0.01);
        assertEquals(100, TemperatureConverter.fahrenheitToCelsius(212), 0.01);
    }

    @Test
    public void testIsExtremeTemperature() {
        assertEquals(true, TemperatureConverter.isExtremeTemperature(-50));
        assertEquals(true, TemperatureConverter.isExtremeTemperature(60));
        assertEquals(false, TemperatureConverter.isExtremeTemperature(20));
    }

}

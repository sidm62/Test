import org.example.TemperatureConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TemperatureTest {
    @Test
    public void testCelsiusToFahrenheit() {
        assertEquals(122, TemperatureConverter.celsiusToFahrenheit(50), 0.01);
        assertEquals(626, TemperatureConverter.celsiusToFahrenheit(330), 0.01);
    }

    @Test
    public void testFahrenheitToCelsius() {
        assertEquals(10, TemperatureConverter.fahrenheitToCelsius(50), 0.01);
        assertEquals(25.5, TemperatureConverter.fahrenheitToCelsius(77.9), 0.01);
    }

    @Test
    public void testIsExtremeTemperature() {
        assertEquals(true, TemperatureConverter.isExtremeTemperature(-50));
        assertEquals(true, TemperatureConverter.isExtremeTemperature(60));
        assertEquals(false, TemperatureConverter.isExtremeTemperature(20));
    }

}

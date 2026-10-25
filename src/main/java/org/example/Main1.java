package org.example;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TemperatureConverter converter = new TemperatureConverter();

        System.out.println("Valitse kieli:");
        System.out.println("1. Suomi");
        System.out.println("2. English");
        System.out.println("3. Japani");
        System.out.println("4. Ruotsi");
        int choice = scanner.nextInt();

        Locale locale = switch (choice) {
            case 1 -> new Locale("en", "US");
            case 2 -> new Locale ("fi", "FI");
            case 3 -> new Locale("ja", "JP");
            case 4 -> new Locale("sv", "SE");
            default -> new Locale("fi", "FI");
        };
          ResourceBundle rb = ResourceBundle.getBundle("MessagesBundle", locale);

        System.out.println(rb.getString("prompt_celsius"));
        double luku = scanner.nextDouble();
        double fahrenheit = converter.celsiusToFahrenheit(luku);
        System.out.println(rb.getString("celsius_to_fahrenheit") + fahrenheit);

        System.out.println(rb.getString("prompt_fahrenheit"));
        double luku2 = scanner.nextDouble();
        double celsius = converter.fahrenheitToCelsius(luku2);
        System.out.println(rb.getString("fahrenheit_to_celsius") + celsius);

        System.out.println(rb.getString("prompt_kelvin"));
        double luku3 = scanner.nextDouble();
        double kelvin = converter.kelvinToCelsius(luku3);
        System.out.println(rb.getString("kelvin_to_celsius") + kelvin);

        scanner.close();
    }
}
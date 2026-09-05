package org.example;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Temperature Converter: ");
        int temp = input.nextInt();
        System.out.println("Temperature in Fahrenheit: " + TemperatureConverter.celsiusToFahrenheit(temp));
        System.out.println("Temperature in Celsius: " + TemperatureConverter.fahrenheitToCelsius(temp));

        System.out.println("Is the temperature extreme? " + TemperatureConverter.isExtremeTemperature(temp));
        input.close();
    }
}

package org.example;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main2 {


    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        // Calculate the result
        int result = addMe(12, 4);
        // Log the result only if INFO level is enabled
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("Result of addMe(12, 4): %d", result));
        }
    }
    public static int addMe(int a, int b) {
        return a + b;
    }
}
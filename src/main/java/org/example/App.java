package org.example;

import javafx.application.Application;
import javafx.application.Platform;

public class App {
    public static void main(String[] args) {

        Platform.setImplicitExit(false);


        new Thread(() -> Application.launch(Main.class, args)).start();


        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
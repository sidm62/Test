package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {
    private final TempRecordDao tempRecordDao = new TempRecordDao();
    private final TemperatureUnitDAO temperatureUnitDao = new TemperatureUnitDAO();
    private TextField inputField = new TextField();
    private Label resultLabel = new Label("Tulos: ");
    private TableView<TempRecord> tableView = new TableView<>();
    private ResourceBundle rb;
    private ComboBox<String> languageComboBox = new ComboBox<>();
    private final Button convertf = new Button();
    private final Button convertc = new Button();
    private final Button convertk = new Button();
    private final Label langSelectLabel = new Label();


    @Override
    public void start(Stage primaryStage) {
       languageComboBox.getItems().addAll("Suomi", "English", "Japani", "Ruotsi", "Arabia");
        languageComboBox.setValue("Suomi");
        languageComboBox.setOnAction((e) ->updateLanguage(languageComboBox.getValue()));

        primaryStage.setTitle("Lämpötilamuunnin");





        convertf.setOnAction(e -> convertF());
        convertc.setOnAction(e -> convertC());
        convertk.setOnAction(e -> convertK());




        TableColumn<TempRecord, Integer> convertInput = new TableColumn<>("Celsius");
        convertInput.setCellValueFactory(new PropertyValueFactory<>("inputValue"));

        TableColumn<TempRecord, Double> convertResult = new TableColumn<>("Fahrenheit");
        convertResult.setCellValueFactory(new PropertyValueFactory<>("convertedValue"));

        tableView.getColumns().add(convertInput);
        tableView.getColumns().add(convertResult);
        updateLanguage("Suomi");
        refreshTable();

        VBox root = new VBox(10, langSelectLabel, languageComboBox, inputField, convertf, convertc, convertk, resultLabel, tableView);
        root.setPadding(new Insets(15));

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();



    }

    private void convertF() {
        try {
            double celsius = Double.parseDouble(inputField.getText());
            double fahrenheit = (celsius * 9 / 5) + 32;

            resultLabel.setText(String.format("Tulos: %.2f °F", fahrenheit));

            TempRecord record = new TempRecord(0, celsius, "C", fahrenheit, "F");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText("Virheellinen syöte. Syötä numero.");
        }
    }
    private void convertC() {
        try {
            double fahrenheit = Double.parseDouble(inputField.getText());
            double celsius = (fahrenheit - 32) * 5 / 9;

            resultLabel.setText(String.format("Tulos: %.2f °C", celsius));

            TempRecord record = new TempRecord(0, fahrenheit, "F", celsius, "C");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText("Virheellinen syöte. Syötä numero.");
        }
    }
    private void convertK() {
        try {
            double kelvin = Double.parseDouble(inputField.getText());
            double celsius = kelvin - 273.15;

            resultLabel.setText(String.format("Tulos: %.2f °C", celsius));

            TempRecord record = new TempRecord(0, kelvin, "K", celsius, "C");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText("Virheellinen syöte. Syötä numero.");
        }
    }


private void refreshTable() {
    tableView.getItems().clear();
    tableView.getItems().addAll(tempRecordDao.getAll());
}

private void updateLanguage(String selectedLanguage) {
        Locale locale = switch (selectedLanguage) {
            case "Suomi" -> new Locale("fi", "FI");
            case "English" -> new Locale("en", "US");
            case "Japani" -> new Locale("ja", "JP");
            case "Ruotsi" -> new Locale("sv", "SE");
            case "Arabia" -> new Locale("ar", "AR");
            default -> new Locale("fi", "FI");
        };
        rb = ResourceBundle.getBundle("MessagesBundle", locale);

        langSelectLabel.setText(rb.getString("select-language"));
        inputField.setPromptText(rb.getString("prompt_celsius"));
         convertf.setText(rb.getString("prompt_fahrenheit"));
         convertc.setText(rb.getString("prompt_celsius"));
         convertk.setText(rb.getString("prompt_kelvin"));






}


}









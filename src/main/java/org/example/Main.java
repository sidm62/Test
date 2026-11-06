package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    private final TempRecordDao tempRecordDao = new TempRecordDao();

    private final TextField inputField = new TextField();
    private final Label resultLabel = new Label();
    private final TableView<TempRecord> tableView = new TableView<>();
    private final ComboBox<String> languageComboBox = new ComboBox<>();

    private final Button convertf = new Button();
    private final Button convertc = new Button();
    private final Button convertk = new Button();
    private final Label langSelectLabel = new Label();

    private final TableColumn<TempRecord, Double> convertInput = new TableColumn<>();
    private final TableColumn<TempRecord, Double> convertResult = new TableColumn<>();

    private final ComboBox<UnitDTO> unitComboBox = new ComboBox<>();
    private VBox root;
    private ResourceBundle rb;

    @Override
    public void start(Stage primaryStage) {
        languageComboBox.getItems().addAll("Suomi", "English", "Japani", "Ruotsi", "Arabia");
        languageComboBox.setValue("Suomi");
        languageComboBox.setOnAction((e) -> updateLanguage(languageComboBox.getValue()));

        primaryStage.setTitle("Lämpötilamuunnin");

        convertf.setOnAction(e -> convertF());
        convertc.setOnAction(e -> convertC());
        convertk.setOnAction(e -> convertK());


        convertInput.setCellValueFactory(new PropertyValueFactory<>("inputValue"));
        convertResult.setCellValueFactory(new PropertyValueFactory<>("convertedValue"));

        tableView.getColumns().add(convertInput);
        tableView.getColumns().add(convertResult);

        root = new VBox(10, langSelectLabel, languageComboBox, unitComboBox, inputField, convertf, convertc, convertk, resultLabel, tableView);
        root.setPadding(new Insets(15));

        updateLanguage("Suomi");
        refreshTable();

        primaryStage.setScene(new Scene(root, 450, 450));
        primaryStage.show();
    }

    private void convertF() {
        try {
            double celsius = Double.parseDouble(inputField.getText());
            double fahrenheit = (celsius * 9 / 5) + 32;

            resultLabel.setText(String.format("%s: %.2f °F", rb.getString("result"), fahrenheit));

            TempRecord record = new TempRecord(0, celsius, "C", fahrenheit, "F");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText(rb.getString("error_invalid_input"));
        }
    }

    private void convertC() {
        try {
            double fahrenheit = Double.parseDouble(inputField.getText());
            double celsius = (fahrenheit - 32) * 5 / 9;

            resultLabel.setText(String.format("%s: %.2f °C", rb.getString("result"), celsius));

            TempRecord record = new TempRecord(0, fahrenheit, "F", celsius, "C");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText(rb.getString("error_invalid_input"));
        }
    }

    private void convertK() {
        try {
            double kelvin = Double.parseDouble(inputField.getText());
            double celsius = kelvin - 273.15;

            resultLabel.setText(String.format("%s: %.2f °C", rb.getString("result"), celsius));

            TempRecord record = new TempRecord(0, kelvin, "K", celsius, "C");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText(rb.getString("error_invalid_input"));
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
            case "Arabia" -> new Locale("ar", "AR");default -> new Locale("fi", "FI");
        };


        rb = ResourceBundle.getBundle("MessagesBundle", locale);

        langSelectLabel.setText(rb.getString("select-language"));
        inputField.setPromptText(rb.getString("prompt_celsius"));
        convertf.setText(rb.getString("prompt_fahrenheit"));
        convertc.setText(rb.getString("prompt_celsius"));
        convertk.setText(rb.getString("prompt_kelvin"));
        resultLabel.setText(rb.getString("result") + ": ");


        convertInput.setText(rb.getString("table_input"));
        convertResult.setText(rb.getString("table_result"));


        String langCode = locale.getLanguage();
        List<UnitDTO> dbUnits = tempRecordDao.getUnitsByLanguage(langCode);

        unitComboBox.getItems().clear();
        unitComboBox.getItems().addAll(dbUnits);
        if (!dbUnits.isEmpty()) {
            unitComboBox.setValue(dbUnits.get(0));
        }


        if (dbUnits.size() >= 3) {
            convertc.setTooltip(new Tooltip(dbUnits.get(0).getDescription())); // Celsius
            convertf.setTooltip(new Tooltip(dbUnits.get(1).getDescription())); // Fahrenheit
            convertk.setTooltip(new Tooltip(dbUnits.get(2).getDescription())); // Kelvin
        }


        if ("ar".equals(langCode)) {
            root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
    }
}
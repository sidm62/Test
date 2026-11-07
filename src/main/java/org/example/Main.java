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
    private String currentLangCode = "fi";

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

            String resultText = tempRecordDao.getTranslation("result", currentLangCode);
            resultLabel.setText(String.format("%s: %.2f °F", resultText, fahrenheit));

            TempRecord record = new TempRecord(0, celsius, "C", fahrenheit, "F");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText(tempRecordDao.getTranslation("error_invalid_input", currentLangCode));
        }
    }

    private void convertC() {
        try {
            double fahrenheit = Double.parseDouble(inputField.getText());
            double celsius = (fahrenheit - 32) * 5 / 9;

            String resultText = tempRecordDao.getTranslation("result", currentLangCode);
            resultLabel.setText(String.format("%s: %.2f °C", resultText, celsius));

            TempRecord record = new TempRecord(0, fahrenheit, "F", celsius, "C");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText(tempRecordDao.getTranslation("error_invalid_input", currentLangCode));
        }
    }

    private void convertK() {
        try {
            double kelvin = Double.parseDouble(inputField.getText());
            double celsius = kelvin - 273.15;

            String resultText = tempRecordDao.getTranslation("result", currentLangCode);
            resultLabel.setText(String.format("%s: %.2f °C", resultText, celsius));

            TempRecord record = new TempRecord(0, kelvin, "K", celsius, "C");
            tempRecordDao.save(record);
            refreshTable();
        } catch (NumberFormatException e) {
            resultLabel.setText(tempRecordDao.getTranslation("error_invalid_input", currentLangCode));
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

        currentLangCode = locale.getLanguage();
        rb = ResourceBundle.getBundle("MessagesBundle", locale);


        String resultText = tempRecordDao.getTranslation("result", currentLangCode);
        String tableInputText = tempRecordDao.getTranslation("table_input", currentLangCode);
        String tableResultText = tempRecordDao.getTranslation("table_result", currentLangCode);


        langSelectLabel.setText(rb.getString("select-language"));
        inputField.setPromptText(rb.getString("prompt_celsius"));
        convertf.setText(rb.getString("prompt_fahrenheit"));
        convertc.setText(rb.getString("prompt_celsius"));
        convertk.setText(rb.getString("prompt_kelvin"));


        resultLabel.setText(resultText + ": ");
        convertInput.setText(tableInputText);
        convertResult.setText(tableResultText);

        List<UnitDTO> dbUnits = tempRecordDao.getUnitsByLanguage(currentLangCode);

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

        if ("ar".equals(currentLangCode)) {
            root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        } else {
            root.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
    }
}
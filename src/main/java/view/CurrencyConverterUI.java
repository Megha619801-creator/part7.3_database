package view;

import controller.CurrencyController;
import entity.currency;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class CurrencyConverterUI extends Application {

    private CurrencyController controller;
    private ComboBox<String> fromBox;
    private ComboBox<String> toBox;

    @Override
    public void init() {
        controller = new CurrencyController();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Currency Converter");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Top instructions
        Label instructions = new Label("Enter an amount and choose currencies to convert.");
        root.setTop(instructions);
        BorderPane.setAlignment(instructions, Pos.CENTER);

        // Center form
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        Label fromLabel = new Label("From Currency:");
        fromBox = new ComboBox<>();

        Label toLabel = new Label("To Currency:");
        toBox = new ComboBox<>();

        Label resultLabel = new Label("Result:");
        TextField resultField = new TextField();
        resultField.setEditable(false);

        grid.add(amountLabel, 0, 0);
        grid.add(amountField, 1, 0);
        grid.add(fromLabel, 0, 1);
        grid.add(fromBox, 1, 1);
        grid.add(toLabel, 0, 2);
        grid.add(toBox, 1, 2);
        grid.add(resultLabel, 0, 3);
        grid.add(resultField, 1, 3);

        root.setCenter(grid);

        // Populate ComboBoxes
        refreshCurrencies();

        // Bottom buttons
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button convertBtn = new Button("Convert");
        Button addCurrencyBtn = new Button("Add Currency");

        buttons.getChildren().addAll(convertBtn, addCurrencyBtn);
        root.setBottom(buttons);

        // Convert action
        convertBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String from = fromBox.getValue();
                String to = toBox.getValue();

                if (from == null || to == null) {
                    resultField.setText("Select both currencies!");
                    return;
                }

                double result = controller.convert(amount, from, to);
                resultField.setText(String.format("%.2f", result));
            } catch (NumberFormatException ex) {
                resultField.setText("Invalid amount!");
            } catch (Exception ex) {
                resultField.setText("Error fetching rates!");
            }
        });

        // Add currency action
        addCurrencyBtn.setOnAction(e -> openAddCurrencyWindow());

        Scene scene = new Scene(root, 450, 350);
        stage.setScene(scene);
        stage.show();
    }

    private void refreshCurrencies() {
        List<String> currencies = controller.getAllCurrencyAbbreviations();
        fromBox.getItems().setAll(currencies);
        toBox.getItems().setAll(currencies);
    }

    private void openAddCurrencyWindow() {
        Stage newStage = new Stage();
        newStage.setTitle("Add New Currency");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        Label nameLabel = new Label("Currency Name:");
        TextField nameField = new TextField();

        Label abbrLabel = new Label("Abbreviation:");
        TextField abbrField = new TextField();

        Label rateLabel = new Label("Rate (to base currency):");
        TextField rateField = new TextField();

        Button saveBtn = new Button("Save");

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(abbrLabel, 0, 1);
        grid.add(abbrField, 1, 1);
        grid.add(rateLabel, 0, 2);
        grid.add(rateField, 1, 2);
        grid.add(saveBtn, 1, 3);

        saveBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String abbr = abbrField.getText();
                double rate = Double.parseDouble(rateField.getText());

                if (name.isEmpty() || abbr.isEmpty()) {
                    showAlert("Error", "Name and abbreviation cannot be empty.");
                    return;
                }

                controller.saveCurrency(name, abbr, rate);
                newStage.close();
                refreshCurrencies(); // Refresh ComboBoxes in main window
            } catch (NumberFormatException ex) {
                showAlert("Error", "Rate must be a number.");
            } catch (Exception ex) {
                showAlert("Error", "Failed to save currency.");
            }
        });

        Scene scene = new Scene(grid, 350, 200);
        newStage.setScene(scene);
        newStage.showAndWait(); // Wait until this stage closes
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

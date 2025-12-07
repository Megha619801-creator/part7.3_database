package application;

import view.CurrencyConverterUI;
import datasource.MariaDbJpaConnection;

public class CurrencyApp {

    public static void main(String[] args) {
        new CurrencyConverterUI(); // Launch the UI
        Runtime.getRuntime().addShutdownHook(new Thread(() -> MariaDbJpaConnection.close()));
    }
}

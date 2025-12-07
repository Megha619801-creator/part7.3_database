package entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double amount;

    private double result;

    private LocalDateTime timestamp;

    @ManyToOne
    private currency sourceCurrency;

    @ManyToOne
    private currency targetCurrency;

    public Transaction() {}

    public Transaction(double amount, double result, currency sourceCurrency, currency targetCurrency) {
        this.amount = amount;
        this.result = result;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public double getResult() {
        return result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public currency getSourceCurrency() {
        return sourceCurrency;
    }

    public currency getTargetCurrency() {
        return targetCurrency;
    }
}
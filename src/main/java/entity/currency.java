
package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "currency")
public class currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "abbreviation", nullable = false, unique = true)
    private String abbreviation;

    @Column(name = "name", nullable = false)
    private String name;

    // maps to rate_to_usd column
    @Column(name = "rate_to_usd", nullable = false)
    private double rateToUsd;

    public currency() {
    }

    public currency(String abbreviation, String name, double rateToUsd) {
        this.abbreviation = abbreviation;
        this.name = name;
        this.rateToUsd = rateToUsd;
    }

    public Integer getId() {
        return id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRateToUsd() {
        return rateToUsd;
    }

    public void setRateToUsd(double rateToUsd) {
        this.rateToUsd = rateToUsd;
    }

    @Override
    public String toString() {
        // how the currency shows in ComboBoxes
        return abbreviation + " (" + name + ")";
    }
}


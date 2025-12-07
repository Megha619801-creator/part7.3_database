
package controller;

import dao.CurrencyDao;
import entity.currency;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyController {

    private final CurrencyDao currencyDao = new CurrencyDao();

    public List<currency> getAllCurrencies() {
        return currencyDao.findAll();
    }

    public List<String> getAllCurrencyAbbreviations() {
        return getAllCurrencies().stream().map(currency::getAbbreviation).collect(Collectors.toList());
    }

    public double convert(double amount, String fromAbbr, String toAbbr) {
        currency from = currencyDao.findByAbbreviation(fromAbbr);
        currency to = currencyDao.findByAbbreviation(toAbbr);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Currency not found in database");
        }
        // amount * (to.rateToUsd / from.rateToUsd)
        return amount * (to.getRateToUsd() / from.getRateToUsd());
    }

    public boolean addCurrency(String abbr, String name, double rateToUsd) {
        // check duplicate
        if (currencyDao.findByAbbreviation(abbr) != null) {
            return false;
        }
        currency c = new currency(abbr, name, rateToUsd);
        currencyDao.persist(c);
        return true;
    }

    public void saveCurrency(String name, String abbreviation, double rate) {
        currency currency = new currency(name, abbreviation, rate);
        currencyDao.persist(currency); // Insert into DB
    }
}


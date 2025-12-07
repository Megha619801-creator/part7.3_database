package controller;

import dao.CurrencyDao;
import dao.TransactionDao;
import entity.currency;
import entity.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyController {

    private CurrencyDao currencyDao;
    private TransactionDao transactionDao;

    public CurrencyController() {
        currencyDao = new CurrencyDao();
        transactionDao = new TransactionDao();
    }

    public List<String> getAllCurrencyAbbreviations() {
        List<currency> currencies = currencyDao.findAll();
        return currencies.stream()
                .map(currency::getAbbreviation)
                .collect(Collectors.toList());
    }

    public double convert(double amount, String fromAbbr, String toAbbr) {
        currency from = currencyDao.findByAbbreviation(fromAbbr);
        currency to = currencyDao.findByAbbreviation(toAbbr);

        double result = amount * (to.getRateToUsd() / from.getRateToUsd());

        // Store the transaction in the database
        Transaction transaction = new Transaction(amount, result, from, to);
        transactionDao.persist(transaction);

        return result;
    }

    public void saveCurrency(String name, String abbreviation, double rate) {
        currency currency = new currency(name, abbreviation, rate);
        currencyDao.persist(currency);
    }
}

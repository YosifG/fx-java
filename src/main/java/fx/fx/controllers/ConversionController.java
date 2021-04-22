package fx.fx.controllers;
import fx.fx.classes.*;
import fx.fx.repository.*;
import fx.fx.exceptions.*;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

/**
 * ConversionController.java
 *
 * Controller responsible for converting money in different currencies.
 * Currently has a single get mapping at /convert.
 * @author Yosif Gorelyov
 * @date 20/04/2021
 */

@RestController
public class ConversionController {

    @Autowired
    private TransactionRepository transactionRepository;
    
    /**
     * GET /convert
     * @param amount - the amount of money to be exchanged
     * @param sourceCurrency - the currency of the user's money.
     * @param targetCurrency - the currency the user wants to exchange his money to.
     * Both sourceCurrency and targetCurrency are three letter currency codes, e.g. USD, EUR...
     * Supports lowercase currency codes, though uppercase is the standart.
     * NOTE: Due to API restrictions, the source currency has to be USD.
     * @return the converted amount and id with keys "exchangedAmount" and "id" respectively.
     * @throws CurrencyPairLengthException
     * @throws CurrencyPairSyntaxException
     * @throws NegativeAmountException
     */
    @GetMapping("/convert")
    public JSONObject getExchangeRate(@RequestParam Double amount,  
                                      @RequestParam String sourceCurrency, 
                                      @RequestParam String targetCurrency) throws CurrencyPairLengthException, 
                                                                                  CurrencyPairSyntaxException, 
                                                                                  NegativeAmountException {

        JSONObject idAndExchangedAmount;

        //currency codes always have to be uppercase.
        CurrencyPair currencyPair = new CurrencyPair(sourceCurrency.toUpperCase(), targetCurrency.toUpperCase());
        double exchangedAmount = currencyPair.exchange(amount);
        String date = String.valueOf(LocalDate.now());
        
        Transaction transaction = new Transaction(sourceCurrency, targetCurrency, amount, exchangedAmount, date);
        transactionRepository.save(transaction);
        
        idAndExchangedAmount = transaction.toJson();
        return idAndExchangedAmount;
    }
}
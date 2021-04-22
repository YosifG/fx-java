package fx.fx.controllers;
import fx.fx.classes.*;
import fx.fx.exceptions.*;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;

/**
 * ExchangeRateController.java
 *
 * Controller responsible for getting the exchange rate of a currency pair.
 * Currently has a single get mapping at /exchangerate.
 * @author Yosif Gorelyov
 * @date 20/04/2021
 */

@RestController
public class ExchangeRateController {

    /**
     * GET /exchangerate
     * @param currencyPair - The source currency code followed by the target currency code, e.g. USDEUR.,
     *                                                     where USD is the source and EUR is the target.
     * As each currency code is exactly 3 symbols, the currency pair should be exactly 6 symbols.
     * Supports lowercase currency codes, though uppercase is the standart.
     * NOTE: Due to API restrictions, the source currency has to be USD.
     * @return the current exchange rate of the two currencies, as JSON with key "rate".
     * @throws CurrencyPairSyntaxException
     * @throws IncorrectSyntaxException
     */
    @GetMapping("/exchangerate")
    public JSONObject getExchangeRate(@RequestParam String currencyPair) throws CurrencyPairLengthException, 
                                                                                CurrencyPairSyntaxException {
        JSONObject exchangeRate;

        //currency codes have to be in uppercase.
        CurrencyPair currencyPairObj = new CurrencyPair(currencyPair.toUpperCase());
        exchangeRate = currencyPairObj.getExchangeRateAsJson();
      
        return exchangeRate;
    }
}
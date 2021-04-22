package fx.fx.classes;

import fx.fx.exceptions.*;

import java.io.IOException;

import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import net.minidev.json.JSONObject;

/**
 * CurrencyPair.java
 *
 * Represents  the combination of two currencies, e.g. USDEUR.
 * Contains methods to get the current exchange rate of two currencies and to perform exchanges.
 * @see CurrencyPair#getExchangeRate()
 * @see CurrencyPair#exchange(int)
 * 
 * @author Yosif Gorelyov
 * @date 20/04/2021
 */
public class CurrencyPair {

    private String sourceCurrency;
    private String targetCurrency;
    private String currencyPair;

    /**
     * Constructor for a CurrencyPair object
     * @param currencyPair - a 6-letter, valid combination of two currencies, e.g. USDEUR.
     * The first currency is the source, the second is the target.
     * @throws IncorrectSyntaxException
     */
    public CurrencyPair(String currencyPair) throws CurrencyPairLengthException {
        if (currencyPair.length() != 6) {
            throw new CurrencyPairLengthException();
        }
        else {
            String source = currencyPair.substring(0, 3);
            String target = currencyPair.substring(3, 6);
            setSourceCurrency(source);
            setTargetCurrency(target);
            setCurrencyPair(currencyPair);

        }
    }

    /**
     * Constructor for a CurrencyPair object
     * Combines a source and a target currency to create a CurrencyPair.
     * @param sourceCurrency - the source currency as a 3-letter string, e.g. EUR.
     * @param targetCurrency - the target currency as a 3-letter string, e.g. USD.
     * @throws IncorrectSyntaxException
     */
    public CurrencyPair (String sourceCurrency, String targetCurrency) throws CurrencyPairLengthException {
        if ((sourceCurrency + targetCurrency).length() != 6) {
            throw new CurrencyPairLengthException();
        }
        setSourceCurrency (sourceCurrency);
        setTargetCurrency (targetCurrency);
        setCurrencyPair (sourceCurrency + targetCurrency);
    }

    /**
     * @return the source currency as a 3-letter string, e.g. EUR.
     */
    public String getSourceCurrency() {
        return sourceCurrency;
    }

     /**
     * @return the target currency as a 3-letter string, e.g. EUR.
     */
    public String getTargetCurrency() {
        return targetCurrency;
    }

    /**
     * @return the object's pair of currencies. The first three letters are the source, the other three- the target.
     */
    public String getCurrencyPair() {
        return currencyPair;
    }

    /**
     * @param sourceCurrency must be a 3-letter valid currency code, e.g. EUR.
     */
    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    /**
     * @param targetCurrency must be a 3-letter valid currency code, e.g. EUR.
     */
    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    /**
     * @param currencyPair must be 6-letter string, combination of two currency codes, e.g. USDEUR.
     */
    public void setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
    }

    /**
     * Reads the Currency Layer API to get the current exchange rate for this CurrencyPair object.
     * @return the exchange rate
     * @throws CurrencyPairSyntaxException
     */
    public double getExchangeRate() throws CurrencyPairSyntaxException {

        //must be initialized.
        double exchangeRate = 0.0;
        try {

            //Build a UriComponent using the Currency Layer API Url.
            //.buildAndExpand passes the target and source currencies in place of {target} and {source}.
            String apiUrl = "http://apilayer.net/api/live?access_key=f5bff8b046fcaa733861ec0ba01119b3&currencies={target}&source={source}&format=1";
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                                                    .buildAndExpand(this.targetCurrency, this.sourceCurrency);
            URL exchangeRateUrl = uri.toUri().toURL();

            //A Jackson JsonNode stores all of the JSON from the url. The exchange rate value is nested in "quotes",
            //and the key is the currencyPair.
            JsonNode productNode = new ObjectMapper().readTree(exchangeRateUrl);

            //If an invalid call is made, it means an invalid currency pair has been provided. success will be false.
            if (productNode.get("success").asBoolean() == false) {
                throw new CurrencyPairSyntaxException(); 
            }

            exchangeRate = productNode.get("quotes").get(this.currencyPair).asDouble();

        } catch (IOException e) {
            // Jackson could not read the input properly.
            e.printStackTrace();
        }

        return exchangeRate;
    }

    /**
     * Returns the exchange rate of the CurrencyPair object as JSON. The key is "rate".
     * @see CurrencyPair#getExchangeRate()
     * @return the exchange rate.
     * @throws CurrencyPairSyntaxException
     */
    public JSONObject getExchangeRateAsJson() throws CurrencyPairSyntaxException {
        double exchangeRate = getExchangeRate();
        JSONObject rate=new JSONObject();
        rate.put("rate:",exchangeRate);     
        return rate;
    }
    
    /**
     * Using the object's target and source currencies, performs an exchange of a given amount.
     * @param amount - the amount of money to be exchanged from the source currency.
     * @return - the amount of money in the target currency.
     * @throws CurrencyPairSyntaxException
     * @throws NegativeAmountException
     */
    public double exchange(Double amount) throws CurrencyPairSyntaxException, NegativeAmountException {
        if (amount < 0.0) {
            throw new NegativeAmountException();
        }
        double exchangeRate = getExchangeRate();

        //To perform a currency exchange, the user's money is multiplied by the current exchange rate.
        double exchangedAmount = amount * exchangeRate;
        return exchangedAmount;
    }    
}
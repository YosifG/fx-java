package fx.fx.classes;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.minidev.json.JSONObject;

/**
 * Transaction.java
 *
 * Represents a Transaction performed by the ConveresionController.
 * The variables of this object are saved in the repository.
 * @author Yosif Gorelyov
 * @date 20/04/2021
 */
@Entity
public class Transaction {

    //id is auto generated upon every created transaction,
    //which is also why there is no set method for it.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String sourceCurrency;
    private String targetCurrency;
    private double amount;
    private double exchangedAmount;
    private String date;

    /**
     * Empty constructor needed for the repository.
     */
    Transaction() {

    }

    /**
     * Constructor for a Transaction object.
     * @param sourceCurrency the source currency as a 3-letter string, e.g. EUR.
     * @param targetCurrency the target  currency as a 3-letter string, e.g. EUR.
     * @param amount the amount of money in the source currency.
     * @param exchangedAmount the exchanged amount of money in the target currency
     * @param date the date when the transaction was performed. 
     */
    public Transaction(String sourceCurrency, String targetCurrency, double amount, double exchangedAmount, String date) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.exchangedAmount = exchangedAmount;
        this.date = date;
    }

    /**
     * @return the object's id.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the source currency as a 3-letter string, e.g. EUR.
     */
    public String getSourceCurrency(){
        return this.sourceCurrency;
    }

    /**
     * @return the target currency as a 3-letter string, e.g. EUR.
     */
    public String getTargetCurrency(){
        return this.targetCurrency;
    }

    /**
     * @return the amount in the source currency.
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     * @return the amount of money after performing an exchange.
     */
    public double getExchangedAmount(){
        return this.exchangedAmount;
    }

    /**
     * @return the date when the transaction was performed.
     */
    public String getDate(){
        return this.date;
    }

    /**
     * @param sourceCurrency - must be a 3-letter valid currency code, e.g. EUR.
     */
    public void setSourceCurrency(String sourceCurrency){
        this.sourceCurrency = sourceCurrency;
    }

    /**
     * @param targetCurrency must be a 3-letter valid currency code, e.g. EUR.
     */
    public void setTargetCurrency(String targetCurrency){
        this.targetCurrency = targetCurrency;
    }

    /**
     * @param amount - the amount of money in the source currency.
     */
    public void setAmount(double amount){
        this.amount = amount;
    }

    /**
     * @param exchangedAmount - the amount of money after performing an exchange
     */
    public void setExchangedAmount(double exchangedAmount){
        this.exchangedAmount = exchangedAmount;
    }

    public void setDate(LocalDate date){
        this.date = String.valueOf(date);
    }

    /**
     * @param date - the date when the transaction was performed.
     */
    public void setDate(String date){
        this.date = date;
    }

    /**
     * Converts the id and exchanged amount to JSON. The keys are "id" and "exchangedAmount" respectively.
     * @return the id and exchanged amount as JSON.
     */
    public JSONObject toJson(){
        JSONObject idAndExchangedAmount = new JSONObject();
        idAndExchangedAmount.put("id:",this.id);  
        idAndExchangedAmount.put("exchangedAmount:", this.exchangedAmount);  
        return idAndExchangedAmount;
    }
}
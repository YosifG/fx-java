package fx.fx.controllers;
import fx.fx.exceptions.*;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.minidev.json.JSONObject;

/**
 * GlobalExceptionHandler.java
 *
 * A global exception handling system. Catches the exceptions thrown by each controller.

 * @author Yosif Gorelyov
 * @date 20/04/2021
 */
@ControllerAdvice
class GlobalExceptionHandler {    

    /**
     * Each method catches an exception and returns an appropriate error message in the body as JSON.
     */
    
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CurrencyPairLengthException.class)
    public JSONObject CurrencyPairLengthException() {
        JSONObject errorMessage = new JSONObject();
        errorMessage.put("status:", "400");
        errorMessage.put("message:", "currencyPair must be exactly 6 symbols!");
        return errorMessage;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CurrencyPairSyntaxException.class)
    public JSONObject CurrencyPairSyntaxException() {
        JSONObject errorMessage = new JSONObject();
        errorMessage.put("status:", "400");
        errorMessage.put("message:", "Currency pair does not exist.");
        return errorMessage;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NegativeAmountException.class)
    public JSONObject NegativeAmountException() {
        JSONObject errorMessage = new JSONObject();
        errorMessage.put("status:", "400");
        errorMessage.put("message:", "Amount must be a positive number");
        return errorMessage;
    }
}
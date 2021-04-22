package fx.fx;
import fx.fx.exceptions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import fx.fx.classes.CurrencyPair;
import fx.fx.controllers.ExchangeRateController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class FxApplicationTests {

	@Autowired
	private ExchangeRateController controller;

	/**
	 * Sanity check test
	 * @throws Exception
	 */
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	/**
	 * Testing the valid creation of a currency pair using both constructors.
	 * @throws Exception
	 */
	@Test
	public void currencyPairCreation() throws Exception {
		CurrencyPair currencyPair = new CurrencyPair("USDEUR");
		CurrencyPair currencyPairAlt = new CurrencyPair("USD", "EUR");
		boolean pairsAreEqual = currencyPair.getCurrencyPair().equals(currencyPairAlt.getCurrencyPair());
		assertThat(pairsAreEqual);
	}

	/**
	 * Tests if a CurrencyPair of incorrect length can be created.
	 * @throws Exception
	 */
	@Test
	public void CurrencyPairLengthExceptionTest() throws Exception {
		assertThrows(CurrencyPairLengthException.class, () -> {
			CurrencyPair currencyPair = new CurrencyPair("USDAAEUR");
		});
	}

	/**
	 * Tests the basic functionality of getExchangeRate - should return a positive double.
	 * @throws Exception
	 */
	@Test
	public void getExchangeRateTest() throws Exception {
		CurrencyPair currencyPair = new CurrencyPair("USDEUR");
		double exchangeRate = currencyPair.getExchangeRate();
		assertThat (exchangeRate > 0.0);
	}

	/**
	 * Test using an invalid CurrencyPair to get an exchange rate.
	 * @throws Exception
	 */
	@Test
	public void CurrencyPairSyntaxExceptionTest() throws Exception {
		CurrencyPair currencyPair = new CurrencyPair("AAABBB");
		assertThrows(CurrencyPairSyntaxException.class, () -> { 
			currencyPair.getExchangeRate();
		});
	}

	/**
	 * Tests if an exchange with a negative amount can be performed.
	 * @throws Exception
	 */
	@Test
	public void negativeAmountExceptionTest() throws Exception {
		CurrencyPair currencyPair = new CurrencyPair("USDEUR");
		double negativeAmount = -100;
		assertThrows(NegativeAmountException.class, () -> {
			currencyPair.exchange(negativeAmount);
		});
	}

	/**
	 * Test a basic exchange
	 */
	@Test
	public void exchangeTest() throws Exception {
		CurrencyPair currencyPair = new CurrencyPair("USDEUR");
		double exchangedAmount = currencyPair.exchange(100.0);
		assertThat(exchangedAmount > 0.1);
	}
}
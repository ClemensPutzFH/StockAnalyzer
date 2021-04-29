package stockanalyzer.ctrl;

import stockanalyzer.ui.UserInterface;
import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

	YahooFinance yahooFinance = new YahooFinance();
		
	 public void process(String ticker) throws IOException {
		System.out.println("Start process");

		//TODO implement Error handling 

		//TODO implement methods for
		//1) Daten laden
		String result;
		List<String> tickerList = new ArrayList<String>();
		tickerList.add(ticker);
		YahooResponse response = yahooFinance.getCurrentData(tickerList);
		QuoteResponse quotes = response.getQuoteResponse();
		quotes.getResult().stream().forEach(quote ->{
			UserInterface.print(quote.getAsk().toString());
		});

		//2) Daten Analyse

	}
	

	public void getMax(String ticker) throws IOException {
		Stock stock = null;
			stock = yahoofinance.YahooFinance.get(ticker);
			double result = stock.getHistory().stream().mapToDouble(q->q.getClose().doubleValue()).max().getAsDouble();
			UserInterface.print("Max: "+result);
	}

	public void getMin(String ticker) throws IOException{
		Stock stock = null;
			stock = yahoofinance.YahooFinance.get(ticker);
			double result;
			result = stock.getHistory().stream().mapToDouble(q->q.getClose().doubleValue()).min().getAsDouble();
			UserInterface.print("Min: "+result);
	}


	public void closeConnection() {
		
	}
}

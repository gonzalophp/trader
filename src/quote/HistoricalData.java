/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quote;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author fiber
 */
public class HistoricalData {
    public static final int ALL=0;
    public static final int DAY=1;
    public static final int WEEK=2;
    
    private HashMap<Integer, TreeMap<Long, quote.Quote>> _quoteDataPeriods;
    private String ticket;

    public HistoricalData(){
        _quoteDataPeriods = new HashMap<Integer, TreeMap<Long, quote.Quote>>();
    }
    
    public void loadPrices(quote.Quote quote, quote.source.PriceReader priceReader){
        if (this.ticket != quote.getTicket()){
            this.ticket = quote.getTicket();
            _quoteDataPeriods.clear();
            _quoteDataPeriods.put(ALL, priceReader.getPrices(quote.getTicket()));
        }
    }
    
    public TreeMap<Long, quote.Quote> getQuotes() throws Exception {
        return getQuotes(ALL);
    }

    public TreeMap<Long, quote.Quote> getQuotes(int period) throws Exception {
        
        if (ticket.isEmpty()) {
            throw new Exception();
        }
        else {
            if (period==ALL){
                return _quoteDataPeriods.get(ALL);
            }
            else {
                if (!_quoteDataPeriods.containsKey(period)){
                    TreeMap<Long, quote.Quote> pricesData = new TreeMap<Long, quote.Quote>();

                    switch(period){
                        case DAY:
                            Set<Long> setAllTimeMillis = _quoteDataPeriods.get(ALL).keySet();
                            Iterator iAllTimeMillis = setAllTimeMillis.iterator();
                            Calendar calendar = Calendar.getInstance();
                            quote.Prices prices;
                            long timeMillis;
                            while(iAllTimeMillis.hasNext()){
                                timeMillis = (Long) iAllTimeMillis.next();
                                calendar.setTimeInMillis(timeMillis);
                                calendar.set(calendar.get(Calendar.YEAR)
                                            ,calendar.get(Calendar.MONTH)
                                            ,calendar.get(Calendar.DAY_OF_MONTH)
                                            ,23
                                            ,59
                                            ,59);
                                prices = new quote.Prices();
                                
                                
                                prices.setOpen(_quoteDataPeriods.get(ALL).get(timeMillis).getPrices().getOpen());
                                prices.setHigh(_quoteDataPeriods.get(ALL).get(timeMillis).getPrices().getHigh());
                                prices.setLow(_quoteDataPeriods.get(ALL).get(timeMillis).getPrices().getLow());
                                prices.setClose(_quoteDataPeriods.get(ALL).get(timeMillis).getPrices().getClose());
                                prices.setVolume(_quoteDataPeriods.get(ALL).get(timeMillis).getPrices().getVolume());
                                prices.setAdjClose(_quoteDataPeriods.get(ALL).get(timeMillis).getPrices().getAdjClose());
                                quote.Quote quote  = new quote.Quote(this.ticket);
                                quote.setPrices(prices);
                                pricesData.put(calendar.getTimeInMillis(), quote);
                            }
                            
                            _quoteDataPeriods.put(DAY, pricesData);
                        break;
                    }

                    _quoteDataPeriods.put(period, pricesData);
                }

                return _quoteDataPeriods.get(period);
            }
        }
    }
}

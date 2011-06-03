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
    
    private HashMap<Integer, TreeMap<Long, quote.Prices>> _pricesDataPeriods;
    private String ticket;

    public HistoricalData(){
        _pricesDataPeriods = new HashMap<Integer, TreeMap<Long, quote.Prices>>();
    }
    
    public void loadPrices(String ticket, quote.source.PriceReader priceReader){
        if (this.ticket != ticket){
            this.ticket = ticket;
            _pricesDataPeriods.clear();
            _pricesDataPeriods.put(ALL, priceReader.getPrices(ticket));
        }
    }
    
    public TreeMap<Long, Prices> getPrices() throws Exception {
        return getPrices(ALL);
    }

    public TreeMap<Long, quote.Prices> getPrices(int period) throws Exception {
        
        if (ticket.isEmpty()) {
            throw new Exception();
        }
        else {
            if (period==ALL){
                return _pricesDataPeriods.get(ALL);
            }
            else {
                if (!_pricesDataPeriods.containsKey(period)){
                    TreeMap<Long, quote.Prices> pricesData = new TreeMap<Long, quote.Prices>();

                    switch(period){
                        case DAY:
                            Set<Long> setAllTimeMillis = _pricesDataPeriods.get(ALL).keySet();
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
                                
                                
                                prices.setOpen(_pricesDataPeriods.get(ALL).get(timeMillis).getOpen());
                                prices.setHigh(_pricesDataPeriods.get(ALL).get(timeMillis).getHigh());
                                prices.setLow(_pricesDataPeriods.get(ALL).get(timeMillis).getLow());
                                prices.setClose(_pricesDataPeriods.get(ALL).get(timeMillis).getClose());
                                prices.setVolume(_pricesDataPeriods.get(ALL).get(timeMillis).getVolume());
                                prices.setAdjClose(_pricesDataPeriods.get(ALL).get(timeMillis).getAdjClose());
                
                
                                pricesData.put(calendar.getTimeInMillis(), prices);
                            }
                            
                            _pricesDataPeriods.put(DAY, pricesData);
                        break;
                    }

                    _pricesDataPeriods.put(period, pricesData);
                }

                return _pricesDataPeriods.get(period);
            }
        }
    }
}

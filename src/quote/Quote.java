/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quote;

import java.util.HashMap;

/**
 *
 * @author fiber
 */
public class Quote {
    private String ticket;
    private Prices prices;
    private Bollinger bollinger;
    private PSAR PSAR;
    private HashMap<Integer,Double> SMA;
    
    public Quote(String ticket){
        this.ticket = ticket;
    }
    
    public String getTicket() {
        return ticket;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }
    
    public Double getSMA(Integer n){
        return SMA.get(n);
    }
    
    public void setSMA(Integer n, Double value){
        SMA.put(n, value);
    }
    
    public Bollinger getBollinger() {
        return bollinger;
    }

    public void setBollinger(Bollinger bollinger) {
        this.bollinger = bollinger;
    }

    public PSAR getPSAR() {
        return PSAR;
    }

    public void setPsar(PSAR PSAR) {
        this.PSAR = PSAR;
    }
}

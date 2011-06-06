/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quote.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.TreeMap;

/**
 *
 * @author fiber
 */
public class Yahoo implements PriceReader {
    
    public TreeMap<Long, quote.Prices> getPrices(String ticket){
        TreeMap<Long, quote.Prices> historicalData = new TreeMap<Long, quote.Prices>();
        quote.Prices prices;
        
        Calendar calendar = Calendar.getInstance();
        String nextLine;
        URL url = null;
        URLConnection urlConn = null;
        InputStreamReader  inStream = null;
        BufferedReader buff = null;
        try{
            url  = new URL("http://ichart.finance.yahoo.com/table.csv?s="+ticket+"&a=0&b=1&c=2003&d="+calendar.get(Calendar.MONTH)+"&e="+calendar.get(Calendar.DAY_OF_MONTH)+"&f="+calendar.get(Calendar.YEAR)+"&g=d&ignore=.csv");                    
            System.out.println(url);
            urlConn = url.openConnection();
            inStream = new InputStreamReader(urlConn.getInputStream());
            buff= new BufferedReader(inStream);
            String[] nextLineValues;
            while ((nextLine = buff.readLine()) != null){
                nextLineValues = nextLine.split(",");
                String[] dateValues = nextLineValues[0].split("-");
                if (dateValues.length != 3) continue;
                calendar.set(Integer.parseInt(dateValues[0])
                            ,Integer.parseInt(dateValues[1])-1
                            ,Integer.parseInt(dateValues[2])
                            ,23
                            ,59
                            ,59);
                prices = new quote.Prices();
                prices.setOpen(Double.parseDouble(nextLineValues[1]));
                prices.setHigh(Double.parseDouble(nextLineValues[2]));
                prices.setLow(Double.parseDouble(nextLineValues[3]));
                prices.setClose(Double.parseDouble(nextLineValues[4]));
                prices.setVolume(Long.parseLong(nextLineValues[5]));
                prices.setAdjClose(Double.parseDouble(nextLineValues[6]));
                
                historicalData.put(calendar.getTimeInMillis(), prices);
            }
        } catch(MalformedURLException e){
            System.out.println("Please check the URL:" + e.toString() );
        } catch(IOException  e1){
            System.out.println("Can't read  from the Internet: "+ e1.toString() ); 
        }
        
        return historicalData;
    }
}

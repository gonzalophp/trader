/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quote.source;

import java.util.TreeMap;

/**
 *
 * @author fiber
 */
public interface PriceReader {
    public TreeMap<Long, quote.Prices> getPrices(String ticket);
}

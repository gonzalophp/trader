/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trader.graph;

/**
 *
 * @author fiber
 */
public class CandleStickPoint {
    private int x, minY, maxY, openY, closeY;
    private long longTimeMillis;
    public CandleStickPoint(long longTimeMillis,int x, int minY, int maxY, int openY, int closeY) {
        this.longTimeMillis = longTimeMillis;
        this.x = x;
        this.minY = minY;
        this.maxY = maxY;
        this.openY = openY;
        this.closeY = closeY;
    }
    
    public long getLongTimeMillis() {
        return longTimeMillis;
    }
    
    public int getX() {
        return x;
    }
    
    public int getOpenY(){
        return openY;
    }
    
    public int getCloseY() {
        return closeY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinY() {
        return minY;
    }

    
}

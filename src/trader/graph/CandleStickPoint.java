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
    private int x, minY, maxY, closeY;

    public CandleStickPoint(int x, int minY, int maxY,int closeY) {
        this.x = x;
        this.minY = minY;
        this.maxY = maxY;
        this.closeY = closeY;
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

    public int getX() {
        return x;
    }
}

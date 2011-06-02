/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trader.graph;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.TreeMap;
import quote.Prices;

/**
 *
 * @author fiber
 */
public class PricesDraw {
    private TreeMap<Long, Prices> graphPrices;
    
    public PricesDraw(TreeMap<Long, Prices> graphPrices){
        this.graphPrices = graphPrices;
    }
    
    public void drawLine(Graphics g
            , int marginLeft
            , int marginTop
            , int graphSizeX
            , int graphSizeY
            , Double scale
            , long firstDrawPoint){
        
        long i=0;
        
        if (firstDrawPoint > 0){
            Double y=0.0
                    ,maxY=0.0
                    ,minY=0.0;
            
            for(long  longTimeMillis: graphPrices.keySet()){
                if (i++ > firstDrawPoint) {
                    y = graphPrices.get(longTimeMillis).getAdjClose();
                    if (y>maxY) maxY=y;
                    else if(y<minY) minY = y;
                }
                else if (i == firstDrawPoint) {
                    y = graphPrices.get(longTimeMillis).getAdjClose();
                    minY = y;
                    maxY = y;
                }
                if ((i-firstDrawPoint) >= (graphSizeX) ) break;
            }
            
            Double deltaY=maxY-minY;
            ArrayList<Point> alPoints = new ArrayList<Point>();
            i=0;
            int x=0;
            for(long  longTimeMillis: graphPrices.keySet()){
                if (i++ >= firstDrawPoint) {
                    x = (int)((i-firstDrawPoint)*(scale))+marginLeft;
                    y = (graphSizeY*(maxY-graphPrices.get(longTimeMillis).getAdjClose())/deltaY)+marginTop;
                    alPoints.add(new Point(x, y.intValue()));
                }
                if ((i-firstDrawPoint) >= graphSizeX ) break;
            }
            
            Point previousPoint=null;
            for(Point point:alPoints){
                if(previousPoint!=null){
                    g.drawLine((int)previousPoint.getX(), (int)previousPoint.getY(), (int)point.getX(), (int)point.getY());
                }
                previousPoint = point;
            }
        }
    }
    
}

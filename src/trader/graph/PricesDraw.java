/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trader.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;
import quote.Prices;

/**
 *
 * @author fiber
 */
public class PricesDraw {
    private TreeMap<Long, Prices> graphPrices;
    private final Double graphYMarginRate = 0.2; // 10% graph margin, both top and bottom -> 20%
    public static final int GRAPH_STYLE_LINE   = 1
                           ,GRAPH_STYLE_CANDLESTICK    = 2;
    private int graphStyle;
    
    public PricesDraw(TreeMap<Long, Prices> graphPrices, int graphStyle){
        this.graphPrices = graphPrices;
        this.graphStyle = graphStyle;
    }
    
     public TreeMap<Long, Prices> getGraphPrices() {
        return graphPrices;
    }
    
    public void draw(Graphics g
                , int marginLeft
                , int marginTop
                , int graphSizeX
                , int graphSizeY
                , Double scale
                , long firstDrawPoint){
        switch(graphStyle){
            case GRAPH_STYLE_LINE:
                _drawLine(g
                        , marginLeft
                        , marginTop
                        , graphSizeX
                        , graphSizeY
                        , scale
                        , firstDrawPoint);
            break;
            case GRAPH_STYLE_CANDLESTICK:
                _drawCandleStick(g
                            , marginLeft
                            , marginTop
                            , graphSizeX
                            , graphSizeY
                            , scale
                            , firstDrawPoint);
            break;
        }
    }
    
    private void _drawLine(Graphics g
            , int marginLeft
            , int marginTop
            , int graphSizeX
            , int graphSizeY
            , Double scale
            , long firstDrawPoint){
        
        if (firstDrawPoint > 0){
            Double y=0.0;
            long i=0;
            int x=0;
            
            Double[] minYAndMaxY = _getAdjCloseMinAndMax(firstDrawPoint, graphSizeX);
            Double deltaY=(minYAndMaxY[1]-minYAndMaxY[0])*(1+graphYMarginRate);
            
            ArrayList<Point> alPoints = new ArrayList<Point>();
            for(long  longTimeMillis: graphPrices.keySet()){
                if (i++ >= firstDrawPoint) {
                    x = (int)((i-firstDrawPoint)*(scale))+marginLeft;
                    y = (graphSizeY*(minYAndMaxY[1]-graphPrices.get(longTimeMillis).getAdjClose())/deltaY)+marginTop+(graphSizeY*(graphYMarginRate)/2);
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
    
    private Double[] _getAdjCloseMinAndMax(long firstDrawPoint, long graphSizeX){
        Double y=0.0
            ,maxY=0.0
            ,minY=0.0;
        int i=0;
        for(long  longTimeMillis: graphPrices.keySet()){
                if (i++ > firstDrawPoint) {
                    minY = Math.min(minY, graphPrices.get(longTimeMillis).getAdjClose());
                    maxY = Math.max(maxY, graphPrices.get(longTimeMillis).getAdjClose());
                }
                else if (i == firstDrawPoint) {
                    minY = graphPrices.get(longTimeMillis).getAdjClose();
                    maxY = graphPrices.get(longTimeMillis).getAdjClose();
                }
                if ((i-firstDrawPoint) >= (graphSizeX) ) break;
            }
        
        Double[] MinAndMaxGraphY = {minY,maxY};
        
        return MinAndMaxGraphY;
    }
    
    
    
    private void _drawCandleStick(Graphics g
                                , int marginLeft
                                , int marginTop
                                , int graphSizeX
                                , int graphSizeY
                                , Double scale
                                , long firstDrawPoint){
        
        if (firstDrawPoint > 0){
            Double yLow=0.0, yHigh=0.0, yAdjClose=0.0;
            long i=0;
            int x=0;
            
            Double[] minYAndMaxY = _getMinAndMax(firstDrawPoint, graphSizeX);
            Double deltaY=(minYAndMaxY[1]-minYAndMaxY[0])*(1+graphYMarginRate);
            
            ArrayList<CandleStickPoint> alCandleStickPoints = new ArrayList<CandleStickPoint>();
            for(long  longTimeMillis: graphPrices.keySet()){
                if (i++ >= firstDrawPoint) {
                    x = (int)((i-firstDrawPoint)*(scale))+marginLeft;
                    yHigh = (graphSizeY*(minYAndMaxY[1]-graphPrices.get(longTimeMillis).getHigh())/deltaY)+marginTop+(graphSizeY*(graphYMarginRate)/2);
                    yLow = (graphSizeY*(minYAndMaxY[1]-graphPrices.get(longTimeMillis).getLow())/deltaY)+marginTop+(graphSizeY*(graphYMarginRate)/2);
                    yAdjClose = (graphSizeY*(minYAndMaxY[1]-graphPrices.get(longTimeMillis).getAdjClose())/deltaY)+marginTop+(graphSizeY*(graphYMarginRate)/2);
                    alCandleStickPoints.add(new CandleStickPoint(longTimeMillis
                                                    ,x
                                                    , yLow.intValue()
                                                    , yHigh.intValue()
                                                    , yAdjClose.intValue()));
                }
                if ((i-firstDrawPoint) >= graphSizeX ) break;
            }
            
            CandleStickPoint previousCandleStickPoint=null;
            for(CandleStickPoint candleStickpoint:alCandleStickPoints){
                if(previousCandleStickPoint!=null){
                    g.setColor(Color.black);
                    g.drawLine(candleStickpoint.getX(), candleStickpoint.getMinY(), candleStickpoint.getX(), candleStickpoint.getMaxY());
                    
                    
                    g.setColor(((candleStickpoint.getCloseY()-previousCandleStickPoint.getCloseY()) < 0) ? Color.green : Color.red );
                    
                    g.fillRect(candleStickpoint.getX()-1
                            , Math.min(previousCandleStickPoint.getCloseY(),candleStickpoint.getCloseY())
                            , 3
                            , Math.abs(candleStickpoint.getCloseY()-previousCandleStickPoint.getCloseY()));
                }
                previousCandleStickPoint = candleStickpoint;
            }
        }
    }
    
    
    private Double[] _getMinAndMax(long firstDrawPoint, long graphSizeX){
        Double maxY=0.0
              ,minY=0.0;
        int i=0;
        for(long  longTimeMillis: graphPrices.keySet()){
                if (i++ > firstDrawPoint) {
                    minY = Math.min(minY, graphPrices.get(longTimeMillis).getLow());
                    maxY = Math.max(maxY, graphPrices.get(longTimeMillis).getHigh());
                }
                else if (i == firstDrawPoint) {
                    minY = graphPrices.get(longTimeMillis).getLow();
                    maxY = graphPrices.get(longTimeMillis).getHigh();
                }
                if ((i-firstDrawPoint) >= (graphSizeX) ) break;
            }
        
        Double[] MinAndMaxGraphY = {minY,maxY};
        
        return MinAndMaxGraphY;
    }
    
}

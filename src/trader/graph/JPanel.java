/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanel.java
 *
 * Created on 13-May-2011, 19:11:34
 */
package trader.graph;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import quote.Prices;

/**
 *
 * @author fiber
 */
public class JPanel extends javax.swing.JPanel {
//    private quote.HistoricalData historicalData;
    private TreeMap<Long, Prices> graphPrices;
    private boolean dataSet;
    
    
    /** Creates new form JPanel */
    public JPanel() {
        dataSet = false;
        initComponents();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (dataSet) _paintPrices(g);
    }
    
    public void setGraphPrices(TreeMap<Long, Prices> graphPrices){
        this.graphPrices = graphPrices;
        dataSet = true;
    }
    
    private void _paintPrices(Graphics g){
        int marginTop=10,marginBotton=20,marginLeft=10,marginRight=30;
        long panelSizeX = getWidth()-marginLeft-marginRight; //size for scale legend
        long panelSizeY = getHeight()-marginTop-marginBotton; //size for scale legend

        g.drawRect(marginLeft, marginTop, (int)panelSizeX, (int)panelSizeY);
        
        long i=0
            ,hiddenPixels = graphPrices.size()-panelSizeX;
        
        if (hiddenPixels > 0){
            Double y=0.0
                    ,maxY=0.0
                    ,minY=0.0;
            
            for(long  longTimeMillis: graphPrices.keySet()){
                if (i++ > hiddenPixels) {
                    y = graphPrices.get(longTimeMillis).getAdjClose();
                    if (y>maxY) maxY=y;
                    else if(y<minY) minY = y;
                }
                else if (i == hiddenPixels) {
                    y = graphPrices.get(longTimeMillis).getAdjClose();
                    minY = y;
                    maxY = y;
                }
            }
            
            Double deltaY=maxY-minY;
            ArrayList<Point> alPoints = new ArrayList<Point>();
            i=0;
            int x;
            for(long  longTimeMillis: graphPrices.keySet()){
                if (i++ >= hiddenPixels) {
                    x = (int)(i-hiddenPixels)+marginLeft;
                    y = (panelSizeY*(maxY-graphPrices.get(longTimeMillis).getAdjClose())/deltaY)+marginTop;
                    System.out.println("x:"+x+" i:"+i+" hiddenPixels:"+hiddenPixels);
                    alPoints.add(new Point(x, y.intValue()));
                }
            }
            
            Point previousPoint=null;
            for(Point point:alPoints){
                if(previousPoint!=null){
                    g.drawLine((int)previousPoint.getX(), (int)previousPoint.getY(), (int)point.getX(), (int)point.getY());
                }
                previousPoint = point;
            }
        }
        
        
        
//        graphPrices.keySet().
//        
//        
//        
//        
//        if (graphPricesSize > panelSizeX){ // More data than pixels
//            long firstLongTimeMillis=graphPrices.firstKey()
//                , i=0
//                , hiddenPixels = graphPricesSize-panelSizeX;
//            
//            for(long  longTimeMillis: graphPrices.keySet()){
//                if (i++ == hiddenPixels) {
//                    firstLongTimeMillis = longTimeMillis;
//                    break;
//                }
//            }
//            System.out.println(firstLongTimeMillis);
//            
////            Set<Long> setLongTimeMillis = graphPrices.keySet();
////            Iterator iLongTimeMillis = setLongTimeMillis.iterator();
////            long longTimeMillis;
////            long hiddenPixels = graphPrices.size()-panelSizeX;
////            for(long i=0;i<hiddenPixels;i++) iLongTimeMillis.next();
////                
////            while(iLongTimeMillis.hasNext()){
////                longTimeMillis = (long)iLongTimeMillis.next();
////            }
////            
////            graphPrices.
//            System.out.println("eo");
//        }
//    }
//    
//    private void _paintPrices2(Graphics g){
//        
//        long panelSizeY = getHeight();
//        long panelSizeX = getWidth();
//        
//        long minDataX    = graphPrices.firstKey();
//        long maxDataX    = graphPrices.lastKey();
//        
//        Double minDataY=0.0;
//        Double maxDataY=0.0;
//        
//        Set<Long> setDate = graphPrices.keySet();
//        Iterator<Long> longIterator = setDate.iterator();
//        
//        if (longIterator.hasNext()){
//            Prices prices = graphPrices.get(longIterator.next());
//            minDataY = maxDataY = prices.getAdjClose();
//        }
//                
//        while(longIterator.hasNext()){
//            Prices prices = graphPrices.get(longIterator.next());
//            if (prices.getAdjClose() < minDataY){
//                minDataY = prices.getAdjClose();
//            }
//            else if(prices.getAdjClose() > maxDataY){
//                maxDataY = prices.getAdjClose();
//            }
//        }
//        
//        long dataSizeX = maxDataX-minDataX;
//        Double dataSizeY = maxDataY-minDataY;
//            
//        longIterator = setDate.iterator();
//        
//        TreeMap<Integer,ArrayList<Integer>> alGraphValues = new TreeMap<Integer,ArrayList<Integer>>();
//        while(longIterator.hasNext()){
//            Long dataX;
//            Double dataY;
//            dataX = longIterator.next();
//            dataY = graphPrices.get(dataX).getAdjClose();
//            
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(dataX);
//            System.out.println(dataX+" "+cal.get(Calendar.DAY_OF_WEEK));
//            
//            int graphX,graphY;
//            graphX = (int)(panelSizeX-(panelSizeX*((double)(maxDataX-dataX)/dataSizeX)));
//            graphY = (int)(panelSizeY-(panelSizeY*(((double)(maxDataY-dataY)/dataSizeY))));
//            
//            if (alGraphValues.containsKey(graphX)){
//                alGraphValues.get(graphX).add(graphY);
//            }
//            else {
//                ArrayList<Integer> alInteger = new ArrayList<Integer>();
//                alInteger.add(graphY);
//                alGraphValues.put(graphX, alInteger);
//            }
//        }
//        
//        Set<Integer> setGraph = alGraphValues.keySet();
//        Iterator<Integer> iIterator = setGraph.iterator();
//        int x=0,y=0;
//        TreeMap<Integer,Integer> alGraphPoints = new TreeMap<Integer,Integer>();
//        while(iIterator.hasNext()){
//            x = iIterator.next();
//            if (alGraphValues.get(x).size() > 1){
//                ArrayList<Integer> alInteger = new ArrayList<Integer>();
//                alInteger = alGraphValues.get(x);
//                
//                Iterator iIterator2 =  alInteger.iterator();
//                y=0;
//                while (iIterator2.hasNext()) y += (Integer)iIterator2.next();
//                y = y/alInteger.size();
//            }
//            else {
//                y = alGraphValues.get(x).get(0);
//            }
//            alGraphPoints.put(x, y);
//        }
//        
//        setGraph = alGraphPoints.keySet();
//        iIterator = setGraph.iterator();
//        int i = 0, previousX = 0, previousY = 0;
//        while(iIterator.hasNext()){
//            x=iIterator.next();
//            if (i++>0){
//                g.drawLine(previousX,previousY,x,alGraphPoints.get(x));
//            }
//            previousX = x;
//            previousY = alGraphPoints.get(x);
//        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setName("Form"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 396, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

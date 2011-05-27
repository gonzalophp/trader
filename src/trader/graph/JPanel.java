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
    private final int marginTop=10,marginBotton=20,marginLeft=10,marginRight=30;
    
    private TreeMap<Long, Prices> graphPrices;
    private boolean dataSet;
    private int firstDrawPoint;
    private Double scale=1.0;
    private long graphSizeX; //size for scale legend
    private long graphSizeY; //size for scale legend
    
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
    
    public int setGraphPrices(TreeMap<Long, Prices> graphPrices){
        firstDrawPoint = (int) ((scale)*(graphPrices.size()-getWidth()+marginLeft+marginRight));
        this.graphPrices = graphPrices;
        dataSet = true;
        System.out.println("hiddenPoints:"+firstDrawPoint+" graphPrices.size():"+graphPrices.size()+" getWidth():"+getWidth()+" marginLeft:"+marginLeft+" marginRight:"+marginRight);
        return firstDrawPoint;
    }
    
    public int zoomPlus(){
        System.out.println("plus");
        scale *= 2;
        firstDrawPoint += (graphSizeX/2);
//        graphSizeX *= 2;
        repaint();
        return firstDrawPoint;
    }
    
    public int zoomMinus(){
        System.out.println("minus");
        scale /= 2;
        firstDrawPoint -= graphSizeX;
//        graphSizeX /= 2;
        repaint();
        return firstDrawPoint;
    }
    
    private void _paintPrices(Graphics g){
        graphSizeX = (int)((getWidth()-marginLeft-marginRight)/scale); //size for scale legend
        
        long totalPoints = graphPrices.size();
        System.out.println("totalPoints:"+totalPoints+" hiddenPoints:"+firstDrawPoint+" graphSizeX:"+graphSizeX);
        graphSizeY = getHeight()-marginTop-marginBotton; //size for scale legend
//        System.out.println("graphSizeX4:"+graphSizeX+" graphSizeY:"+graphSizeY+" getHeight()"+getHeight());
        g.drawRect(marginLeft, marginTop, (int)(graphSizeX*scale), (int)graphSizeY);
        
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
//                System.out.println("i:"+i+" x:"+x+" y:"+y+" scale:"+scale+" hiddenPoints:"+hiddenPoints+" graphSizeX:"+graphSizeX);
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

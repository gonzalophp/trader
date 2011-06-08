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
import java.util.TreeMap;
import quote.Prices;

/**
 *
 * @author fiber
 */
public class JPanel extends javax.swing.JPanel {
    private final int MARGIN_TOP    = 10
                    ,MARGIN_BOTTOM  = 20
                    ,MARGIN_LEFT    = 10
                    ,MARGIN_RIGHT   = 30;
    
    private int lastMouseDragX=-1;
    private trader.graph.JInternalFrame jInternalFrame1;
    private trader.graph.ScaleDraw scaleDraw;
    private trader.graph.PricesDraw pricesDraw;
    private boolean dataSet;
    private int firstDrawPoint;
    private Double scale;
    
    /** Creates new form JPanel */
    public JPanel() {
        dataSet = false;
        initComponents();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (dataSet) {
            pricesDraw.draw(g, getMarginLeft(), MARGIN_TOP, getGraphSizeX(), getGraphSixeY(), scale, firstDrawPoint);
            
            if (!(scaleDraw instanceof ScaleDraw)){
                scaleDraw = new ScaleDraw(MARGIN_LEFT, MARGIN_TOP, (int)(getGraphSizeX()*scale), getGraphSixeY());
            }
            scaleDraw.drawScale(g);
        }
    }
    
    public void setGraphPrices(TreeMap<Long, Prices> graphPrices){
        jInternalFrame1 = (trader.graph.JInternalFrame)getParentObject(this,"trader.graph.JInternalFrame");
        dataSet = true;
        pricesDraw = new PricesDraw(graphPrices);
    }

    public PricesDraw getPricesDraw() {
        return pricesDraw;
    }
    
    public void setGraphStyle(int graphStyle){
        pricesDraw.setGraphStyle(graphStyle);
        switch (graphStyle) {
            case PricesDraw.GRAPH_STYLE_LINE:
                scale = 1.0;
                firstDrawPoint = pricesDraw.getGraphPrices().size() - getGraphSizeX();
            break;
            case PricesDraw.GRAPH_STYLE_CANDLESTICK:
                scale = 3.0;
                firstDrawPoint = pricesDraw.getGraphPrices().size() - getGraphSizeX();
            break;
        }
    }
    
    public void setFirstDrawPoint(int firstDrawPoint){
        this.firstDrawPoint = firstDrawPoint;
        repaint();
    }
    
    public int zoomPlus(){
        int graphSizeX = getGraphSizeX();
        Double ratioDataPixels = (new Double(graphSizeX) / (getWidth()-MARGIN_LEFT-MARGIN_RIGHT));
        if (ratioDataPixels > 0.2){
            scale *= 2;
            firstDrawPoint += (graphSizeX/2);
            repaint();
        }
        
        return firstDrawPoint;
    }

    public int zoomMinus(){
        int graphSizeX = getGraphSizeX();
        if (firstDrawPoint < graphSizeX){
            scale = new Double(getWidth()-MARGIN_LEFT-MARGIN_RIGHT)/(pricesDraw.getGraphPrices().size());
            firstDrawPoint = 1;
        }
        else {
            scale /= 2;
            firstDrawPoint -= graphSizeX;
        }
        
        repaint();
        
        return firstDrawPoint;
    }
    
    public int getGraphSizeX(){
        Double graphSizeXDouble = ((getWidth()-MARGIN_LEFT-MARGIN_RIGHT)/scale); //size for scale legend
        return graphSizeXDouble.intValue();
    }
    
    public int getGraphSixeY(){
        return getHeight()-MARGIN_TOP-MARGIN_BOTTOM;
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
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

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

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        lastMouseDragX = -1;
    }//GEN-LAST:event_formMouseReleased

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        lastMouseDragX = evt.getX();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
            int newFirstDrawPoint = firstDrawPoint+lastMouseDragX-evt.getX();
            
            if ((newFirstDrawPoint+getGraphSizeX())>pricesDraw.getGraphPrices().size()){
                newFirstDrawPoint = pricesDraw.getGraphPrices().size()-getGraphSizeX();
            }
            if ((firstDrawPoint != newFirstDrawPoint) &&(newFirstDrawPoint>1)){
                lastMouseDragX = evt.getX();
                jInternalFrame1.getScrollBar().setValue(newFirstDrawPoint);
                setFirstDrawPoint(newFirstDrawPoint);
            }
    }//GEN-LAST:event_formMouseDragged

    private java.awt.Component getParentObject(java.awt.Component component, String className){
        try {
            while(((component=component.getParent()) != null) && (!Class.forName(className).isInstance(component)));
        } catch (ClassNotFoundException ex){}
        
        return component;
        
    }
    
    public int getMarginLeft() {
        return MARGIN_LEFT;
    }

    public int getMarginRight() {
        return MARGIN_RIGHT;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

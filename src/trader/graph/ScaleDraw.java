/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trader.graph;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author fiber
 */
public class ScaleDraw {
    private int x1,y1,x2,y2;
    
    public ScaleDraw(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public void drawScale(Graphics g){
        g.setColor(Color.gray);
        g.drawRect(x1, y1, x2,y2);
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import gui.DrawPanel;


/**
 * Class represents the system of two ODE
 * @author 122
 */
public abstract class DiffEq2D extends DiffEq {
    /**
     * Canvas which holds phase portrait of the system
     */
    protected BufferedImage canvas = new BufferedImage(DrawPanel.WIDTH_P, DrawPanel.HEIGHT_P, BufferedImage.TYPE_INT_ARGB);
    protected Transformer tr = new Transformer();

    public Transformer getTr() {
        return tr;
    }
    
    protected void fillCanvas(Color col) {
        int c = col.getRGB();
        
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, c);
            }
        }
    }
    
    @Override
    public BufferedImage getImage() {
        int black = Color.BLACK.getRGB();
        
        fillCanvas(Color.white);
        
        Point2D.Double ScreenP = new Point2D.Double();
        Point2D.Double WorldP = new Point2D.Double();
        
        for(HashSet<Double[]> traec : traces) {
            for(Double[] point : traec) {
                WorldP.setLocation(point[0], point[1]);
                tr.toScreen(ScreenP, WorldP);
                try {
                    if(tr.isInner(WorldP))
                       canvas.setRGB((int)ScreenP.x, (int)ScreenP.y, black);
                } catch(Exception e) {
                    System.err.println(ScreenP + " ");
                    System.err.println(e);
                }
            }
        }
        
        return canvas;
    }
    
    public void clearImage() {
        fillCanvas(Color.white);
        traces.clear();
    }
}

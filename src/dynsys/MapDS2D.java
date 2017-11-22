/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import gui.DrawPanel;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;

/**
 * 
 * @author 122
 */
public class MapDS2D extends MapDS {
    /**
     * Canvas which holds phase portrait of the system
     */
    protected BufferedImage canvas = new BufferedImage(DrawPanel.WIDTH_P, DrawPanel.HEIGHT_P, BufferedImage.TYPE_INT_ARGB);
    Transformer tr = new Transformer();
    
     public Transformer getTr() {
        return tr;
    }
    
     /**
      * It fills the canvas with color col
      * @param col 
      */
    protected void fillCanvas(Color col) {
        int c = col.getRGB();
        
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, c);
            }
        }
    }
    
    /**
     * It sets the cascades' points which are 
     * inside of the screen on the canvas.
     * @return canvas with established points
     */
    @Override
    public BufferedImage getImage() {
        int black = Color.BLACK.getRGB();
        
        fillCanvas(Color.white);
        
        Point2D.Double ScreenP = new Point2D.Double();
        Point2D.Double WorldP = new Point2D.Double();
        
        for(HashSet<Double[]> cascade : cascades) {
            for(Double[] point : cascade) {
                WorldP.setLocation(point[0], point[1]);
                tr.toScreen(ScreenP, WorldP);
                try {
                    if(tr.isInner(WorldP))
                      canvas.setRGB((int)ScreenP.x, (int)ScreenP.y, black);
                } catch(Exception e) {
                    System.err.println(ScreenP + " and " + WorldP);
                    System.err.println(e);
                }
            }
        }
        
        return canvas;
    }
    
    /**
     * Clear canvas and
     * reset the cascades of points
     */
    public void clearImage() {
        fillCanvas(Color.white);
        cascades.clear();
    }
}

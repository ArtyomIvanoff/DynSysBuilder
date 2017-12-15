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
    public MapDS2D() {
        tr = new Transformer();
        mim = new MapImageManager(tr);
    }
    
    private Transformer tr;
    
     public Transformer getTr() {
        return tr;
    }
    
    /**
     * It sets the cascades' points which are 
     * inside of the screen on the canvas.
     * @return canvas with established points
     */
    @Override
    public BufferedImage getImage() {
        return mim.updateImage();
    }
    
    private class MapImageManager extends ImageManager {
        
        public MapImageManager(Transformer tr) {
            super(tr);
            c = Color.BLACK;
        }

        @Override
        BufferedImage updateImage() {
          int colorRGB = c.getRGB();
        
          fillCanvas(Color.white);
        
          Point2D.Double ScreenP = new Point2D.Double();
          Point2D.Double WorldP = new Point2D.Double();
        
          for(HashSet<Double[]> cascade : cascades) {
            for(Double[] point : cascade) {
                WorldP.setLocation(point[0], point[1]);
                tr.toScreen(ScreenP, WorldP);
                try {
                    if(tr.isInner(WorldP))
                      canvas.setRGB((int)ScreenP.x, (int)ScreenP.y, colorRGB);
                } catch(Exception e) {
                    System.err.println(ScreenP + " and " + WorldP);
                    System.err.println(e);
                }
            }
          }
        
          return canvas;
        }
        
        private Color c;

        public void setC(Color c) {
            this.c = c;
        }
    }
    
    private MapImageManager mim;
}

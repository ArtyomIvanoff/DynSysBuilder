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
    public DiffEq2D() {
        tr = new Transformer();
        deim = new DiffEqImageManager(tr);
    }
   
    private Transformer tr;

    public Transformer getTr() {
        return tr;
    }
    
    @Override
    public BufferedImage getImage() {
        return deim.updateImage();
    }
    
    private class DiffEqImageManager extends ImageManager {
        public DiffEqImageManager(Transformer tr) {
            super(tr);
            c = Color.black;
        }
        
        @Override
        BufferedImage updateImage() {
            int cRGB = c.getRGB();

            fillCanvas(Color.white);

            Point2D.Double ScreenP = new Point2D.Double();
            Point2D.Double WorldP = new Point2D.Double();

            for(HashSet<Double[]> traec : traces) {
                for(Double[] point : traec) {
                    WorldP.setLocation(point[0], point[1]);
                    tr.toScreen(ScreenP, WorldP);
                    try {
                        if(tr.isInner(WorldP))
                           canvas.setRGB((int)ScreenP.x, (int)ScreenP.y, cRGB);
                    } catch(Exception e) {
                        System.err.println(ScreenP + " ");
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
    
    private DiffEqImageManager deim;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import gui.DrawPanel;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author 122
 */
public abstract class ImageManager {
    public ImageManager(Transformer tr) {
        this.tr = tr;
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
    
    public void clearImage() {
        fillCanvas(Color.white);
    }
    
    abstract BufferedImage updateImage();
    
    private final Transformer tr;
    /**
     * Canvas which holds phase portrait of the system
     */
    protected BufferedImage canvas = new BufferedImage(DrawPanel.WIDTH_P, DrawPanel.HEIGHT_P, BufferedImage.TYPE_INT_ARGB);
}

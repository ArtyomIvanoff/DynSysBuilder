/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import java.awt.image.BufferedImage;

/**
 *
 * @author 122
 */
public class FractalRandIFS extends Fractal {
    
    public FractalRandIFS() {
        // the area is 1x1 square with the left-bottom corner (0;0)
        tr = new Transformer(0.5, 0.5, 0.5);
        rifsim = new RandIFSImageManager(tr);
    }
    
    private Transformer tr;
    
    @Override
    public BufferedImage getImage() {
        return rifsim.updateImage();
    }
    
    private class RandIFSImageManager extends ImageManager {

        public RandIFSImageManager(Transformer tr) {
            super(tr);
        }

        @Override
        BufferedImage updateImage() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private RandIFSImageManager rifsim;
}

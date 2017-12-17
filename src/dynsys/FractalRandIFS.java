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
import java.util.Random;

/**
 *
 * @author 122
 */
public class FractalRandIFS extends Fractal {
    
    public FractalRandIFS(IFS ifs1) {
        ifs = ifs1;
        
        tr = ifs1.getTr();
        rifsim = new RandIFSImageManager(tr);
        set = new HashSet<>(super.getIterations());
        setIterations(10000);
        
        // set start point,(0;0), for example
        pCur = new Point2D.Double(0.0, 0.0);
        
        r = new Random(42);
        int n = ifs1.getNumAT();
        // just "warm up" the PRNG
        for(int i = 0; i < 1000; i++)
            r.nextInt(n);
    }
    
    private Transformer tr;
    
    public void applyIFS() {
        final int num = ifs.getNumAT();
        
        Point2D.Double pNext;
        Point2D.Double pScreen;
        
        // number of the current transform
        int k;
        for(int i = 0; i < iterations; i++) {
            k = r.nextInt(num);
            pNext = new Point2D.Double(); 
            pScreen = new Point2D.Double();
            
            pNext.x = ifs.applyTransformX(k, pCur.x, pCur.y);
            if(pNext.x >= tr.getxMin() && pNext.x <= tr.getxMax()) {
                pNext.y = ifs.applyTransformY(k, pCur.x, pCur.y);
                if(pNext.y >= tr.getyMin() && pNext.y < tr.getyMax()) {
                    tr.toScreen(pScreen, pNext);
                    set.add(pScreen);
                }
            }
            pCur = pNext;
        }
    }
    
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
            int c = Color.black.getRGB();
            
            fillCanvas(Color.white);
            
            for(Point2D.Double p : set) {
                try{
                    canvas.setRGB((int)p.x, (int)p.y, c);
                } catch(Exception e) {
                    System.err.println(e + " " + p);
                }
            }
            
            return canvas;
        }
    }
    
    private RandIFSImageManager rifsim;
    private HashSet<Point2D.Double> set;
    private IFS ifs;
    private Point2D.Double pCur;
    private Random r;
}

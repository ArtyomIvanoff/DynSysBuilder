/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import java.awt.image.BufferedImage;
import static java.lang.Math.*;

/**
 * The root of hierarchy of dynamic systems including
 * ODE, PDE, fractals...
 * @author 122
 */
public abstract class DynSys {
    public abstract BufferedImage getImage();
    public static final double doubPI = PI*2;
    /**
     * Represents interface of the right part of system
     * which can be implemented in various ways
     */
    protected interface RightPart {
        double f(double t, double... x);
    }
    
    protected RightPart[] rps;
    
    /**
     * Transform a point to the point of [-PI, PI]
     * @param x 
     * @return a point of [-PI, PI]
     */
    protected double mod2PI(double x){
        double res = x%(doubPI);
        if(Math.abs(res)> Math.PI)
          res -= Math.signum(res)*doubPI;
        return res;
    }
    
    /**
     * Enumeration for using various topologic areas
     */
    public enum Area { R2, CYLINDER, TORUS};
}

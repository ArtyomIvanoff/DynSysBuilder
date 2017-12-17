/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Represents abstract system of maps
 * @author 122
 */
public abstract class MapDS extends DynSys {
     /**
     * Hold all current cascades
     */
    protected HashSet<HashSet<Double[]>> cascades = new HashSet<>();
    
    /**
     * Number of iterations
     */
    protected int iterCount = 300;
    
    /**
     * Starting point of the trace which can be continued
     */
    protected double[] x0;

    public int getIterCount() {
        return iterCount;
    }

    public void setIterCount(int iterCount) {
        if(iterCount > 0)
            this.iterCount = iterCount;
    }

    public double[] getX0() {
        return x0;
    }

    public void setX0(double... x0) {
        this.x0 = x0;
    }
    
    /**
     * The method creates a sequence of
     * points which are added to a new cascade
     * Note that there is 
     * a world coordinate system
     * @return an array op points if it's needed
     */
    public double[][] getSequence() {
        if(x0 == null)
           return null;
        
        final int n = x0.length;
        double[][] x = new double[iterCount][n];
        x[0] = Arrays.copyOf(x0, n);
        
        HashSet<Double[]> cascade = new HashSet<>(iterCount);
        Double[] point = new Double[n];
        for(int i = 0; i < n; i++)
            point[i] = x[0][i];
        
        cascade.add(point);
       
        for(int i = 0; i < iterCount-1; i++) {
            point = new Double[n];
            for(int j = 0; j < n; j++) {
                x[i+1][j] = rps[j].f(0, x[i]);
                point[j] = x[i+1][j];
            }
            
            cascade.add(point);
        }
        
        //add last inner point for possibility of continuening the trace
        setX0(x[iterCount-1]);
        
        cascades.add(cascade);
        
        return x;
    }
}

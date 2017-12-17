/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Class represents systems of
 * ordinary differential equations
 * which are solved by Runge-Kutta methods 4th order
 * @author 122
 */
public abstract class DiffEq extends DynSys {
    
    /**
     * Hold all current traces
     */
    protected HashSet<HashSet<Double[]>> traces = new HashSet<>();
    
    /**
     * Number of iterations
     */
    protected int iterCount = 500;
    /**
     * Step of the time t
     */
    protected double step = 0.01;
    /**
     * Starting point of the trace which can be continued
     */
    protected double[] x0;
    
    
    protected boolean periodicB;

    public boolean isPeriodic() {
        return periodicB;
    }
    /**
     * Minimal period of the ODE
     */
    private double period;

    public double getPeriod() {
        return period;
    }

    protected void setPeriod(double period) {
        if(periodicB)
          this.period = period;
        else
          System.err.println("The system isn't periodic, can't set the period.");
    }
    
    public double[] getX0() {
        return x0;
    }

    public void setX0(double... x0) {
        this.x0 = x0;
    }

    public RightPart[] getRps() {
        return rps;
    }

    public void setRps(RightPart[] rps) {
        this.rps = rps;
    }

    public HashSet<HashSet<Double[]>> getTraces() {
        return traces;
    }

    public void setTraces(HashSet<HashSet<Double[]>> traces) {
        this.traces = traces;
    }

    public int getIterCount() {
        return iterCount;
    }

    public void setIterCount(int iterCount) {
        if(iterCount > 0)
            this.iterCount = iterCount;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        if(step > 0)
            this.step = step;
    }
    
    /**
     * Implementation of implicit
     * Runge-Kutta method 4th-order.
     * It creates a new trace from
     * the starting point x0, adding
     * points. Note that there is 
     * a world coordinate system
     */
    public void rk4() {
        if(x0 == null)
            return;
        
        final int n = x0.length;
        
        double[][] k = new double[n][4];
        double[] args = new double[n];
        final double koef = 1.0/6;
        
        HashSet<Double[]> trace = new HashSet<>(iterCount);
        Double[] pointCur = new Double[n];
        for(int i = 0; i < n; i++)
            pointCur[i] = x0[i];
        
        trace.add(pointCur);
        
        Double[] pointNext;
        double t;
        double tHalf;
        
        //temporary array
        double[] tmp = new double[n];
        
        for(int i = 1; i <= iterCount; i++) {
            t = i * step;
            tHalf = t + step/2;
            
            for(int j = 0; j < n; j++)
                tmp[j] = pointCur[j];
            
            for(int j = 0; j < n; j++) {
                k[j][0] = step * rps[j].f(t, tmp);
                args[j] = pointCur[j] + k[j][0]/2;
            }
            
            for(int j = 0; j < n; j++) {
                k[j][1] = step * rps[j].f(tHalf, args);
            }
            
            for(int j = 0; j < n; j++) {
                 args[j] = pointCur[j] + k[j][1]/2;
            }
             
            for(int j = 0; j < n; j++) {
                k[j][2] = step * rps[j].f(tHalf, args);
            }
            
            for(int j = 0; j < n; j++) {
                 args[j] = pointCur[j] + k[j][2];
            }
            
            for(int j = 0; j < n; j++) {
                k[j][3] = step * rps[j].f(t, args);
            }
            
            pointNext = new Double[n];
            for(int j = 0; j < n; j++) {
                pointNext[j] = pointCur[j] + koef *(k[j][0] + 2*k[j][1] + 2*k[j][2] + k[j][3]);
            }
            
            if(!isPeriodic() || i == iterCount) {
              trace.add(pointNext);
            }
            pointCur = pointNext;
        }
        
        //add last point of trace for possibility of continuing the trace
        for(int j = 0; j < n; j++)
            tmp[j] = pointCur[j];
        setX0(tmp);
        
        traces.add(trace);
    }
    
    public void poincareMap() {
        if(periodicB && period > 0.0) {
            step = period / iterCount;
            
            for(int i = 0; i < 500; i++)
                this.rk4();
        }
        else
          System.err.println("Can't call the PoincareMap: period = " + period);
    }
}

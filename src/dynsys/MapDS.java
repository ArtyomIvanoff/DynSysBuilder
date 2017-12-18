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
 *
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
        if (iterCount > 0) {
            this.iterCount = iterCount;
        }
    }

    public double[] getX0() {
        return x0;
    }

    public void setX0(double... x0) {
        this.x0 = x0;
    }

    /**
     * The method creates a sequence of points which are added to a new cascade
     * Note that there is a world coordinate system
     */
    public void getSequence() {
        if (x0 == null) {
            return;
        }

        final int n = x0.length;

        HashSet<Double[]> cascade = new HashSet<>(iterCount);
        Double[] pointCur = new Double[n];
        for (int i = 0; i < n; i++) {
            pointCur[i] = x0[i];
        }

        cascade.add(pointCur);

        //temporary array
        double[] tmp = new double[n];
        Double[] pointNext;
        for (int i = 0; i < iterCount - 1; i++) {
            for (int j = 0; j < n; j++) {
                tmp[j] = pointCur[j];
            }

            pointNext = new Double[n];
            for (int j = 0; j < n; j++) {
                pointNext[j] = rps[j].f(0, tmp);
                pointCur[j] = pointNext[j];
            }

            cascade.add(pointCur);
        }

        //add last inner point for possibility of continuening the trace
        for (int j = 0; j < n; j++) {
            tmp[j] = pointCur[j];
        }
        setX0(tmp);

        cascades.add(cascade);
    }
}

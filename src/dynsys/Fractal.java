/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

/**
 * Abstract class of fractals
 * @author 122
 */
public abstract class Fractal extends DynSys {
    protected int iterations;

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        if(iterations >= 0)
            this.iterations = iterations;
    }
}

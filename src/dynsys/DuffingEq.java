/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

/**
 * Represents x" + c1*x + c2*(x^3) = a*sin(b*t)
 * @author 122
 */
public class DuffingEq extends DiffEq2D {
    public final double c1;
    public final double c2;
    public final double a;
    public final double b;
    
    public DuffingEq(double c1, double c2, double a, double b) {
        this.c1 = c1;
        this.c2 = c2;
        this.a = a;
        this.b = b;
        
        //if the system is periodic with period 2PI / b
        if(a != 0 && b != 0) {
            periodicB = true;
            setPeriod((2*Math.PI)/b);
        }
        
        rps = new RightPart[] {
            (t, x) -> { return x[1]; },
            (t, x) -> { return -c1*x[0] - c2*Math.pow(x[0], 3) + a*Math.sin(b*t);}
        };
    }
    
    public DuffingEq(double a, double b) {
        this(-1, -1, a, b);
    }
    
    public DuffingEq(double b) {
        this(0, b);
    }
    
    public DuffingEq() {
        this(1.0);
    }
    
    @Override
    public String toString(){
        return "Duffing";
    }
}

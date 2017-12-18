/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import static java.lang.Math.*;
/**
 * Standart map
 * @see DynSys#mod2PI(double) 
 * @author 122
 */
public class Chirikov extends MapDS2D {
    public final double a;
    
    private RightPart[] rpsR2;
    private RightPart[] rpsCyl2PI;
    private RightPart[] rpsTorus2PI;
    
    public Chirikov(double a, Area ar) {
        this.a = a;
        
        rpsR2 = new RightPart[] {
            (t, x) -> { return x[0] + rps[1].f(0, x); },
            (t, x) -> { return x[1] + a*sin(x[0]); }
        };
        rpsCyl2PI = new RightPart[] {
            (t, x) -> { return mod2PI(rpsR2[0].f(0, mod2PI(x[0]), x[1])); },
            (t, x) -> rpsR2[1].f(t, mod2PI(x[0]), x[1])
        };
        rpsTorus2PI = new RightPart[] {
            (t, x) -> { return mod2PI(rpsR2[0].f(0, mod2PI(x[0]), mod2PI(x[1]))); },
            (t, x) -> {return mod2PI(rpsR2[1].f(t, mod2PI(x[0]), x[1])); }
        };
        
        switch(ar) {
            case R2 :
                rps = rpsR2;
                break;
            case CYLINDER :
                rps = rpsCyl2PI;
                break;
            case TORUS :
                rps = rpsTorus2PI;
        }
    }
    
    public Chirikov(double a) {
       this(a, Area.R2);
    }
    
    public Chirikov() {
        this(0.7);
    }
    
    @Override
    public String toString(){
        return "Chirikov_" + a;
    }
}

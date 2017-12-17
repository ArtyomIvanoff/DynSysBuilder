/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

/**
 *
 * @author 122
 */
public class IFS {

    public IFS(double[][] cmtx, Transformer tr1) {
        numAT = cmtx.length;
        C = new double[numAT][];
        this.tr = tr1;

        for (int i = 0; i < numAT; i++) {
            if (cmtx[i].length != 6) {
                throw new UnsupportedOperationException("Wrong IFS!");
            }

            C[i] = cmtx[i];
        }
    }

    public static IFS getInstance(CommonIFS select) {
        // the area is 1x1 square with the left-bottom corner (0;0)
        Transformer tr = new Transformer(0.5, 0.5, 0.5);
        
        switch (select) {
            case SierpinskiTriangle:
                return new IFS(IFS.getMtxST(), tr);
            case SierpinskiCarpet:
                return new IFS(IFS.getMtxSC(), tr);
            case Fern:
                return new IFS(IFS.getMtxFern(), tr);
            case Crystal:
            default:
                return new IFS(IFS.getMtxCrystal(), tr);
        }
    }

    public double applyTransformX(int k, double xS, double yS) {
        return C[k][0] * xS + C[k][1] * yS + C[k][4];
    }

    public double applyTransformY(int k, double xS, double yS) {
        return C[k][2] * xS + C[k][3] * yS + C[k][5];
    }

    public int getNumAT() {
        return numAT;
    }

    public double[][] getC() {
        return C;
    }
    
    public enum CommonIFS {
        SierpinskiTriangle, SierpinskiCarpet, Fern, Crystal
    }

    public Transformer getTr() {
        return tr;
    }
    
    private final int numAT;
    private double[][] C;
    private Transformer tr;

    private static double[][] getMtxST() {
        final int n = 3;
        double[][] C1 = new double[n][6];

        for (int i = 0; i < n; i++) {
            C1[i][0] = C1[i][3] = 0.5;
            C1[i][1] = C1[i][2] = 0.0;
        }

        C1[0][4] = C1[0][5] = 0.0;
        C1[1][4] = 0.5;
        C1[1][5] = 0.0;
        C1[2][4] = 0.25;
        C1[2][5] = 0.433;

        return C1;
    }

    private static double[][] getMtxSC() {
        final int n = 8;
        double[][] C1 = new double[n][6];
        double koef = 1.0 / 3.0;

        for (int i = 0; i < n; i++) {
            C1[i][0] = C1[i][3] = koef;
            C1[i][1] = C1[i][2] = 0.0;
        }

        C1[0][4] = C1[0][5] = 0.0;
        C1[1][4] = 0.0;
        C1[1][5] = koef;
        C1[2][4] = 0.0;
        C1[2][5] = 2 * koef;

        C1[3][4] = koef;
        C1[3][5] = 0.0;
        C1[4][4] = koef;
        C1[4][5] = 2 * koef;

        C1[5][4] = 2 * koef;
        C1[5][5] = 0.0;
        C1[6][4] = 2 * koef;
        C1[6][5] = koef;
        C1[7][4] = 2 * koef;
        C1[7][5] = 2 * koef;

        return C1;
    }

    private static double[][] getMtxFern() {
        final int n = 4;
        double[][] C1 = new double[n][6];

        C1[0][0] = 0.7;
        C1[0][1] = C1[0][2] = 0.0;
        C1[0][3] = 0.7;
        C1[0][4] = 0.1496;
        C1[0][5] = 0.2962;

        C1[1][0] = 0.1;
        C1[1][1] = -0.433;
        C1[1][2] = 0.1732;
        C1[1][3] = 0.25;
        C1[1][4] = 0.4478;
        C1[1][5] = 0.0014;

        C1[2][0] = 0.1;
        C1[2][1] = 0.4333;
        C1[2][2] = -0.1732;
        C1[2][3] = 0.25;
        C1[2][4] = 0.4445;
        C1[2][5] = 0.1559;

        C1[3][0] = C1[3][1] = C1[3][2] = 0.0;
        C1[3][3] = 0.3;
        C1[3][4] = 0.4987;
        C1[3][5] = 0.007;

        return C1;
    }

    private static double[][] getMtxCrystal() {
        final int n = 4;
        double[][] C1 = new double[n][6];

        C1[0][0] = 0.255;
        C1[0][1] = C1[0][2] = 0.0;
        C1[0][3] = 0.255;
        C1[0][4] = 0.3726;
        C1[0][5] = 0.6714;

        C1[1][0] = 0.255;
        C1[1][1] = C1[0][2] = 0.0;
        C1[1][3] = 0.255;
        C1[1][4] = 0.1146;
        C1[1][5] = 0.2232;

        C1[2][0] = 0.255;
        C1[2][1] = C1[2][2] = 0.0;
        C1[2][3] = 0.255;
        C1[2][4] = 0.6306;
        C1[2][5] = 0.2232;

        C1[3][0] = 0.37;
        C1[3][1] = -0.642;
        C1[3][2] = 0.642;
        C1[3][3] = 0.37;
        C1[3][4] = 0.6356;
        C1[3][5] = -0.0061;

        return C1;
    }
    
    
}

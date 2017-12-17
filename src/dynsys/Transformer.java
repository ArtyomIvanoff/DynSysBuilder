/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynsys;

import static gui.DrawPanel.HEIGHT_P;
import static gui.DrawPanel.WIDTH_P;
import java.awt.geom.Point2D;

/**
 *
 * @author 122
 */
/**
 * Class helps to work in the world coordinates which are used in rk4(). This
 * class depends from size of DrawPanel.
 *
 * @see DiffEq#rk4()
 * @see DrawPanel
 */
public class Transformer {

    public double getxMin() {
        return xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public double getyMin() {
        return yMin;
    }

    public double getyMax() {
        return yMax;
    }
    
    //edges of the picture

    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    
    //distance from the centre to edge
    private double dist;
    //centre of the picture
    private double xCent;
    private double yCent;
    //screen size
    private int wS = WIDTH_P;
    private int hS = HEIGHT_P;

    public double getwS() {
        return wS;
    }

    public void setwS(int wS) {
        this.wS = wS;
    }

    public double gethS() {
        return hS;
    }

    public void sethS(int hS) {
        this.hS = hS;
    }
    

    public void setDist(double dist) {
        if (dist > 0) {
            this.dist = dist;
            setEdges();
        }
    }

    public void setxCent(double xCent) {
        this.xCent = xCent;
        setEdges();
    }

    public void setyCent(double yCent) {
        this.yCent = yCent;
        setEdges();
    }

    public final void setCentre(double newX, double newY) {
        xCent = newX;
        yCent = newY;

        setEdges();
    }

    public double getDist() {
        return dist;
    }

    public double getxCent() {
        return xCent;
    }

    public double getyCent() {
        return yCent;
    }

    public Transformer(double xC, double yC, double d) {
        xCent = xC;
        yCent = yC;
        dist = d;

        setEdges(xCent, yCent, dist);
    }

    public Transformer() {
        this(0, 0, 2);
    }

    public int toScreenX(double wX) {
        int sX = (int)(wS * (wX - xMin) / (xMax - xMin));
        if (sX == wS) // if the coordinate is a bit out of the screen width
        {
            sX = wS - 1;
        }

        return sX;
    }

    public int toScreenY(double wY) {
        int sY = (int)(hS * (yMax - wY) / (yMax - yMin));
        if (sY == hS) // if the coordinate is a bit out of the screen height
        {
            sY = hS - 1;
        }

        return sY;
    }

    public void toScreen(Point2D.Double S, Point2D.Double W) {
        S.x = toScreenX(W.x);
        S.y = toScreenY(W.y);
    }

    public double toWorldX(double sX) {
        return sX * (xMax - xMin) / wS + xMin;
    }

    public double toWorldY(double sY) {
        return sY * (yMin - yMax) / hS + yMax;
    }

    public void toWorld(Point2D.Double S, Point2D.Double W) {
        W.x = toWorldX(S.x);
        W.y = toWorldY(S.y);
    }

    public final void setEdges(double centX, double centY, double newDist) {
        setCentre(centX, centY);
        setDist(newDist);
    }

    public final void setEdges() {
        xMin = xCent - dist;
        xMax = xCent + dist;
        yMin = yCent - dist;
        yMax = yCent + dist;
    }

    /**
     * Check whether the point is inside of the screen
     *
     * @param WorldP point in the world coordinate system
     * @return true if it's inside, else - false
     */
    public boolean isInner(Point2D.Double WorldP) {
        return (WorldP.x >= xMin && WorldP.x <= xMax)
                && (WorldP.y >= yMin && WorldP.y <= yMax);
    }
}

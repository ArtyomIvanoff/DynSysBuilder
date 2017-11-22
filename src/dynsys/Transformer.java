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
     * Class helps to work in the world coordinates
     * which are used in rk4().
     * This class depends from size of DrawPanel.
     * @see DiffEq#rk4()
     * @see DrawPanel
     */
    public class Transformer {
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
        private double wS = WIDTH_P;

    public double getwS() {
        return wS;
    }

    public void setwS(double wS) {
        this.wS = wS;
    }

    public double gethS() {
        return hS;
    }

    public void sethS(double hS) {
        this.hS = hS;
    }
        private double hS = HEIGHT_P;

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
        
        public Transformer() {
            xCent = yCent = 0;
            dist = 2;
            
            setEdges(xCent, yCent, dist);
        }

        public void toScreen(Point2D.Double S, Point2D.Double W) {
            S.x = wS * (W.x  - xMin)/(xMax - xMin);
            S.y = hS * (yMax - W.y)/(yMax - yMin);
        }

        public void toWorld(Point2D.Double S, Point2D.Double W) {
            W.x = S.x*(xMax - xMin)/wS + xMin;
            W.y = S.y*(yMin-yMax)/hS+ yMax;
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
         * @param WorldP point in the world coordinate system
         * @return true if it's inside, else - false
         */
        public boolean isInner(Point2D.Double WorldP) {
            return (WorldP.x >= xMin && WorldP.x <= xMax) &&
                    (WorldP.y >= yMin && WorldP.y <= yMax);
        }
    }
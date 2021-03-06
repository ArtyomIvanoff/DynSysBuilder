/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dynsys.DynSys;
import dynsys.DiffEq2D;
import dynsys.DuffingEq;
import dynsys.MapDS2D;
import dynsys.Transformer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

/**
 *
 * @author 122
 */
public class DrawPanel extends javax.swing.JPanel {
    public static final int HEIGHT_P = 500;
    public static final int WIDTH_P = 500;
    
    private DynSys ds;
    
    public DynSys getDs() {
        return ds;
    }

    public void setDs(DynSys ds) {
        this.ds = ds;
    }
    /**
     * Creates new form DrawPanel
     */
    public DrawPanel() {
        initComponents();
        ds = new DuffingEq(1, -1, 0.0, 1);
        //ds = new Chirikov(0.7);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(500, 500));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
       Point2D.Double p = new Point2D.Double(evt.getX(), evt.getY());
       Point2D.Double w = new Point2D.Double();
        
        if(ds instanceof DiffEq2D) {
            DiffEq2D de = (DiffEq2D)ds;
            Transformer tr = de.getTr();
            tr.toWorld(p, w);
            de.setX0(w.x, w.y);
            
            if(de.isPeriodic())
              de.poincareMap();
            else
              de.rk4();
            
            repaint();
        } else if(ds instanceof MapDS2D) {
            MapDS2D map = (MapDS2D)ds;
            Transformer tr = map.getTr();
            tr.toWorld(p, w);
            map.setX0(w.x, w.y);
            map.getSequence();
            repaint();
        }   
    }//GEN-LAST:event_formMouseClicked
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.drawImage(ds.getImage(), null, null);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

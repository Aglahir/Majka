/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Dell X
 */
public class MouseManager implements MouseListener, MouseMotionListener{
    private boolean izquierdo;  //to check if left has been pushed
    private boolean derecho;    //to check if right has been pushed
    private boolean dragged;
    private int x;              //to get x position of the mouse
    private int y;              //to get y position of the mouse
    
   
    public MouseManager(){
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            izquierdo = true;
            x = e.getX();
            y = e.getY();
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean isIzquierdo(){
        return izquierdo;
    }
    
    public boolean isDerecho(){
        return derecho;
    }
    
    public boolean isDragged(){
        return dragged;
    }
    
    public void setIzquierdo(boolean izquierdo){
        this.izquierdo = izquierdo;
    }
}
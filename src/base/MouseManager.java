package base;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener{

    private boolean left;               //to check if left has been pushed
    private boolean right;              //to check if right has been pushed
    private int x;                      //to get x position of the mouse
    private int y;                      //to get y position of the mouse
    
    public MouseManager()
    {
        
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public boolean isLeft()
    {
        return left;
    }
    
    public boolean isRight()
    {
        return right;
    }
    
    public void setLeft(boolean left)
    {
        this.left = left;
    }
    
    public void setRight(boolean right)
    {
        this.right = right;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        //change left boolean to true
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            x = e.getX();
            y = e.getY();
            left = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        //reset left to false
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            left = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //Update X and Y values while dragging
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
            
    }

}

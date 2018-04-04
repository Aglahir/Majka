package base;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyManager implements KeyListener{

    public boolean Q;      //flag to move up the player
    public boolean E;      //flag to move down the player
    public boolean A;      //flag to move left the player
    public boolean D;      //flag to move right the player
    public boolean P;      //flag to pause game
    public boolean R;      //flag to restart game
    public boolean S;      //floag to save
    public boolean L;      //floag to load
    public boolean Left;   //flag to left 
    public boolean Right;  //flag to right
    public boolean Up;     //flag to up
    
    private boolean keys[]; //to store all the flags for every key
    
    /**
     * Creates boolean for key storage
     */
    public KeyManager()
    {
        keys = new boolean[256];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //set true to every key pressed
        if(e.getKeyCode() != KeyEvent.VK_P)
        {
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //set false to every key released
        if(e.getKeyCode() == KeyEvent.VK_P)
        {
            if(keys[e.getKeyCode()])
                keys[e.getKeyCode()] = false;
            else
                keys[e.getKeyCode()] = true;
        }else
        {
            keys[e.getKeyCode()] = false;
        }
    }
    
    /**
     * Update key state into array
     */
    public void tick()
    {
        E = keys[KeyEvent.VK_E];
        A = keys[KeyEvent.VK_A];
        Q = keys[KeyEvent.VK_Q];
        D = keys[KeyEvent.VK_D];
        P = keys[KeyEvent.VK_P];
        R = keys[KeyEvent.VK_R];
        S = keys[KeyEvent.VK_S];
        L = keys[KeyEvent.VK_L];
        Left = keys[KeyEvent.VK_LEFT];
        Right = keys[KeyEvent.VK_RIGHT];
        Up = keys[KeyEvent.VK_UP];
    }
    
    public boolean isS()
    {
        return keys[KeyEvent.VK_S];
    }
    
    public boolean isL()
    {
        return keys[KeyEvent.VK_L];
    }
    
}

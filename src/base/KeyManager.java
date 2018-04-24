package base;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyManager implements KeyListener{

    public boolean W;           //Key to move up
    public boolean A;           //Key to move left
    public boolean S;           //Key to move down
    public boolean D;           //Key to move right
    public boolean Q;           //Key to change weapon
    public boolean E;           //Key to change weapon 2
    public boolean P;           //Key to pause the game
    public boolean M;           //Key to show the instructive of the game
    public boolean Enter;       //Key to continue
    public boolean Escape;      //Key to return
    public boolean Up;          //Key to attack up
    public boolean Left;        //Key to attack left    
    public boolean Down;        //Key to attack down
    public boolean Right;       //Key to attack right
    public boolean Space;       //space key 
    
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

    /**
     * Void to identify wich key is pressed
     * 
     * @param e the event of a key pressed in the keyboard 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        //set true to every key pressed
        if(e.getKeyCode() != KeyEvent.VK_P && e.getKeyCode() != KeyEvent.VK_M)
        {
            keys[e.getKeyCode()] = true;
        }
    }

    /**
     *  Void to see if a key is released
     * @param e the event on the keyboard
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //set false to every key released
        if(e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_M)
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
        W = keys[KeyEvent.VK_W];
        A = keys[KeyEvent.VK_A];        
        S = keys[KeyEvent.VK_S];
        D = keys[KeyEvent.VK_D];
        Q = keys[KeyEvent.VK_Q];
        E = keys[KeyEvent.VK_E];
        P = keys[KeyEvent.VK_P];
        M = keys[KeyEvent.VK_M];
        if(P){
            keys[KeyEvent.VK_P] = false;
        }
        if(M){
            keys[KeyEvent.VK_M] = false;
        }
        Up     = keys[KeyEvent.VK_UP];
        Left   = keys[KeyEvent.VK_LEFT];
        Down   = keys[KeyEvent.VK_DOWN];        
        Right  = keys[KeyEvent.VK_RIGHT];        
        Enter  = keys[KeyEvent.VK_ENTER];        
        Escape = keys[KeyEvent.VK_ESCAPE];
        Space  = keys[KeyEvent.VK_SPACE];
    }
    
}

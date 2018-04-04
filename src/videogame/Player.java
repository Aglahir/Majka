
package videogame;

import base.Animation;
import base.Collider;
import base.Item;
import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author Aglahir Jiménez Flórez
 */
public class Player extends Item{

    private Game game;          // store game
    
    
    
    public Player(int x, int y, int width, int height, Game game, int vel){
        super(x, y, width, height);
        this.game = game;
        
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
       
    }
}

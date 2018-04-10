
package videogame;

import base.Animation;
import base.Collider;
import base.Item;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Hack'n matata
 */
public class Mob extends Item{
    
    private Game game;
    
    
    /**
     * 
     * @param x the x position of the mob
     * @param y the y position of the mob
     * @param width the width of the mob
     * @param height the height of the mob
     * @param game the game
     * @param vel the velocity
     */
    public Mob(int x, int y, int width, int height, Game game, int vel){
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

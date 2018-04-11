
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
    private int direction;
    private int speed;
    
    /**
     * 
     * @param x the x position of the mob
     * @param y the y position of the mob
     * @param width the width of the mob
     * @param height the height of the mob
     * @param game the game
     * @param direction the direction
     */
    public Mob(int x, int y, int width, int height, Game game, int direction){
        super(x, y, width, height);
        this.game = game;
        this.direction = direction;
        this.speed = 15;
    }
    
    @Override
    public void tick() {
        switch(direction){
            case 1: 
                    setX(getX()-speed);
                    break;
            case 2: 
                    setY(getY()-speed);
                    break;
            case 3: 
                    setX(getX()+speed);
                    break;
            case 4: 
                    setY(getY()+speed);
                    break;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}

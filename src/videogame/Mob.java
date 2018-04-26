
package videogame;

import base.Animation;
import base.Collider;
import base.Item;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Hack'n matata
 */
public class Mob extends Item{
    
    private Game game;
    private int direction;
    private int speed;
    private Animation sprite;
    private int type;
    private boolean alive;
    private int endAnimationFrameCount;
    private Collider collider;
    private int damage;
    
    
    /**
     * This class can be used to create throwable objects
     * @param x the x position of the mob
     * @param y the y position of the mob
     * @param width the width of the mob
     * @param height the height of the mob
     * @param game the game
     * @param direction the direction 1: Left, 2:Up, 3:Right, 4:Down
     * @param type type of Mob to be created
     */
    public Mob(int x, int y, int width, int height, Game game, int direction, int type){
        super(x, y, width, height);
        this.game = game;
        this.direction = direction;
        this.type = type;
        this.alive = true;
        this.endAnimationFrameCount = 0;
        this.collider = new Collider(getX()+getWidth()/2,getY()+getHeight()/2,Math.max(getWidth()/2, getHeight()/2));
        switch(type){
            case 1:
                damage = 30;
                switch(direction){
                    case 1: 
                            sprite = new Animation(Assets.arrowLeft);
                            break;
                    case 2: 
                            sprite = new Animation(Assets.arrowUp);
                            break;
                    case 3: 
                            sprite = new Animation(Assets.arrowRight);
                            break;
                    case 4: 
                            sprite = new Animation(Assets.arrowDown);
                            break;
                }
                this.speed = 15;
                break;
        }
    }
    
    public boolean isDead(){
        return sprite.animationEnded();
    }
    
    public void setDead(){
        alive = false;
    }
    
    public boolean isAlive(){
        return alive;
    }
    
    public Collider getCollider(){
        return this.collider;
    }
    
    @Override
    public void tick() {
        
        if(type==1){
            if(!alive){
                sprite.tick();
            }else{
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
        }else sprite.tick();
        
        collider.setX(getX()+getWidth()/2);
        collider.setY(getY()+getHeight()/2);
    }

    public int getDamage(){
        return this.damage;
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(sprite.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
}

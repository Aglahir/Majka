
package videogame;

import base.Animation;
import base.Collider;
import base.Item;
import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author Hack'n Matata
 */
public class Player extends Item{

    private Game game;                  // store game
    private Animation actualAnimation;
    private int speed;
    private int direction;              // 0-Iddle 1-Left 2-Up 3-Right 4-Down
    private float minMapPosX, minMapPosY; // positions of point on screen for the mini map player
    private int lifes;
    /**
     * 
     * @param x the x position of the player
     * @param y the y position of the player
     * @param width the width of the player
     * @param height the height of the player
     * @param game the game
     * @param vel the velocity
     */
    public Player(int x, int y, int width, int height, Game game){
        super(x, y, width, height);
        this.game = game;
        this.actualAnimation = Assets.playerRBasic;
        this.speed = 8;
        this.direction = 2;
        this.minMapPosX = (float)this.game.getWidth()-218;
        this.minMapPosY = 128f;
        this.lifes = 3;
    }
    
    /**
     * Checks where is it moving to
     */
    private void checkDirection(){
        direction=0;
        
        if(game.getKeyManager().D && !game.getKeyManager().A){actualAnimation = Assets.playerRBasic;direction=3;}
        else if(!game.getKeyManager().D && game.getKeyManager().A){actualAnimation = Assets.playerLBasic;direction=1;}
        
        switch(direction){
            case 1: 
                    setX(getX()-speed);
                    minMapPosX -= (float)speed/37;
                    actualAnimation.tick();
                    break;
            case 3: 
                    setX(getX()+speed);
                    minMapPosX += (float)speed/37;
                    actualAnimation.tick();
                    break;
        }
        
        if(game.getKeyManager().W && !game.getKeyManager().S){direction=2;}
        else if(!game.getKeyManager().W && game.getKeyManager().S){direction=4;}
        
        switch(direction){
            case 2: 
                    setY(getY()-speed);
                    minMapPosY -= (float)speed/31;
                    actualAnimation.tick();
                    break;
            case 4: 
                    setY(getY()+speed);
                    minMapPosY += (float)speed/31;
                    actualAnimation.tick();
                    break;
        }
    }
    
    @Override
    public void tick() {
        checkDirection();
        if(game.getKeyManager().Left)
            game.addHadouken(getX(), getY(), getWidth(), getHeight(), 1);
        if(game.getKeyManager().Up)
            game.addHadouken(getX(), getY(), getWidth(), getHeight(), 2);
        if(game.getKeyManager().Right)
            game.addHadouken(getX(), getY(), getWidth(), getHeight(), 3);
        if(game.getKeyManager().Down)
            game.addHadouken(getX(), getY(), getWidth(), getHeight(), 4);
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(actualAnimation.getCurrentFrame(),getX(),getY(),getWidth(),getHeight(),null);
       g.drawImage(Assets.minimap,game.getWidth()-250,20,game.getWidth()/6,game.getHeight()/6,null);
       g.drawImage(actualAnimation.getCurrentFrame(),(int)minMapPosX,(int)minMapPosY,getWidth()/10,getHeight()/10,null);
    }
}

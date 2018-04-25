
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

    private Game game;                      // store game
    private Animation actualAnimation;
    private int speedX;
    private int speedY;
    private int maxSpeed;
    private int direction;                  // 0-Iddle 1-Left 2-Up 3-Right 4-Down
    private float minMapPosX, minMapPosY;   // positions of point on screen for the mini map player
    private int lifes;
    private Collider collider;
    private int timerTemporalAnimation;     // This timer is made to work with any temporal animation needed
    private int playerState;                // 0: normal, 1: Colliding, 2: Attacking
    
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
        this.speedX = 0;
        this.speedY = 0;
        this.maxSpeed = 8;
        this.direction = 2;
        this.minMapPosX = (float)this.game.getWidth()-218;
        this.minMapPosY = 128f;
        this.lifes = 3;
        this.collider = new Collider(getX()+getWidth()/2,getY()+getHeight()/2,getWidth()/2);
        this.timerTemporalAnimation = 0;
        this.playerState = 0;                               //Iddle
    }
    
    /**
     * Checks where is it moving to
     */
    private void checkDirection(){
        direction=0;
        double tmp1 = Math.random()*50-25,tmp2 = Math.random()*5;
        if(game.getKeyManager().D && !game.getKeyManager().A){actualAnimation = Assets.playerRBasic;direction=3;}
        else if(!game.getKeyManager().D && game.getKeyManager().A){actualAnimation = Assets.playerLBasic;direction=1;}
        
        switch(direction){
            case 1: 
                    if(getX()>=game.getX0())if(speedX>maxSpeed*-1)speedX--;
                    minMapPosX -= (float)speedX/37;
                    game.createParticle(new Popup(getX()+getWidth()/2,getY()+getHeight()+(int)tmp1,(int)tmp2,(int)tmp2,4,50,10));
                    break;
            case 3: 
                    if(getX()<=game.getXf())if(speedX<maxSpeed)speedX++;
                    minMapPosX += (float)speedX/37;
                    game.createParticle(new Popup(getX()+getWidth()/2,getY()+getHeight()+(int)tmp1,(int)tmp2,(int)tmp2,4,50,10));
                    break;
            
            default:
                    if(speedX>0)speedX-=2;
                    else if(speedX<0)speedX+=2;
                    actualAnimation = Assets.playerIddleBasic;
                    break;
        }
        
        if(game.getKeyManager().W && !game.getKeyManager().S){actualAnimation = Assets.playerUBasic;direction=2;}
        else if(!game.getKeyManager().W && game.getKeyManager().S){actualAnimation = Assets.playerDBasic;direction=4;}
        else direction=5;
        
        switch(direction){
            case 2: 
                     if(getY()>=game.getY0())if(speedY>maxSpeed*-1)speedY--;
                    minMapPosY -= (float)speedY/31;
                    game.createParticle(new Popup(getX()+(int)tmp1+getWidth()/2,getY()+getHeight()+(int)tmp1,(int)tmp2,(int)tmp2,4,50,10));
                    break;
            case 4: 
                     if(getY()<=game.getYf())if(speedY<maxSpeed)speedY++;
                    minMapPosY += (float)speedY/31;
                    game.createParticle(new Popup(getX()+(int)tmp1+getWidth()/2,getY(),(int)tmp2,(int)tmp2,4,50,10));
                    break;
            case 5:
                    if(speedY>0)speedY-=2;
                    else if(speedY<0)speedY+=2;
        }
        setX(getX()+speedX);
        setY(getY()+speedY);
        actualAnimation.tick();
    }
    
    public Collider getCollider(){
        return this.collider;
    }
    
    public boolean checkCollision(Collider col){
        return collider.checkCollision(col);
    }
    
    public void collisionJump(Item item) {
        speedX*=-1;
        speedY*=-1;
        
        if(getX()<item.getX())speedX-=10;
        else speedX+=10;
        
        if(getY()<item.getY())speedY-=10;
        else speedY+=10;
                
        setX(getX()+speedX);
        setY(getY()+speedY);
    }
    
    /**
     * Takes one life from the player, makes collision animation and checks if still alive
     * @return 
     */
    public boolean hitPlayer(Item item){
        lifes--;
        collisionJump(item);
        return lifes>0;
    }
    
    @Override
    public void tick() {
        checkDirection();
        if(game.getKeyManager().Left)
            game.addMob(getX(), getY(), getWidth(), getHeight(), 1,1);
        if(game.getKeyManager().Up)
            game.addMob(getX(), getY(), getWidth(), getHeight(), 2,1);
        if(game.getKeyManager().Right)
            game.addMob(getX(), getY(), getWidth(), getHeight(), 3,1);
        if(game.getKeyManager().Down)
            game.addMob(getX(), getY(), getWidth(), getHeight(), 4,1);
        
        collider.setX(getX()+getWidth()/2);
        collider.setY(getY()+getHeight()/2);
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(actualAnimation.getCurrentFrame(),getX(),getY(),getWidth(),getHeight(),null);
       g.drawImage(Assets.minimap,game.getWidth()-250,20,game.getWidth()/6,game.getHeight()/6,null);
       g.drawImage(actualAnimation.getCurrentFrame(),(int)minMapPosX,(int)minMapPosY,getWidth()/10,getHeight()/10,null);
    }
}

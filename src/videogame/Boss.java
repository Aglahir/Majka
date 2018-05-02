
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
public class Boss extends Item{

    private Game game;                  // store game
    private Animation actualAnimation;
    private int speed;
    private int direction;              // 0-Iddle 1-Left 2-Up 3-Right 4-Down
    private int life;
    private boolean charging;
    private int contador=0;
    int targetX = 0;
    int targetY = 0;
    private Collider collider;
    /**
     * 
     * @param x the x position of the spaniard
     * @param y the y position of the spaniard
     * @param width the width of the spaniard
     * @param height the height of the spaniard
     * @param life the life of the boss
     * @param game the game
     */
    public Boss(int x, int y, int width, int height,int life, Game game){
        super(x, y, width, height);
        this.game = game;
        this.actualAnimation = Assets.bossLeft;
        this.speed = 2;
        this.direction = 2;
        this.life = life;
        this.collider = new Collider(getX()+getWidth()/2,getY()+getHeight()/2,getWidth()/2);
    }
    
    /**
     * Checks where is it moving to
     */
    private void checkDirection(){
        direction=0;
        if(getX()>game.getPlayer().getX()+game.getPlayer().getWidth())direction=1;
        else if(getX()+getWidth()<game.getPlayer().getX())direction=3;
        
        switch(direction){
            case 1: 
                    setX(getX()-speed);
                    actualAnimation = Assets.bossLeft;
                    break;
            case 3: 
                    setX(getX()+speed);
                    actualAnimation = Assets.bossRight;
                    break;
        }
        
        if(getY()>game.getPlayer().getY()+2)direction=2;
        else if(getY()+2<game.getPlayer().getY() && getY()+game.getScale()*3<=game.getYf())direction=4;
        
        switch(direction){
            case 2: 
                    setY(getY()-speed);
                    //actualAnimation = Assets.spaniardUBasic;
                    break;
            case 4: 
                    setY(getY()+speed);
                    //actualAnimation = Assets.spaniardDBasic;
                    break;
        }        
        actualAnimation.tick();
    }
    
    /**
     * to check collition with arrow
     * @param damage the damage received
     * @return boss state, boolean
     */
    public boolean hurt(int damage){
        double tmp1 = Math.random()*(150-25)+25,
               tmp2 = Math.random()*(3 +1),
               tmp3 = Math.random()*(150-25)+25;
        game.createParticle(new Popup(getX()+(int)tmp1,getY()+(int)tmp3,30+(int)tmp1,30+(int)tmp1,(int)tmp2,100,9));
        life-=damage;
        return life<=0;
    }
    
    
    private void charge(int targetX, int targetY){                        
        direction=0;
        if(getX()>targetX+game.getPlayer().getWidth())direction=1;
        else if(getX()+getWidth()<targetX)direction=3;
        
        switch(direction){
            case 1: 
                    setX(getX()-12);
                    actualAnimation = Assets.bossLeft;
                    break;
            case 3: 
                    setX(getX()+12);
                    actualAnimation = Assets.bossRight;
                    break;
        }
        
        if(getY()>targetY+2)direction=2;
        else if(getY()+2<targetY && getY()+game.getScale()*3<=game.getYf())direction=4;
        
        switch(direction){
            case 2: 
                    setY(getY()-12);                    
                    break;
            case 4: 
                    setY(getY()+12);                    
                    break;
        }        
        actualAnimation.tick();
    }
    
    
    public Collider getCollider(){
        return this.collider;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    
    
    
    @Override
    public void tick() {            
        //checkDirection();
        contador++;
        if(contador==400 && !charging){
            targetX = game.getPlayer().getX();
            targetY = game.getPlayer().getY();
            charging=true;
            contador=0;
        }else{
            if(contador==100 && charging){
                charging = false;
            }
        }
        if(charging){
            charge(targetX, targetY);
        }else{
            checkDirection();
        }        
        collider.setX(getX()+getWidth()/2);
        collider.setY(getY()+getHeight()/2);
    }
    
    public void collisionJump(Item item) {
        if(getX()<item.getX())speed-=10;
        else speed+=10;
        setX(getX()+speed);
        
        if(getY()<item.getY())speed-=10;
        else speed+=10;
        setY(getY()+speed);
        
        speed=2;
    }

    @Override
    public void render(Graphics g) {    
       g.drawImage(actualAnimation.getCurrentFrame(),getX(),getY(),getWidth(),getHeight(),null);
       
       g.setColor(Color.red);
       g.fillRect(200, game.getHeight()-100, life, 25);
    }
}

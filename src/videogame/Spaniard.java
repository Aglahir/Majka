
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
public class Spaniard extends Item{

    private Game game;                  // store game
    private Animation actualAnimation;
    private int speedX,speedY;
    private int maxSpeed;
    private int direction;              // 0-Iddle 1-Left 2-Up 3-Right 4-Down
    private int life;
    private Collider collider;
    /**
     * 
     * @param x the x position of the spaniard
     * @param y the y position of the spaniard
     * @param width the width of the spaniard
     * @param height the height of the spaniard
     * @param game the game
     */
    public Spaniard(int x, int y, int width, int height, Game game){
        super(x, y, width, height);
        this.game = game;
        this.actualAnimation = Assets.spaniardRBasic;
        this.speedX = 0;
        this.speedY = 0;
        this.direction = 2;
        this.life = 100;
        this.maxSpeed = 6;
        this.collider = new Collider(getX()+getWidth()/2,getY()+getHeight()/2,getWidth()/2);
    }
    
    /**
     * Checks where is it moving to
     */
    private void checkDirection(){
        if(getY()<=game.getY0()){
            speedY=0;
            setY(game.getY0()+1);
        }
        else if(getY()>=game.getYf()){
            speedY=0;
            setY(game.getYf()-1);
        }
            
        if(getX()<=game.getX0()){
            speedX = 0;
            setX(game.getX0()+1);
        }else if(getX()>=game.getXf()){
            speedX = 0;
            setX(game.getXf()-1);
        }
        
        direction=0;
        if(getX()>game.getPlayer().getX()+game.getPlayer().getWidth())direction=1;
        else if(getX()+getWidth()<game.getPlayer().getX())direction=3;
        
        switch(direction){
            case 1: 
                    if(speedX>maxSpeed*-1)speedX--;
                    actualAnimation = Assets.spaniardLBasic;
                    break;
            case 3: 
                    if(speedX<maxSpeed)speedX++;
                    actualAnimation = Assets.spaniardRBasic;
                    break;
            default:
                    speedX=0;
                    break;
        }
        
        if(getY()>game.getPlayer().getY()+2)direction=2;
        else if(getY()+2<game.getPlayer().getY())direction=4;
        else direction=5;
        
        switch(direction){
            case 2: 
                    if(speedY>maxSpeed*-1)speedY--;
                    actualAnimation = Assets.spaniardUBasic;
                    break;
            case 4: 
                    if(speedY<maxSpeed)speedY++;
                    actualAnimation = Assets.spaniardDBasic;
                    break;
            case 5: 
                    speedY=0;
        }
        setX(getX()+speedX);
        setY(getY()+speedY);
        
        if(direction!=0 || direction!=5)
            actualAnimation.tick();
    }
    
    public Collider getCollider(){
        return this.collider;
    }
    
    public boolean checkCollision(Collider col){
        return collider.checkCollision(col);
    }
    
    public boolean hurt(int damage){
        double tmp1 = Math.random()*50-25,
               tmp2 = Math.random()*3 +1,
               tmp3 = Math.random()*50-25;
        game.createParticle(new Popup(getX()+(int)tmp1,getY()+(int)tmp3,10+(int)tmp1,10+(int)tmp1,(int)tmp2,100,9));
        life-=damage;
        return life<=0;
    }
    
    public void collisionJump(Item item) {
        speedX*=-3;
        speedY*=-3;
        
        if(getX()<item.getX())speedX--;
        else speedX++;
        
        if(getY()<item.getY())speedY--;
        else speedY++;
    }
    
    @Override
    public void tick() {
        
        checkDirection();
        collider.setX(getX()+getWidth()/2);
        collider.setY(getY()+getHeight()/2);
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(actualAnimation.getCurrentFrame(),getX(),getY(),getWidth(),getHeight(),null);
       
       if(life>70)g.setColor(Color.green);
       else if(life>40)g.setColor(Color.yellow);
       else g.setColor(Color.red);
       
       g.fillRect(getX(), getY()-15, life, 10);
    }
}

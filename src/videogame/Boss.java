
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
    private int cont;
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
        this.actualAnimation = Assets.spaniardRBasic;
        this.speed = 3;
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
                    actualAnimation = Assets.spaniardLBasic;
                    break;
            case 3: 
                    setX(getX()+speed);
                    actualAnimation = Assets.spaniardRBasic;
                    break;
        }
        
        if(getY()>game.getPlayer().getY()+2)direction=2;
        else if(getY()+2<game.getPlayer().getY() && getY()+200<=game.getYf())direction=4;
        
        switch(direction){
            case 2: 
                    setY(getY()-speed);
                    actualAnimation = Assets.spaniardUBasic;
                    break;
            case 4: 
                    setY(getY()+speed);
                    actualAnimation = Assets.spaniardDBasic;
                    break;
        }        
        actualAnimation.tick();
    }
    
    public boolean hurt(int damage){
        double tmp1 = Math.random()*50-25,
               tmp2 = Math.random()*3 +1,
               tmp3 = Math.random()*50-25;
        game.createParticle(new Popup(getX()+(int)tmp1,getY()+(int)tmp3,10+(int)tmp1,10+(int)tmp1,(int)tmp2,100,9));
        life-=damage;
        return life<=0;
    }
    
    private void charge(){
        int targetX = game.getPlayer().getX();
        int targetY = game.getPlayer().getY();
        
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
        if(!charging){
            checkDirection();
            cont++;
        }else{
            charge();
            cont=0;
        }
        collider.setX(getX()+getWidth()/2);
        collider.setY(getY()+getHeight()/2);
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(actualAnimation.getCurrentFrame(),getX(),getY(),getWidth(),getHeight(),null);
       if(life>(life*.7))g.setColor(Color.green);
       else if(life>(life*.4))g.setColor(Color.yellow);
       else g.setColor(Color.red);
       
       g.fillRect(getX(), getY()-15, life, 10);
    }
}

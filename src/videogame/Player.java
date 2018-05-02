
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
    private int direction;                  // 0:Iddle, 1:Left, 2:Up, 3:Right, 4:Down
    private int lifes;
    private Collider collider;
    private int timerTemporalAnimation;     // This timer is made to work with any temporal animation needed
    private int playerState;                // 0: Normal, 1: Colliding, 2: Attacking Bow, 3: Attacking Melee, 4: Shield, 5: Dead, 6:Win
    private boolean isMoving;               // flag to know if its moving
    private int shieldLifes;                // shield lifes
    private int maxShieldLifes;             // max shield lifes
    private int timerShield;                // timer for the shield to restart
    private int timerPlayerHit;             
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
        this.direction = 3;
        this.lifes = game.getLifes();
        this.collider = new Collider(getX()+getWidth()/2,getY()+getHeight()/2,getWidth()/2);
        this.timerTemporalAnimation = 60*3;                 // 3 seconds to restart
        this.playerState = 0;                               //Normal
        this.isMoving = true;
        this.maxShieldLifes = 10;
        this.shieldLifes = this.maxShieldLifes;
        this.timerShield = 60*3;
    }
    
    /**
     *    Process actual data from the player to get actual animation that should be running
     * @return Animation
     */
    private Animation getAnimation(){
        
        /*
        *    Directions:
        *    0:Iddle, 1:Left, 2:Up, 3:Right, 4:Down
        *
        *   States:
        *   0: Normal, 1: Colliding, 2: Attacking Bow, 3: Attacking Melee, 4: Shield, 5: Dead, 6: Win
        */
        
        if(playerState==5)return Assets.playerDead;
        if(playerState==6)return Assets.playerWin;
        
        
        switch(this.direction){
            case 0:
                switch(this.playerState){
                    case 4:
                        return Assets.playerRShield;
                }
                break;
            case 1:
                switch(this.playerState){
                    case 0:
                        return Assets.playerLBasic;
                    case 1:
                        return Assets.playerLBasic;
                    case 2:
                        return Assets.playerLBow;
                    case 3:
                        return Assets.playerLBasic;
                    case 4:
                        return Assets.playerLShield;
                }
                break;
            case 2:
                switch(this.playerState){
                    case 0:
                        return Assets.playerUBasic;
                    case 1:
                        return Assets.playerUBasic;
                    case 2:
                        return Assets.playerUBow;
                    case 3:
                        return Assets.playerUBasic;
                    case 4:
                        return Assets.playerUShield;
                }
                break;
            case 3:
                switch(this.playerState){
                    case 0:
                        return Assets.playerRBasic;
                    case 1:
                        return Assets.playerRBasic;
                    case 2:
                        return Assets.playerRBow;
                    case 3:
                        return Assets.playerRBasic;
                    case 4:
                        return Assets.playerRShield;
                }
                break;
            case 4:
                switch(this.playerState){
                    case 0:
                        return Assets.playerDBasic;
                    case 1:
                        return Assets.playerDBasic;
                    case 2:
                        return Assets.playerDBow;
                    case 3:
                        return Assets.playerDBasic;
                    case 4:
                        return Assets.playerDShield;
                }
                break;
        }
        
        return Assets.playerIddleBasic;
    }
    
    /**
     * Checks where is it moving to
     */
    private void checkDirection(){
        
        // Update state on collision or movement
        // 0: Normal, 1: Colliding
        int movementDirection = 0;
        isMoving = true;
        double tmp1 = Math.random()*50-25,tmp2 = Math.random()*5;
        
         //check x moving direction
        if(game.getKeyManager().D && !game.getKeyManager().A){movementDirection=3;}
        else if(!game.getKeyManager().D && game.getKeyManager().A){movementDirection=1;}
        
        switch(movementDirection){
            case 1: 
                    if(getX()>=game.getX0()){if(speedX>maxSpeed*-1)speedX--;}
                    else speedX=0;
                    game.createParticle(new Popup(getX()+getWidth()/2,getY()+getHeight()+(int)tmp1,(int)tmp2,(int)tmp2,4,50,10));
                    break;
            case 3: 
                    if(getX()+getWidth()<=game.getXf()){if(speedX<maxSpeed)speedX++;}
                    else speedX=0;
                    game.createParticle(new Popup(getX()+getWidth()/2,getY()+getHeight()+(int)tmp1,(int)tmp2,(int)tmp2,4,50,10));
                    break;
            
            default:
                    if(speedX>0)speedX-=2;
                    else if(speedX<0)speedX+=2;
                    
                    break;
        }
         //check y moving direction
        if(game.getKeyManager().W && !game.getKeyManager().S){movementDirection=2;}
        else if(!game.getKeyManager().W && game.getKeyManager().S){movementDirection=4;}
        
        switch(movementDirection){
            case 2: 
                    if(getY()>=game.getY0()){if(speedY>maxSpeed*-1)speedY--;}
                    else speedY=0;
                    game.createParticle(new Popup(getX()+(int)tmp1+getWidth()/2,getY()+getHeight()+(int)tmp1,(int)tmp2,(int)tmp2,4,50,10));
                    break;
            case 4: 
                    if(getY()<=game.getYf()-getHeight()){if(speedY<maxSpeed)speedY++;}
                    else speedY=0;
                    game.createParticle(new Popup(getX()+(int)tmp1+getWidth()/2,getY(),(int)tmp2,(int)tmp2,4,50,10));
                    break;
            case 0:
                    if(speedY>0)speedY-=2;
                    else if(speedY<0)speedY+=2;
                    
                    isMoving=false;
                    break;
        }
        
        //Checks collision and updates state if neccessary
        if(getY()<=game.getY0()){
            speedY=0;
            setY(game.getY0()+1);
        }
        else if(getY()+getHeight()>=game.getYf()){
            speedY=0;
            setY(game.getYf()-getHeight()-1);
        }
            
        if(getX()<=game.getX0()){
            speedX = 0;
            setX(game.getX0()+1);
        }else if(getX()+game.getPlayer().getWidth()>=game.getXf()){
            speedX = 0;
            setX(game.getXf()-game.getPlayer().getWidth()-1);
        }

        if(playerState==0 || actualAnimation.animationEnded()){
            playerState=0;
            if(movementDirection!=0)direction=movementDirection;
        }
        
        
        setX(getX()+speedX);
        setY(getY()+speedY);
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
     * This method checks input, execute corresponding actions and then updates states
     */
    private void checkInput() {
        
        
        if(game.getKeyManager().CTRL && game.getLevel()>=5){
            if(shieldLifes>0){
                playerState=4;
            }
        }
        else{
            
            if(game.getKeyManager().Left || game.getKeyManager().Up || game.getKeyManager().Right || game.getKeyManager().Down)
            {
                if(game.getArrowCooldown()==0){
                    if(game.getKeyManager().Left){
                        direction=1;
                        game.addMob(getX(), getY(), getWidth(), getHeight(), 1,1);
                    }
                        
                    if(game.getKeyManager().Up){
                        game.addMob(getX(), getY(), getWidth(), getHeight(), 2,1);
                        direction=2;
                    }
                        
                    if(game.getKeyManager().Right){
                        game.addMob(getX(), getY(), getWidth(), getHeight(), 3,1);
                        direction=3;
                    }
                        
                    if(game.getKeyManager().Down){
                        direction=4;
                        game.addMob(getX(), getY(), getWidth(), getHeight(), 4,1);
                    }
                    
                    Assets.shootPlayerSound.play(); 
                    game.setArrowCooldown(30);

                    if(playerState!=2)playerState=2;
                }
            }
            
            if(game.getKeyManager().Space && game.getPunchCooldown() == 0 && game.getLevel()>=5){
                playerState=3;
                game.addMob(getX()-4, getY()-4, getWidth()+8, getHeight()+8, direction, 2);
                game.setPunchCooldown(30);
            }
        }
    }
    
    /**
     * Takes one life from the player, makes collision animation and checks if still alive
     * @return 
     */
    public boolean hitPlayer(Item item){
        if(playerState!=5 && game.hitAvailable()){
            if(playerState!=4){
                Assets.hitPlayerSound.play();
                lifes--;
                game.setLifes(lifes);
            }else{
                shieldLifes--;
                if(shieldLifes<=0){
                    Assets.hitPlayerSound.play();
                    lifes--;
                    game.setLifes(lifes);
                }
            }
            collisionJump(item);
            if(lifes<=0){
                Assets.loseSound.play();
                playerState=5;
            }
        }
        return lifes>0;
    }
    
    public void win(){
        playerState = 6;
        Assets.winSound.play();
    }
    
    @Override
    public void tick() {
        if(shieldLifes!=maxShieldLifes){
            if(timerShield<=0){
                timerShield=60*3;
                shieldLifes = maxShieldLifes;
            }else timerShield--;
        }
        
        if(playerState!=5 && playerState!=6){
            checkDirection();
            checkInput();  
        }
        
        //Update Animation
        actualAnimation=getAnimation();
        if(isMoving || playerState!=0)
        actualAnimation.tick();
        
        if(playerState==5 || playerState==6){
            if(actualAnimation.animationEnded() && timerTemporalAnimation<=0){
                game.resetGame();
            }else{
                timerTemporalAnimation--;
            }
        }
        collider.setX(getX()+getWidth()/2);
        collider.setY(getY()+getHeight()/2);
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(actualAnimation.getCurrentFrame(),getX(),getY(),getWidth(),getHeight(),null);
       for(int i=0;i<lifes;i++)
            g.drawImage(Assets.heart,20 + 30*i,30,30,30,null);
       
       if(game.getLevel()>5)
       for(int i=0;i<shieldLifes;i++){
           g.setColor(Color.cyan);
           g.fillOval(20 + i*30, 60, 30, 30);
       }
    }
}

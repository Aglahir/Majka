package videogame;

import base.Display;
import base.File;
import base.Item;
import base.KeyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;


public class Game implements Runnable{

    private BufferStrategy bs;                  // to have several buffers when displaying
    private Graphics g;                         // to paint objects
    private Display display;                    // to display in game
    String title;                               // title of the window
    private int width;                          // width of the window
    private int height;                         // height of the window
    private Thread thread;                      // thread to create the game
    private boolean running;                    // to set the game
    private boolean pause;                      // to pause the game
    private boolean manual;                     // to show the manual of the game
    private boolean inDoor;                     // to check collition with door
    private boolean hasBoss;                    // to check the boss of the level
    private boolean tutorial;                   // to show the tutorial at the begining of the game
    private KeyManager keyManager;              // to manage the keyboard
    private MouseManager mouseManager;          // to manage the mouse
    private Player player;                      // first player
    private int fps = 60;                       // set FPS for the thread running
    private int shootTimer = fps/2;             // delay to shoot
    private int spacebarCooldown = 60;          // cooldown for the melee to attack again
    private int level=0;                        // for the level of the map
    private int x0,xf,y0,yf,dx,dy,dt,px,py;     // data for the map limits and the doors
    private int enemies;                        // number of enemies in each map
    private int contador = 0;                   // delay to shoot
    private int scale=100;                      // to set the size of the player and spaniards
    int count=0;                                // timer for the dialogs
    private Map map;               // to read the data of every map
    private BufferedImage mapImage;             // the image of the background in every room
    private ArrayList<Mob> mobs;                // mobs list
    private ArrayList<Popup> popups;            // simple popups list
    private ArrayList<Spaniard> spaniards;      // simple spaniard list
    private Boss boss;                          // the boss of the level
    private STATE state;                        // for manage the states that the game can have
    
    //Horde options
    private boolean hordeMode;                  // state of the horde
    private int hordeTimer;                     // timer to spawn enemies
    private int hordeLevel;                     // 1 max to 5
    private boolean displayNewHorde;            // flag to display horde level or not
    private int displayNewHordeTimer;           // timer to display alert
    private int hordeWinLevel;                  // level required to win horde
    private int hordeCount;                     // countdown for enemies to kill
    private int bossTimer;                      // timer for bosses to appear
    private ArrayList<Boss> bosses;             // bosses arraylist
    private int playerLifesCache = 15;           // Cache for the player to get its lifes
    private int playerHitTimer;
    
    //to init the resources that we will use on the game
    private void init()
    {
        hordeMode = false;
        hordeTimer = fps*3;                                             //Horde spawn enemies each 3 seconds
        hordeCount = 20;                                                //Left of enemies
        hordeLevel = 1;                                                 //Start horde at level 1
        displayNewHordeTimer = fps*2;                                   //Timer to display text
        bossTimer = fps*(4*hordeWinLevel-hordeLevel/2);                 //Delay bosses
        hordeWinLevel = 3;                                              //Horde level
        playerHitTimer = fps/2;
        display = new Display(title,getWidth(),getHeight());            //Display instanciated
        Assets.init();                                                  //Assets loaded
        mobs = new ArrayList<>();
        popups = new ArrayList<>();
        spaniards = new ArrayList<>();
        bosses = new ArrayList<>();
        loadMap();
        Assets.music.play();
        display.getJframe().addKeyListener(keyManager);                 //make the keyManager listens to keys
        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }
    
    /**
     * Method to load every map data
     */
    private void  loadMap(){
        player = null;
        map = null;
        map = new Map(level);
        x0 = map.getX0();
        xf = map.getXf();
        y0 = map.getY0();
        yf = map.getYf();
        dx = map.getDoorx();
        dy = map.getDoory();
        dt = map.getDoosType();
        px = map.getPlayerx();
        py = map.getPlayery();
        scale       = map.getScale();
        enemies     = map.getEnem();
        hasBoss     = map.hasBoss();
        mapImage    = map.getImageMap();
        player      = new Player(px,py,scale,scale,this);
        spaniards.clear();
        //create enemies
        for(int i=0; i<enemies; i++){
            int enemX = ThreadLocalRandom.current().nextInt(x0, xf + 1);
            int enemY = ThreadLocalRandom.current().nextInt(y0, yf + 1);
            spaniards.add(new Spaniard(enemX, enemY,scale, scale,100, this));
        }
        //if the level has boss, create it
        if(hasBoss){
            boss = new Boss(xf, (yf-y0)/2, scale*5, scale*3, 1000, this);
            Assets.snakehiss.play();
        }
        
        if(level==12){
            hordeCount=20;
            hordeLevel=1;
            hordeMode=true;
            Assets.music.play();
        }
        
        if(level==6){
            playerLifesCache = 17;
        }
        
    }
    
    /**
     * To manage the states that the game can have
     */
    private enum STATE{
        menu, game;
    };
    
    /**
    *	to create title, width and height and set the game is still not running
    *	@param	title to set the title of the window
    *	@param	width to set the width of the window
    *	@param	height to set	the height of the window
    */
    public Game(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        pause = false;
        manual = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        state = STATE.menu;
    }
    
    /*
    To run the game
    */
    @Override
    public void run()
    {
        init(); // initialize before run
        double delta = 0;       //delta frames to render
        double timeTick = 1000000000 / fps; // actually the time delay
        long now;
        long lastTime = System.nanoTime();
        
        // Just tick and render when each frame comes to be displayed
        while(running)
        {
                now = System.nanoTime();
                delta += (now - lastTime) / timeTick;
                lastTime = now;
                if(delta >= 1){
                    delta --;
                    getKeyManager().tick();
                    tick();
                    render();
                 }
        }
        //End game
        stop();
    }
    

    /**
     * Is what happens in background for operations to be done
     */
    private void tick()
    {
        if(playerHitTimer>0)playerHitTimer--;
        
        if(hordeMode && !isPaused()){
            for(int i=0;i<bosses.size();i++){
                Boss tmpBoss=bosses.get(i);
                tmpBoss.tick();
                if(getPlayer().checkCollision(tmpBoss.getCollider())){
                    getPlayer().collisionJump(tmpBoss);
                    tmpBoss.collisionJump(getPlayer());
                    if(!getPlayer().hitPlayer(tmpBoss)){
                        hordeMode=false;
                    }
                    if(playerHitTimer<=0)playerHitTimer = fps/2;
                }
            }
            if(bossTimer>0){
                bossTimer--;
            }
            else{
                bossTimer = fps*(4*hordeWinLevel-hordeLevel/2);
                if(hordeLevel>5){
                    bosses.add(new Boss(0, (yf-y0)/2, scale*4, scale*2, 500, this));
                }
                bosses.add(new Boss(xf, (yf-y0)/2, scale*4, scale*2, 500, this));
            }
            if(hordeCount>0){
                if(hordeTimer<=0){
                    hordeTimer=fps*6 - hordeLevel;
                    double xRandom = Math.random()*getWidth(),
                           yRandom = Math.random()*getHeight();

                    spaniards.add(new Spaniard(0,(int)yRandom,50,50,80,this));
                    spaniards.add(new Spaniard((int)xRandom,0,50,50,80,this));
                    spaniards.add(new Spaniard(getWidth(),(int)yRandom,50,50,80,this));
                    spaniards.add(new Spaniard((int)xRandom,getHeight(),50,50,80,this));
                }
                hordeTimer--;
            }else{
                if(hordeLevel==hordeWinLevel)
                {
                    hordeMode=false;
                    getPlayer().win();
                }
                hordeLevel++;
                hordeCount = 20 + 5*hordeLevel;
                displayNewHorde=true;
            }
        }
        
        if(this.getMouseManager().isIzquierdo()){
            if(this.getMouseManager().getX()>=482 && this.getMouseManager().getX() <= 891 && this.getMouseManager().getY()>=310 && this.getMouseManager().getY() <= 399)
                state = STATE.game;
            if(this.getMouseManager().getX()>=482 && this.getMouseManager().getX() <= 891 && this.getMouseManager().getY()>=412 && this.getMouseManager().getY() <= 491)
                startHorde();
            if(this.getMouseManager().getX()>=482 && this.getMouseManager().getX() <= 891 && this.getMouseManager().getY()>=504 && this.getMouseManager().getY() <= 590)
                System.exit(0);
        }
        if(this.getKeyManager().P && state == STATE.game) {
            this.setPause(!this.isPaused());
            this.setManual(false);
        } //for pausing the game
        
        if(this.getKeyManager().M && state == STATE.game) this.setManual(!this.isManual()); // for showing the manual of the game
        
        if(!this.isPaused() && state == STATE.game){ // if the game is not paused then
            contador++;
            if(spacebarCooldown>0)spacebarCooldown --;
            if(shootTimer>0)shootTimer --;

            player.tick();
            if(hasBoss)boss.tick();
            if(player.getX() <= dx+160 && player.getX()>=dx-160 &&player.getY()<=dy+160 && player.getY()>=dy-160 && spaniards.isEmpty() && !hasBoss){
                inDoor=true;
                if(getKeyManager().Enter && contador > 600){                                    
                    level++;
                    loadMap();
                }
            }else{
                inDoor=false;
            }
            
            if(hasBoss){
                if(getPlayer().checkCollision(boss.getCollider())){
                    getPlayer().collisionJump(boss);
                    if(!getPlayer().hitPlayer(boss)){
                        hordeMode=false;
                    }
                    if(playerHitTimer<=0)playerHitTimer = fps/2;
            }}
            
            
            //to get the player and spaniard collition
            for(int i = 0;i<spaniards.size();i++){
                Spaniard tempSpaniard = spaniards.get(i);
                tempSpaniard.tick();
                if(hordeLevel!=hordeWinLevel)
                {
                    if(getPlayer().checkCollision(tempSpaniard.getCollider())){
                        if(!getPlayer().hitPlayer(tempSpaniard)){
                            hordeMode=false;
                        }
                        tempSpaniard.collisionJump(getPlayer());
                        if(playerHitTimer<=0)playerHitTimer = fps/2;
                    }
                }
            }
        
        //to check the arrows collision
        for(int j = 0;j<mobs.size();j++){
            Mob tempMob = mobs.get(j);
            tempMob.tick();
            
            if(hordeMode && tempMob.isAlive()){
                for(int i=0;i<bosses.size();i++){
                    Boss tmpBoss=bosses.get(i);
                    if(tempMob.checkCollision(tmpBoss.getCollider())){
                        tempMob.setDead();
                        if(tmpBoss.hurt(tempMob.getDamage())){
                            hordeCount-=5;
                            bosses.remove(i);
                            i--;
                        }
                    }
                }
            }
            
            if(hasBoss && tempMob.isAlive()){
                if(tempMob.checkCollision(boss.getCollider())){
                    tempMob.setDead();
                    if(boss.hurt(tempMob.getDamage())){
                        hordeCount-=5;
                        boss = null;
                        hasBoss = false;
                        if(level==5)contador=600;
                    }
                }
            }
            if(outOfBounds(tempMob)){
                tempMob.setDead();
            }
            if(tempMob.isDead()){
                mobs.remove(j);
            }
        }
            
       //to get the spaniards and arrows collition
       for(int i = 0;i<spaniards.size();i++){
            Spaniard tempSpaniard = spaniards.get(i);
            
            for(int j = 0;j<mobs.size();j++){
                Mob tempMob = mobs.get(j);
                
                if(tempMob.isAlive()){
                    if(tempSpaniard.checkCollision(tempMob.getCollider())){
                        tempSpaniard.collisionJump(tempMob);
                        if(tempSpaniard.hurt(tempMob.getDamage())){hordeCount--;spaniards.remove(i);break;}
                        tempMob.setDead();
                    }
                }
            }
       }
    }
}
    /**
     * To create popups of hits
     * @param popup 
     */
    public void createParticle(Popup popup){
        popups.add(popup);
    }
    
    /**
     * To check if an item is outside the map
     * @param item
     * @return 
     */
    public boolean outOfBounds(Item item){
        return (item.getX()+10<x0 || item.getX()-10>xf || item.getY()+10<y0 || item.getY()-10>yf);
    }
    
    /**
     * Method to load everything to draw in the display
     */
    private void render()
    {
        //get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /*  if its null, we define one with 3 buffers to display images of the game,
            if not null, then we display every image of the game but after clearing 
            the Rectangle, getting the graphic object from the buffer strategy element.
            show the graphic and dispose it to the trash system
        */
        if(bs == null)
        {
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {
            g = bs.getDrawGraphics();
            
            //Insert code here!!
            
            if(count<=80){
                g.drawImage(Assets.logo,0,0,this.getWidth(),this.getHeight(),null);
                count++;
            } else if(state == STATE.menu){
                g.drawImage(Assets.mainMenu,0,0,this.getWidth(),this.getHeight(),null);
            } else if(isPaused()){ //for displaying the paused image
                g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), null);
                if(spaniards.isEmpty() && !hasBoss){                                    
                    if(dt==2)g.drawImage(Assets.door, dx, dy, -60, 200, null);
                    if(dt==1)g.drawImage(Assets.door1, dx, dy, 200, 60, null);
                    if(dt==3)g.drawImage(Assets.door3, dx, dy, -60, 200, null);
                    if(dt==4)g.drawImage(Assets.door4, dx, dy, 200, 60, null);
                }
                for(int i = 0;i<spaniards.size();i++){
                    spaniards.get(i).render(g);
                }
                player.render(g);
                if(isManual()){
                    g.drawImage(Assets.manual, getWidth()/2 - 300, 100, 600, 400, null);
                }
                else{
                    g.drawImage(Assets.pause, getWidth()/2 - 300, 100, 600, 400, null);
                }
            } else if(!isPaused()){
                g.drawImage(mapImage, 0, 0, getWidth()  , getHeight(), null);                
                
                if(inDoor && contador > 600){
                    g.setColor(Color.white);                    
                    g.drawImage(Assets.enter, player.getX()+player.getWidth()+15, player.getY(),100,140,null);
                }
                
                //spaniards render
                for(int i = 0;i<spaniards.size();i++){
                    spaniards.get(i).render(g);
                }
                //player render
                player.render(g);
                //boss render
                if(spaniards.isEmpty() && !hasBoss){                                    
                    if(dt==2)g.drawImage(Assets.door, dx, dy, -60, 200, null);
                    if(dt==1)g.drawImage(Assets.door1, dx, dy, 200, 60, null);
                    if(dt==3)g.drawImage(Assets.door3, dx, dy, -60, 200, null);
                    if(dt==4)g.drawImage(Assets.door4, dx, dy, 200, 60, null);
                    if(level==5){
                        if(contador <= 900) g.drawImage(Assets.texto2, 0, 2*getHeight()/3, getWidth(), getHeight()/3, null);
                        else if(contador >= 900 && contador <= 1150) g.drawImage(Assets.texto3, 0, 3*getHeight()/4, getWidth(), getHeight()/4, null);
                    }
                }
                if(hasBoss){
                    boss.render(g);
                }
                // POPUPS!!! These are just rendered, no tick is needed
                for(int i=0;i<popups.size();i++){
                    popups.get(i).render(g);
                    if(popups.get(i).hasEnded())popups.remove(i);
                }
                
                //mobs render!!!
                for(int i = 0;i<mobs.size();i++){
                    mobs.get(i).render(g);
                }
                
                if(hordeMode){
                    for(int i=0;i<bosses.size();i++){
                        bosses.get(i).render(g);
                    }
                    g.setColor(Color.yellow);
                    g.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
                    g.drawString("Enemigos restantes : " + hordeCount, getWidth()/2 - 200, 50);
                    if(displayNewHorde){
                        if(displayNewHordeTimer>0){
                            displayNewHordeTimer--;
                            spaniards.clear();
                            g.drawString("Level UP!!!", getWidth()/2 - 50, getHeight()/2);
                            g.drawString(""+hordeLevel, getWidth()/2 - 50, getHeight()/2 + 80);
                        }
                        else{
                            displayNewHordeTimer = fps;
                            displayNewHorde=false;
                        }
                    }
                }
                
                
                //to display initial texts
                if(!hordeMode){
                    if(contador <= 300) g.drawImage(Assets.texto1, 0, 2*getHeight()/3, getWidth(), getHeight()/3, null);
                    else if(contador >= 300 && contador <= 600) g.drawImage(Assets.texto, 0, 2*getHeight()/3, getWidth(), getHeight()/3, null);
                }
            }
            bs.show();
            g.dispose();
        }
        
    }
    /**
     * setting the thread for the game
     */
    public synchronized void start()
    {
        if(!running)
        {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop()
    {
        if(running)
        {
            running = false;
            try
            {
                thread.join();
            }
            catch(InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
    }
    
    
    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Get the key manager
     * @return keyManager
     */
    public KeyManager getKeyManager()
    {
        return keyManager;
    }
  
    /**
     * To get the player of the game
     * @return player
     */
    public Player getPlayer(){
        return this.player;
    }
    
    /**
     * check if game state is pause
     * @return boolean paused
     */
    public boolean isPaused(){
        return pause;
    }
    
    /**
     * set the game state
     * @param pause boolean
     */
    public void setPause(boolean pause){
        this.pause = pause;
    }

    /**
     * To check if the player is reading the manual
     * @return boolean manual
     */
    public boolean isManual() {
        return manual;
    }

    /**
     * To set the game state to reading the manual
     * @param manual boolean
     */
    public void setManual(boolean manual) {
        this.manual = manual;
    }
    
    public MouseManager getMouseManager(){
        return mouseManager;
    }
    
    /**
     * This method will create instances of Mobs depending of what is needed
     * @param x X position
     * @param y Y position
     * @param w Width
     * @param h Height
     * @param direction 1:Left, 2:Up, 3:Right, 4:Down
     * @param type 
     */
    public void addMob(int x, int y, int w, int h, int direction, int type){
        
        mobs.add(new Mob(x,y,w,h,this,direction,type));
    }

    /**
     * get the map x inf limit
     * @return 
     */
    public int getX0() {
        return x0;
    }

    /**
     * get the map x final limit
     * @return 
     */
    public int getXf() {
        return xf;
    }

    /**
     * get the map y inf limit
     * @return 
     */
    public int getY0() {
        return y0;
    }

    /**
     * get the map final y limit
     * @return 
     */
    public int getYf() {
        return yf;
    }

    /**
     * get the map door x coord
     * @return 
     */
    public int getDx() {
        return dx;
    }

    /**
     * get the map door y coord
     * @return 
     */
    public int getDy() {
        return dy;
    }
    
    public int getScale(){
        return scale;
    }
    
    public int getLevel(){
        return level;
    }
    
    public int getPunchCooldown()
    {
        return spacebarCooldown;
    }
    
    public void setPunchCooldown(int cooldown){
        spacebarCooldown=cooldown;
    }
    
    public int getArrowCooldown()
    {
        return shootTimer;
    }
    
    public void setArrowCooldown(int cooldown){
        shootTimer=cooldown;
    }
    
    public void startHorde(){
        level=11;
        hordeWinLevel = 10;
        state = STATE.game;
        contador = 1000;
    }
    
    public void resetGame(){
        level=0;
        spaniards.clear();
        mobs.clear();
        popups.clear();
        bosses.clear();
        hordeMode=false;
        setLifes(10);
        loadMap();
    }
    
    public boolean hitAvailable(){
        return playerHitTimer<=0;
    }
    
    public int getLifes(){
        return playerLifesCache;
    }
    
    public void setLifes(int lifes){
        playerLifesCache = lifes;
    }
}
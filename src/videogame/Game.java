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
    private int spacebarCooldown = 30;          //cooldown for the melee to attack again
    private int level=0;                        //for the level of the map
    private int x0,xf,y0,yf,dx,dy,dt,px,py;     //data for the map limits and the doors
    private int enemies;                        //number of enemies in each map
    private int contador = 0;                   // delay to shoot
    int count=0;                                // timer for the dialogs
    private Map map = new Map(0);               //to read the data of every map
    private BufferedImage mapImage;             //the image of the background in every room
    private ArrayList<Mob> mobs;                // mobs list
    private base.SoundClip hurt;                //sound to check player collition
    private base.SoundClip music;               //ambient music
    private ArrayList<Mob> throwableObjects;    // throwable list
    private ArrayList<Popup> popups;            // simple popups list
    private ArrayList<Spaniard> spaniards;      // simple spaniard list
    private Boss boss;                          //the boss of the level
    private STATE state;                        // for manage the states that the game can have
    
    //to init the resources that we will use on the game
    private void init()
    {
        display = new Display(title,getWidth(),getHeight());            //Display instanciated
        Assets.init();                                                  //Assets loaded
        mobs = new ArrayList<>();
        popups = new ArrayList<>();
        spaniards = new ArrayList<>();
        loadMap();
        music  = new base.SoundClip("/sounds/song.mp3", 50, true);
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
        enemies = map.getEnem();
        hasBoss = map.hasBoss();
        mapImage = map.getImageMap();
        player = new Player(px,py,100,100,this);
        spaniards.clear();
        //create enemies
        for(int i=0; i<enemies; i++){
            int enemX = ThreadLocalRandom.current().nextInt(x0, xf + 1);
            int enemY = ThreadLocalRandom.current().nextInt(y0, yf + 1);
            spaniards.add(new Spaniard(enemX, enemY,100, 100,100, this));
        }
        //if the level has boss, create it
        if(hasBoss){
            boss = new Boss((xf-x0)/2, (yf-y0)/2, 300, 300, 2000, this);
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
        //System.out.println("X: "+player.getX()+" Y: "+player.getY());
        if(this.getMouseManager().isIzquierdo()){
            if(this.getMouseManager().getX()>=482 && this.getMouseManager().getX() <= 891 && this.getMouseManager().getY()>=315 && this.getMouseManager().getY() <= 392)
                state = STATE.game;
            if(this.getMouseManager().getX()>=482 && this.getMouseManager().getX() <= 891 && this.getMouseManager().getY()>=520 && this.getMouseManager().getY() <= 593)
                System.exit(0);
        }
        if(this.getKeyManager().P && state == STATE.game) {
            this.setPause(!this.isPaused());
            this.setManual(false);
        } //for pausing the game
        
        if(this.getKeyManager().M && state == STATE.game) this.setManual(!this.isManual()); // for showing the manual of the game
        
        if(!this.isPaused() && state == STATE.game){ // if the game is not paused then
            contador++;
            if(spacebarCooldown == 0){
                if(getKeyManager().Space){
                    spacebarCooldown = 30;
                    double tmp1 = Math.random()*50-25,
                           tmp2 = Math.random()*3 +1,
                           tmp3 = Math.random()*50-25;
                    popups.add(new Popup(getPlayer().getX()+(int)tmp1,getPlayer().getY()+(int)tmp3,10+(int)tmp1,10+(int)tmp1,(int)tmp2,100,9));
                }
            }else spacebarCooldown--;

            if(shootTimer>0)shootTimer --;

            player.tick();
            if(hasBoss)boss.tick();
            if(player.getX() <= dx+160 && player.getX()>=dx-160 &&player.getY()<=dy+160 && player.getY()>=dy-160 && spaniards.isEmpty()){
                inDoor=true;
                if(getKeyManager().Enter && contador > 12){                                    
                    level++;
                    loadMap();
                }
            }else{
                inDoor=false;
            }
       
            //to get the player and spaniard collition
        for(int i = 0;i<spaniards.size();i++){
            Spaniard tempSpaniard = spaniards.get(i);
            tempSpaniard.tick();
            if(getPlayer().checkCollision(tempSpaniard.getCollider())){
                getPlayer().collisionJump(tempSpaniard);
                tempSpaniard.collisionJump(getPlayer());
            }
        }
        
        //to check the arrows collition
        for(int j = 0;j<mobs.size();j++){
            Mob tempMob = mobs.get(j);
            tempMob.tick();
            if(hasBoss && tempMob.isAlive()){
                if(tempMob.getX()>=boss.getX() && tempMob.getX()<=boss.getX()+250 && tempMob.getY()>=boss.getY() && tempMob.getY()<=boss.getY()+300){
                    tempMob.setDead();
                    if(boss.hurt(tempMob.getDamage())){
                        boss = null;
                        hasBoss = false;
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
                        if(tempSpaniard.hurt(tempMob.getDamage())){spaniards.remove(i);break;}
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
                if(spaniards.isEmpty())
                if(dt==2)g.drawImage(Assets.door, dx, dy, -60, 200, null);
                else g.drawImage(Assets.door1, dx, dy, 200, 60, null);
                for(int i = 0;i<spaniards.size();i++){
                    spaniards.get(i).render(g);
                }
                player.render(g);
                if(isManual()){
                    g.drawImage(Assets.manual, 0, 0, width, height, null);
                }
                else{
                    g.drawImage(Assets.pause, 0, 0, width, height, null);
                }
            } else if(!isPaused()){
                g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), null);
                g.drawImage(mapImage, 0, 0, getWidth(), getHeight(), null);
                if(spaniards.isEmpty())
                    if(dt==2)g.drawImage(Assets.door, dx, dy, -60, 200, null);
                    else g.drawImage(Assets.door1, dx, dy, 200, 60, null);
            
                if(inDoor && contador > 600){
                    g.setColor(Color.white);                    
                    g.drawString("Presiona ENTER para usar la puerta", player.getX(), player.getY()+120);
                }
            
                //arrows render!!!
                for(int i = 0;i<mobs.size();i++){
                    mobs.get(i).render(g);
                }

                //spaniards render
                for(int i = 0;i<spaniards.size();i++){
                    spaniards.get(i).render(g);
                }
                //player render
                player.render(g);
                //boss render
                if(hasBoss){
                    boss.render(g);
                }
                // POPUPS!!! These are just rendered, no tick is needed
                for(int i=0;i<popups.size();i++){
                    popups.get(i).render(g);
                    if(popups.get(i).hasEnded())popups.remove(i);
                }
                //to display initial texts
                if(contador <= 300) g.drawImage(Assets.texto1, 0, 2*getHeight()/3, getWidth(), getHeight()/3, null);
                else if(contador >= 300 && contador <= 600) g.drawImage(Assets.texto, 0, 2*getHeight()/3, getWidth(), getHeight()/3, null);
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
        
        if(shootTimer<=0){
            mobs.add(new Mob(x,y,w,h,this,direction,type));
            shootTimer = fps/2;
        }
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
    
    
}
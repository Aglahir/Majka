package videogame;

import base.Display;
import base.File;
import base.Item;
import base.KeyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Game implements Runnable{

    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private KeyManager keyManager;  // to manage the keyboard
    private Player player;          // first player
    private int fps = 60;           // set FPS for the thread running
    private int shootTimer = fps/2;   // delay to shoot
    private ArrayList<Mob> hadoukens;      // simple hadoukens list
    private void init()
    {
        display = new Display(title,getWidth(),getHeight());            //Display instanciated
        Assets.init();                                                  //Assets loaded
        hadoukens = new ArrayList<>();
        player = new Player(getWidth()/2,getHeight()/2,100,100,this);
        
        display.getJframe().addKeyListener(keyManager);                 //make the keyManager listens to keys
    }
    
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
        keyManager = new KeyManager();
        
    }
    
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
                if(delta >= 1)
                {
                    delta --;
                    
                    getKeyManager().tick();                    
                    if(!getKeyManager().P)
                    {
                         tick();
                    }
                    else
                    {
                        /*
                        if(getKeyManager().isS())
                        {
                            File.saveFile(this);
                        }
                        if(getKeyManager().isL())
                        {
                            File.loadFile(this);
                        }
                        */
                    }
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
       if(shootTimer>0)shootTimer --;
       player.tick();
       for(int i = 0;i<hadoukens.size();i++){
           hadoukens.get(i).tick();
           if(outOfBounds(hadoukens.get(i))){
               hadoukens.remove(i);
               i--;
           }
       }
    }
    
    public boolean outOfBounds(Item item){
        return (item.getX()<0 || item.getX()+item.getWidth()>getWidth() || item.getY()<0 || item.getY()+item.getHeight()>getHeight());
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
            
            g.drawImage(Assets.map, 0, 0, getWidth(), getHeight(), null);
            player.render(g);
            for(int i = 0;i<hadoukens.size();i++){
                hadoukens.get(i).render(g);
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
  
    public Player getPlayer(){
        return this.player;
    }
    
    public void addHadouken(int x, int y, int w, int h, int direction){
        if(shootTimer<=0){
            hadoukens.add(new Mob(x,y,w,h,this,direction));
            shootTimer = 0;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author Rbtote
 */
public class Map {
    //All map data
    private int x0,xf,y0,yf,doorx,doory,playerx,playery,enem,doortype;
    private boolean boss;
    int index;                                                                      //the index (level) of the map
    private BufferedImage image;                                                    //the map image
    private static int playerX[]={611,660,50,10,385,660};                           //check the player x position
    private static int playerY[]={336,608,336,608,336,608};                         //check the player x position
    private static int x0s[]={27,27,27,8,385,27};                                   //check the map x start
    private static int xfs[]={1235,1235,1235,240,1235,1235};                        //check the map x limit
    private static int y0s[]={8,8,152,8,8,8};                                       //check the map y start
    private static int yfs[]={608,608,400,608,608,608};                             //check the map y limit
    private static int doorsx[]={611,1370,1370,355,611,-500};                       //check the door y position
    private static int doorsy[]={5,300,260,330,5,-500};                             //check the door y position
    private static int enemies[]={0,1,0,2,3,1};                                     //to check the number of enemies in the map
    private static int doortypes[]={1,2,2,2,1,1};                                   //to check if the door is vertical or horizontal 1 and 2 respectively
    private static boolean bosses[]={false,false,false,false,false,true};           //to check if the map has boss or not
    
    /**
     * constructor of the map
     * @param index 
     */
    public Map(int index){        
        this.index      = index;        
        this.x0         = x0s[index];
        this.xf         = xfs[index];
        this.y0         = y0s[index];
        this.yf         = yfs[index];
        this.doorx      = doorsx[index];
        this.doory      = doorsy[index];
        this.playerx    = playerX[index];
        this.playery    = playerY[index];
        this.enem       = enemies[index];
        this.doortype   = doortypes[index];
        this.boss       = bosses[index];
        this.image      = videogame.Assets.getMapImage(index);
    }
    
    /**
     * 
     * @return image of the map
     */
    public BufferedImage getImageMap() {
        return image;
    }

    /**
     * 
     * @return map x inf limit
     */
    public int getX0() {
        return x0;
    }

    /**
     * 
     * @return map x sup limit
     */
    public int getXf() {
        return xf;
    }

    /**
     * 
     * @return map inf y limit
     */
    public int getY0() {
        return y0;
    }

    /**
     * 
     * @return map sup y limit
     */
    public int getYf() {
        return yf;
    }

    /**
     * 
     * @return map x door coord
     */
    public int getDoorx() {
        return doorx;
    }

    /**
     * 
     * @return map y door coord
     */
    public int getDoory() {
        return doory;
    }

    /**
     * 
     * @return player initial x coord
     */
    public int getPlayerx() {
        return playerx;
    }

    /**
     * 
     * @return player initial y coord
     */
    public int getPlayery() {
        return playery;
    }

    /**
     * 
     * @return number of enemies in the map
     */
    public int getEnem() {
        return enem;
    }

    /**
     * 
     * @return if the map has a boss
     */
    public boolean hasBoss() {
        return boss;
    }
    
    /**
     * 
     * @return door type in the map
     */
    public int getDoosType(){
        return doortype;
    }
    

}

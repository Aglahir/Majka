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
    private int x0,xf,y0,yf,doorx,doory,playerx,playery,enem,doortype,scale;
    private boolean boss;
    int index;                                                                                                  //the index (level) of the map
    private BufferedImage image;                                                                                //the map image
    private static int playerX[]={611,660,50,10,385,660,10,10,500,150,50,611};                                  //check the player x position
    private static int playerY[]={336,608,336,608,336,608,608,280,608,608,50,336};                              //check the player x position
    private static int x0s[]={27,27,27,8,385,27,27,27,27,27,27,27};                                             //check the map x start
    private static int xfs[]={1320,1320,1320,335,1320,1320,1320,1320,700,410,1320,1320};                        //check the map x limit
    private static int y0s[]={60,8,152,8,8,8,8,335,8,8,8,50};                                                   //check the map y start
    private static int yfs[]={715,715,510,715,715,715,715,715,715,715,275,715};                                 //check the map y limit
    private static int doorsx[]={611,1370,1370,355,611,1370,1365,500,150,445,1360,1360};                        //check the door y position
    private static int doorsy[]={5,300,260,330,5,260,280,307,5,50,50,300};                                      //check the door y position
    private static int enemies[]={0,1,0,2,3,1,6,2,3,3,1,4};                                                     //to check the number of enemies in the map
    private static int doortypes[]={1,2,2,2,1,2,3,4,4,3,3,3};                                                   //to check if the door is vertical or horizontal 1 and 2 respectively
    private static int scales[]={100,100,100,100,100,90,60,60,60,50,50,50};                                     //to set the size of the player and spaniards in the map
    private static boolean bosses[]={false,false,false,false,false,true,false,false,false,false,false,true};    //to check if the map has boss or not
    
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
        this.scale      = scales[index];
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

    public int getScale() {
        return scale;
    }
    

}

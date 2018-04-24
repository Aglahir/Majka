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
    private int x0,xf,y0,yf,doorx,doory,playerx,playery,enem;
    int index;
    private BufferedImage image;
    private static int playerX[]={611,660};
    private static int playerY[]={336,600};
    private static int x0s[]={27,27};
    private static int xfs[]={1235,1235};
    private static int y0s[]={8,8};
    private static int yfs[]={608,608};
    private static int doorsx[]={611,1370};
    private static int doorsy[]={5,300};
    private static int enemies[]={0,12};
    
    public Map(int index){        
        this.index = index;        
        this.x0 = x0s[index];
        this.xf = xfs[index];
        this.y0 = y0s[index];
        this.yf = yfs[index];
        this.doorx = doorsx[index];
        this.doory = doorsy[index];
        this.playerx = playerX[index];
        this.playery = playerY[index];
        this.enem = enemies[index];
        this.image = videogame.Assets.getMapImage(index);
    }
    
    public BufferedImage getImageMap() {
        return image;
    }

    public int getX0() {
        return x0;
    }

    public int getXf() {
        return xf;
    }

    public int getY0() {
        return y0;
    }

    public int getYf() {
        return yf;
    }

    public int getDoorx() {
        return doorx;
    }

    public int getDoory() {
        return doory;
    }

    public int getPlayerx() {
        return playerx;
    }

    public int getPlayery() {
        return playery;
    }

    public int getEnem() {
        return enem;
    }
    

}

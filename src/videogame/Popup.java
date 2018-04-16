/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import base.Item;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aglahir
 */
public class Popup extends Item{

    private int type;       //1: BOOM 2: POW  3: BAM
    private int frames = 1;
    private int maxFrames;  //limit of frames and size
    private int step;       //how fast is it going to reach maxFrames(size)
    private boolean growing = true;
    private BufferedImage image;
    
    /**
     * 
     * @param x X position
     * @param y Y position
     * @param width Initial Width
     * @param height Initial Height
     * @param type 1: BOOM, 2: POW, 3: BAM, 4: Cloud Particles
     * @param frames Unit of length for this image to be growing (max size depends on it too)
     * @param step How fast is it going to reach the frames max size, default 1, up to 10
     */
    public Popup(int x, int y, int width, int height, int type, int frames, int step) {
        super(x, y, width, height);
        this.type = type;
        this.maxFrames = frames;
        this.step = step;
        switch(type){
            case 1: image = Assets.boom;
                    break;
            case 2: image = Assets.pow;
                    break;
            case 3: image = Assets.bam;
                    break;
            case 4: image = Assets.cloudParticles;
                    break;
        }
    }
    
    public boolean hasEnded(){
        return frames==0;
    }
    
    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        if(growing){
            if(frames<maxFrames){
                frames+=step;
            }else{
                growing=false;
            }
        }else{
            if(frames>0)
                frames-=step;
            else 
                frames = 0;
        }
        
        g.drawImage(image, getX()-frames/2, getY()-frames/2, getWidth()+frames,getHeight()+frames,null);
        
    }
    
}

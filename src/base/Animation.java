
package base;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Aglahir Jiménez Flórez
 */
public class Animation {
    private int speed;                  //speed of each frame
    private int index;                  //get next frame index 
    private long  lastTime;               //save previous frame time
    private long  timer;                  //accumulate time of animation
    private BufferedImage[] frames;     //store every image
    
    
    /**
     * Creating animation with all frames and speed of each one
     * @param frames array of images
     * @param speed int value for every frame
     */
    public Animation(BufferedImage[] frames, int speed)
    {
        this.frames = frames;       //storing frames
        this.speed = speed;         //storing speed
        index = 0;                  //initializing index
        timer = 0;                  //initializing timer
        lastTime = System.currentTimeMillis();  //getting initial time
    }
    
    /**
     * Getting current frame to paint
     * @return BufferedImage to paint next frame
     */
    public BufferedImage getCurrentFrame()
    {
        return frames[index];
    }
    
    public void tick()
    {
        // accumulating from previous tick to this one
        timer += System.currentTimeMillis() - lastTime;
        
        //update lasttime to current time
        lastTime = System.currentTimeMillis();
        
        //check timer to increase index
        if(timer > speed)
        {
            index++;
            timer = 0;
            //check index no to get out of bounds
            if(index >= frames.length)
            {
                index = 0;
            }
        }
    }
}

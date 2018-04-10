package videogame;

import base.ImageLoader;
import base.SoundClip;
import base.SpriteSheet;
import java.awt.image.BufferedImage;

/**
 *
 * @author Hack'n matata
 */
public class Assets {
    
    public static BufferedImage map,arrow;
    public static BufferedImage up[],down[],left[],right[];
    public static BufferedImage upM[],downM[],leftM[],rightM[];
    public static BufferedImage upB[],downB[],leftB[],rightB[];
    public static SoundClip music;
    public static SoundClip shoot, hit, ouchEnemy, ouchPlayer;
    
    /**
     * initializing the assets of the game
     */
    public static void init()
    {
        try{
            map = ImageLoader.loadImage("/assets/images/map.png");
            
            SpriteSheet sprites = new SpriteSheet(ImageLoader.loadImage("/assets/images/sprite.png"));
            /*up = new BufferedImage[2];
            for (int i = 0; i < 2; i++) {
                floating[i] = ss.crop(i * 25, 0, 25, 25);
            }*/
            
            music = new SoundClip("/assets/sounds/music.wav", -3f, true);
            shoot = new SoundClip("/assets/sounds/music.wav", 5f, false);
            hit = new SoundClip("/assets/sounds/music.wav", 5f, false);
            ouchEnemy = new SoundClip("/assets/sounds/music.wav", 5f, false);
            ouchPlayer = new SoundClip("/assets/sounds/music.wav", 5f, false);
        }
        catch(Exception e)
        {
            System.out.println("Exception: " + e.toString());
        }
        
    }
    
}
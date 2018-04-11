package videogame;

import base.Animation;
import base.ImageLoader;
import base.SoundClip;
import base.SpriteSheet;
import java.awt.image.BufferedImage;

/**
 *
 * @author Hack'n matata
 */
public class Assets {
    
    public static Animation playerLBasic,playerRBasic;
    public static BufferedImage map, minimap;
    public static SoundClip music;
    public static SoundClip shoot, hit, ouchEnemy, ouchPlayer;
    
    /**
     * initializing the assets of the game
     */
    public static void init()
    {
        try{
            BufferedImage up[],down[],left[],right[];
            BufferedImage upM[],downM[],leftM[],rightM[];
            BufferedImage upB[],downB[],leftB[],rightB[];
            
            map = ImageLoader.loadImage("/images/singleMap.png");
            minimap = ImageLoader.loadImage("/images/fullMap.png");
            
            SpriteSheet sprites1 = new SpriteSheet(ImageLoader.loadImage("/images/playerLeftBasic.png"));
            SpriteSheet sprites2 = new SpriteSheet(ImageLoader.loadImage("/images/playerRightBasic.png"));
            left = new BufferedImage[8];
            right = new BufferedImage[8];
            
            // Height: 91px  Width: 730px   Sprites: 8
            for(int i = 0; i<8;i++){
                left[i] = sprites1.crop(91*i,0,91,91);
                right[i] = sprites2.crop(91*i,0,91,91);
            }
            
            playerLBasic = new Animation(left,1);
            playerRBasic = new Animation(right,1);
            /*            
            
            music = new SoundClip("/assets/sounds/music.wav", -3f, true);
            shoot = new SoundClip("/assets/sounds/music.wav", 5f, false);
            hit = new SoundClip("/assets/sounds/music.wav", 5f, false);
            ouchEnemy = new SoundClip("/assets/sounds/music.wav", 5f, false);
            ouchPlayer = new SoundClip("/assets/sounds/music.wav", 5f, false);
            }*/
        }
        catch(Exception e)
        {
            System.out.println("Exception: " + e.toString());
        }
        
    }
    
}
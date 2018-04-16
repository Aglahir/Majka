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
    
    public static Animation playerLBasic,playerRBasic,playerUBasic,playerDBasic, playerIddleBasic;
    public static Animation spaniardLBasic,spaniardRBasic,spaniardUBasic,spaniardDBasic, spaniardIddleBasic;
    public static BufferedImage map, minimap, boom, pow, bam, cloudParticles;
    public static SoundClip music;
    public static SoundClip shoot, hit, ouchEnemy, ouchPlayer;
    
    /**
     * initializing the assets of the game
     */
    public static void init()
    {
        try{
            BufferedImage up[],down[],left[],right[],iddleBasic[];
            BufferedImage upS[],downS[],leftS[],rightS[], iddleBasicS[];
            
            boom = ImageLoader.loadImage("/images/boom.png");
            pow = ImageLoader.loadImage("/images/pow.png");
            bam = ImageLoader.loadImage("/images/bam.png");
            cloudParticles = ImageLoader.loadImage("/images/cloud_particles.png");
            
            SpriteSheet sprites1 = new SpriteSheet(ImageLoader.loadImage("/images/playerLeftBasic.png"));
            SpriteSheet sprites2 = new SpriteSheet(ImageLoader.loadImage("/images/playerRightBasic.png"));
            SpriteSheet sprites3 = new SpriteSheet(ImageLoader.loadImage("/images/playerIddleBasic.png"));
            
            map = ImageLoader.loadImage("/images/singleMap.png");
            minimap = ImageLoader.loadImage("/images/fullMap.png");
            
            left = new BufferedImage[8];
            right = new BufferedImage[8];
            
            up = new BufferedImage[4];
            down = new BufferedImage[4];
            
            iddleBasic = new BufferedImage[7];
            
            // Height: 91px  Width: 730px   Sprites: 8
            for(int i = 0; i<7;i++){
                left[i] = sprites1.crop(91*i,0,91,91);
                right[i] = sprites2.crop(91*i,0,91,91);
                iddleBasic[i] = sprites3.crop(91*i,0,91,91);
            }
            left[7] = sprites1.crop(91*7,0,91,91);
            right[7] = sprites2.crop(91*7,0,91,91);
            
            playerLBasic = new Animation(left,30);
            playerRBasic = new Animation(right,30);
            playerIddleBasic = new Animation(iddleBasic,30);
            
            spaniardLBasic = new Animation(left,30);
            spaniardRBasic = new Animation(right,30);
            spaniardIddleBasic = new Animation(iddleBasic,30);
            
            sprites1 = new SpriteSheet(ImageLoader.loadImage("/images/PlayerUpBasic.png"));
            sprites2 = new SpriteSheet(ImageLoader.loadImage("/images/playerDownBasic.png"));
            
            
            // Height: 91px  Width: 364px   Sprites: 4
            for(int i = 0; i<4;i++){
                up[i] = sprites1.crop(91*i,0,91,91);
                down[i] = sprites2.crop(91*i,0,91,91);
            }
            
            playerUBasic = new Animation(up,70);
            playerDBasic = new Animation(down,70);
            
            spaniardUBasic = new Animation(up,70);
            spaniardDBasic = new Animation(down,70);
        }
        catch(Exception e)
        {
            System.out.println("Exception: " + e.toString());
        }
        
    }
    
}
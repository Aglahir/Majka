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
    public static Animation spaniardLBasic,spaniardRBasic,spaniardUBasic,spaniardDBasic, spaniardIddleBasic,bossLeft,bossRight;
    public static BufferedImage map, minimap, boom, pow, bam, cloudParticles, pause, mainMenu, manual,door,door1,door4,door3, texto, texto1,texto2, logo;
    public static SoundClip music;
    public static SoundClip shoot, hit, ouchEnemy, ouchPlayer;
    public static Animation arrowUp,arrowLeft,arrowRight,arrowDown;
    
    public static BufferedImage getMapImage(int index){
        return ImageLoader.loadImage("/images/Map"+index+".png");
    }
    
    /**
     * initializing the assets of the game
     */
    public static void init()
    {
        try{
            BufferedImage up[],down[],left[],right[],iddleBasic[];
            BufferedImage arrowUpImg[],arrowLeftImg[],arrowRightImg[],arrowDownImg[];
            
            boom = ImageLoader.loadImage("/images/boom.png");
            pow = ImageLoader.loadImage("/images/pow.png");
            bam = ImageLoader.loadImage("/images/bam.png");
            cloudParticles = ImageLoader.loadImage("/images/cloud_particles.png");
            pause = ImageLoader.loadImage("/images/Pausa1.png");
            mainMenu = ImageLoader.loadImage("/images/MainMenu1.png");
            manual = ImageLoader.loadImage("/images/tutorial.png");
            door = ImageLoader.loadImage("/images/door.png");
            door1 = ImageLoader.loadImage("/images/door1.png");
            door3 = ImageLoader.loadImage("/images/door3.png");
            door4 = ImageLoader.loadImage("/images/door4.png");
            texto = ImageLoader.loadImage("/images/texto.png");
            texto1 = ImageLoader.loadImage("/images/texto1.png");
            logo = ImageLoader.loadImage("/images/mj.png");
            
            arrowUpImg = new BufferedImage[5];
            arrowLeftImg = new BufferedImage[5];
            arrowRightImg = new BufferedImage[5];
            arrowDownImg = new BufferedImage[5];
            
            SpriteSheet sprites1 = new SpriteSheet(ImageLoader.loadImage("/images/arrowUp.png"));
            SpriteSheet sprites2 = new SpriteSheet(ImageLoader.loadImage("/images/arrowLeft.png"));
            SpriteSheet sprites3 = new SpriteSheet(ImageLoader.loadImage("/images/arrowRight.png"));
            SpriteSheet sprites4 = new SpriteSheet(ImageLoader.loadImage("/images/arrowDown.png"));
           
            
            
            for(int i = 0;i<5;i++){
                arrowUpImg[i] = sprites1.crop(122*i, 0, 122, 160);
                arrowLeftImg[i] = sprites2.crop(122*i, 0, 122, 160);
                arrowRightImg[i] = sprites3.crop(122*i, 0, 122, 160);
                arrowDownImg[i] = sprites4.crop(122*i, 0, 122, 160);
            }
            

            arrowUp = new Animation(arrowUpImg,70);
            arrowLeft = new Animation(arrowLeftImg,70);
            arrowRight = new Animation(arrowRightImg,70);
            arrowDown = new Animation(arrowDownImg,70);
            
            map = ImageLoader.loadImage("/images/singleMap.png");
            minimap = ImageLoader.loadImage("/images/fullMap.png");
            
            sprites1 = new SpriteSheet(ImageLoader.loadImage("/images/playerLeftBasic.png"));
            sprites2 = new SpriteSheet(ImageLoader.loadImage("/images/playerRightBasic.png"));
            sprites3 = new SpriteSheet(ImageLoader.loadImage("/images/playerIddleBasic.png"));
            
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
            
            sprites1 = new SpriteSheet(ImageLoader.loadImage("/images/playerUpBasic.png"));
            sprites2 = new SpriteSheet(ImageLoader.loadImage("/images/playerDownBasic.png"));
            
            
            // Height: 91px  Width: 364px   Sprites: 4
            for(int i = 0; i<4;i++){
                up[i] = sprites1.crop(91*i,0,91,91);
                down[i] = sprites2.crop(91*i,0,91,91);
            }
            
            playerUBasic = new Animation(up,70);
            playerDBasic = new Animation(down,70);
            
            left = new BufferedImage[12];
            up = new BufferedImage[4];
            right = new BufferedImage[12];
            down = new BufferedImage[4];
            
            sprites1 = new SpriteSheet(ImageLoader.loadImage("/images/spaniardBasicUp.png"));
            sprites2 = new SpriteSheet(ImageLoader.loadImage("/images/spaniardBasicLeft.png"));
            sprites3 = new SpriteSheet(ImageLoader.loadImage("/images/spaniardBasicRight.png"));
            sprites4 = new SpriteSheet(ImageLoader.loadImage("/images/spaniardBasicDown.png"));
            
            //4 columns. 32x32
            for(int i=0;i<4;i++){
                up[i]=sprites1.crop(32*i, 0, 32, 32);
                down[i]=sprites4.crop(32*i, 0, 32, 32);
                left[i]=sprites2.crop(32*i, 0, 32, 32);
                right[i]=sprites3.crop(32*i, 0, 32, 32);
            }
            
            //the remaining columns 12-4=8 columns
            for(int i=4;i<12;i++){
                left[i]=sprites2.crop(32*i, 0, 32, 32);
                right[i]=sprites3.crop(32*i, 0, 32, 32);
            }
            
            
            spaniardUBasic = new Animation(up,70);
            spaniardDBasic = new Animation(down,70);
            spaniardLBasic = new Animation(left,30);
            spaniardRBasic = new Animation(right,30);
            
            
            left = new BufferedImage[8];
            right = new BufferedImage[8];
            
            sprites1 = new SpriteSheet(ImageLoader.loadImage("/images/snakeBossL.png"));
            sprites2 = new SpriteSheet(ImageLoader.loadImage("/images/snakeBossR.png"));
            
            for(int i=0;i<8;i++){
                left[i]=sprites1.crop(64*i, 0, 64, 32);
                right[i]=sprites2.crop(64*i, 0, 64, 32);
            }
            
            bossLeft  = new Animation(left, 60);
            bossRight = new Animation(right, 60);
        }
        catch(Exception e)
        {
            System.out.println("Exception: " + e.toString());
        }
        
    }
    
}
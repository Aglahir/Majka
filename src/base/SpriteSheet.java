
package base;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Aglahir Jiménez Flórez
 */
public class SpriteSheet {

    private BufferedImage sheet;        //to store spritesheet
    
    /**
     * Create new spritesheet
     * @param sheet the <code>image</code> with the sprites
     */
    public SpriteSheet(BufferedImage sheet)
    {
        this.sheet = sheet;
    }
    
    public BufferedImage crop(int x, int y, int width, int height){
        return sheet.getSubimage(x, y, width, height);
    }
}

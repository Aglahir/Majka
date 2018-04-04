package base;

import java.awt.Graphics;

public abstract class Item {
    protected int x;        //to store x position
    protected int y;        //to store y position
    protected int width;    //to store width
    protected int height;   //to store height
    
    /**
     * Set the initial values to create the item
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
     * @param width width of the object
     * @param height height of the object
     */
    public Item(int x,int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * To update positions of the item for every tick
     */
    public abstract void tick();
    
    /**
     * To paint the item
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);

    /**
     * Get x value
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Get y value
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Set x value
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y value
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Get height
     * @return height
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * Get width
     * @return width
     */
    public int getWidth()
    {
        return width;
    }
}

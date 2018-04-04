package base;

import java.awt.Rectangle;

public class Collider{

    private int x;      //x position
    private int y;      //y position
    private int width;  //width of collider
    private int height; //height of collider
    private int radius; //radius of circled collider
    private Rectangle rectangle;
    
    /**
     * Constructor for the rectangular collider
     * @param x X position offset for the collider
     * @param y Y position offset for the collider
     * @param width Width of collider
     * @param height Height of collider
     */
    public Collider(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = 0;
        this.rectangle = new Rectangle(this.x,this.y,this.width,this.height);
    }
    
    /**
     * Constructor for the circled collider
     * @param x X position offset for the collider
     * @param y Y position offset for the collider
     * @param radius Radius of the circle
     */
    public Collider(int x, int y, int radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.height = radius*2;
        this.width = radius*2;
    }
    
    
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
    
    /**
     * Checks collision with another object
     * @param object2 receives another collision object to check collision with
     * @return true if collision, false if not
     */
    public boolean checkCollision(Collider object2)
    {
        if(this.radius > 0 && object2.radius > 0)
        {
            int yDistance = this.getY()-object2.getY();    // distance between y centers
            int xDistance = this.getX()-object2.getX();    // distance between x centers
            int distanceFromCenter = (int)Math.sqrt(Math.pow(xDistance, 2)+Math.pow(yDistance, 2));     //total distance vector (magnitude)

            return(distanceFromCenter <= (this.radius + object2.radius)); // If sum of radius exceed 
           
        }
        else
        {
            return false;
        }
    }
    
    public int getRadius()
    {
        return radius;
    }
    
    public boolean checkTopCollision(Collider object2)
    {
        return ((this.getX() <= object2.getX() && this.getX() + this.getWidth() >= object2.getX()) || (this.getX() <= object2.getX() + object2.getWidth() && this.getX() + this.getWidth() >= object2.getX() + object2.getWidth())) && (this.getY() >= object2.getY() + object2.getHeight() && this.getY() <= object2.getY() + object2.getHeight());
    }
    
    public boolean checkRightCollision(Collider object2)
    {
        return ((this.getX() <= object2.getX()+object2.getHeight() && this.getX() >= object2.getX())&&((this.getY()>=object2.getY() && this.getY()<=object2.getY()+object2.getHeight())||(object2.getY()>=this.getY() && object2.getY()+object2.getHeight()<=this.getY())));
    }
   
}

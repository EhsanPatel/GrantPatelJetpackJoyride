/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Abstract Game Object Class - codes for methods common to all game objects
 */
package grantpateljetpackjoyride;

import java.awt.Graphics;

public abstract class AbstractGameObject implements GameObject{
    //instance variables
    protected int xPos, yPos, height, width;
    
    //class variables
    private int speed;
    
    //default constructor
    public AbstractGameObject(){
        xPos = 0;
        yPos = 0;
        height = 0;
        width = 0;
    }
    
    /**
     * constructs a game object with specified attributes
     * @param xPos the x position
     * @param yPos the y position
     * @param height the height
     * @param width the width
     */
    public AbstractGameObject(int xPos, int yPos, int height, int width){
        this();
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = width;
    }
    
    /**
     * gets the x position of the object
     * @return the x position
     */
    @Override
    public int getXPos(){
        return xPos;
    }
    
    /**
     * sets the x position
     * @param xPos the new x position
     */
    @Override
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    
    /**
     * gets the y position of the object
     * @return the y position
     */
    @Override
    public int getYPos(){
        return yPos;
    }
    
    /**
     * sets the y position
     * @param yPos the new y position
     */
    @Override
    public void setYPos(int yPos){
        this.yPos = yPos;
    }    
    
    /**
     * gets the height of the object
     * @return the height
     */
    @Override
    public int getHeight(){
        return height;
    }
    
    /**
     * sets the height
     * @param h the new height
     */
    @Override
    public void setHeight(int h){
        height = h;
    }
    
    /**
     * gets the width of the object
     * @return the width
     */
    @Override
    public int getWidth(){
        return width;
    }
    
    /**
     * sets the width
     * @param w the new width
     */
    @Override
    public void setWidth(int w){
        width = w;
    }
    
    /**
     * gets the speed of the object
     * @return the speed
     */
    public int getSpeed(){
        return speed;
    }
    
    /**
     * sets the speed
     * @param speed the new speed
     */
    public void setSpeed(int speed){
        this.speed = speed;
    }
    
    
    //draws the images onto the screen
    @Override
    abstract public void draw(MainGUI m, Graphics g); //cannot code for in abstract classes
    
    /**
     * sends attributes of object to a string
     * @return the string
     */
    @Override
    public String toString(){
        String str = "X Position:\t" + xPos + "\n" + 
                "Y Position:\t" + yPos + "\n" + 
                "Height:\t" + height + "\n" + 
                "Width:\t" + width;
        return str;
    }
    
    /**
     * determines if two game objects are the same
     * @param g the second game object
     * @return true if objects are the same, else false
     */
    public boolean equals(AbstractGameObject g){
        return this.xPos == g.getXPos() && this.yPos == g.getYPos() &&
                this.height == g.getHeight() && this.width == g.getWidth();
    }
    
}

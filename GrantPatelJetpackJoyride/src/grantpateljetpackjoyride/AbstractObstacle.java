/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Abstract Obstacle Class - codes for methods common to all obstacles
 */
package grantpateljetpackjoyride;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author ehsan
 */
abstract public class AbstractObstacle extends AbstractGameObject{
    
    //instance variables
    protected String type;
    protected double frame;
    protected boolean bigObstacle;
    
    //default constructor
    public AbstractObstacle(){
        super();
        type = "";
    }
    
    /**
     * constructs abstract obstacle with specified attributes
     * @param xPos the x position
     * @param yPos the y position
     * @param height the height
     * @param width the width
     * @param type the type of obstacle
     */
    public AbstractObstacle(int xPos, int yPos, int height, int width, String type){
        super(xPos, yPos, height, width);
        this.type = type;
        
        //determining if obstacle is big or not (which image set to use)
        if (((int)(Math.random() * 2) + 1) == 1){
            bigObstacle = true;
        } else {
            bigObstacle = false;
        }
        
        bigObstacle = false;
    }
    
    /**
     * gets the type of obstacle
     * @return the type
     */
    public String getType(){
        return type;
    }
    
    /**
     * sets the type of obstacle
     * @param type the new type
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * gets the frame number
     * @return the frame number
     */
    public double getFrame(){
        return frame;
    }
    
    /**
     * sets the frame number
     * @param frame the new frame number
     */
    public void setFrame(double frame){
        this.frame = frame;
    }
    
    /**
     * gets if obstacle is big
     * @return true if it is big, else false
     */
    public boolean getBigObstacle(){
        return bigObstacle;
    }
    
    /**
     * sets the obstacle size
     * @param big if obstacle is big or not
     */
    public void setBigObstacle(boolean big){
        bigObstacle = big;
    }
    
    /**
     * puts attributes of object in a string
     * @return the string
     */
    public String toString(){
        String str = super.toString() + "\nType:\t" + type;
        return str;
    }
    
    /**
     * determines if two abstract obstacles are the same
     * @param a the second object
     * @return true if the same, else false
     */
    public boolean equals(AbstractObstacle a){
        return super.equals(a) && a.getType().equals(this.getType());
    }
    
    /**
     * draws the obstacles to the screen
     * @param m the mainGUI screen
     * @param g the graphics tool
     * @param images the images
     */
    public void draw(MainGUI m, Graphics g, Image[] images){
        //casts the graphics object to the better 2d version
        Graphics2D g2d = (Graphics2D) g;
        
        //Selects which image to draw
        Image imageToDraw = images[(int)frame];
        
        //draws the character on the window at the x,y coordinates
        g2d.drawImage(imageToDraw,xPos,yPos,m);
    }
    
    //methods that cannot be coded for in abstract class
    abstract public void loadImages();
    
    
    
}

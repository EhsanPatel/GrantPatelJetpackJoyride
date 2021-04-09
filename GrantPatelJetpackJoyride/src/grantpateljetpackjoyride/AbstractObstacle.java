/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Abstract Obstacle Class - codes for methods common to all obstacles
 */
package grantpateljetpackjoyride;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JOptionPane;

/**
 *
 * @author ehsan
 */
abstract public class AbstractObstacle extends AbstractGameObject{
    
    //instance variables
    protected String type;
    
    //class variables
    protected static double frame;
    
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
        
        
    }
    
    /**
     * checks for collisions between the player and a horizontal or vertical obstacle
     * @param p the player
     * @return true if they collided, else false
     */
    public boolean notDiagonalOCollisions(Player p){
        //eliminate some obstacles to eliminate processing
        if (Math.abs(this.getXPos() - p.getXPos()) < 500){ //if obstacle is within 500 pixels on either side of the player
            //check if player is in correct y space
            if (p.getYPos() + 15 < this.getYPos() + this.getHeight() - 40 && p.getYPos() - 15 + p.getHeight() > this.getYPos() + 40){
                //check if player is in correct x space
                if (p.getXPos() + 15 < this.getXPos() + this.getWidth() - 40 && p.getXPos() - 15 + p.getWidth() > this.getXPos() + 40){
                    return true;
                }
            }
        }
        return false; //no collision occurred
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
     * determines which frame to draw
     * @param amount the amount the frame changes by each time
     */
    public static void nextFrame(double amount){
        frame += amount;
        if ((int)frame >= 4){
            frame = 0;
        }
    }
    
    /**
     * puts attributes of object in a string
     * @return the string
     */
    @Override
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
    
    
    
    
}

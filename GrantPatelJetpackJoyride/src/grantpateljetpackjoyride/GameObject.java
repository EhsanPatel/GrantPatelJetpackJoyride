/*
Ehsan Patel and Colin Grant
April 2, 2021
Game Object Interface - contains methods common to all game objects
 */
package grantpateljetpackjoyride;

import java.awt.Graphics;

/**
 *
 * @author cogra9807
 */
public interface GameObject {
    //gets the x position
    public int getXPos();
    
    //sets the x position
    public void setXPos(int xPos);
    
    //gets the y position
    public int getYPos();
    
    //sets the y position
    public void setYPos(int yPos);
    
    //gets the height
    public int getHeight();
    
    //sets the height
    public void setHeight(int h);
    
    //gets the width
    public int getWidth();
    
    //sets the width
    public void setWidth(int w);
    
    //loads the images
    public void loadImages();
    
    //draws the images onto the screen
//    public void draw(Graphics g);
    
    //sends attributes of the object to a string
    public String toString();
}

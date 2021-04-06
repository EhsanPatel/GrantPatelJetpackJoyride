/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for horizontal obstacles
 */
package grantpateljetpackjoyride;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author ehsan
 */
public class HorizontalObstacle extends AbstractObstacle{
    
    //class variables
    private static Image[] framesSmall;
    private static Image[] framesMedium;
    
    //default constructor
    public HorizontalObstacle(){
        super();
    }
    
    /**
     * constructs obstacle with specified attributes
     * @param xPos
     * @param yPos
     * @param height
     * @param width
     * @param type 
     */
    public HorizontalObstacle(int xPos, int yPos, int height, int width, String type){
        super(xPos, yPos, height, width, type);
    }
    
    /**
     * loads the image of the obstacle
     */
    public void loadImages(){
        //load the horizontal obstacle frames into an array, resize them to the width and height
        ImageIcon[] iiHObstacle = new ImageIcon[4];
        framesMedium = new Image[iiHObstacle.length];
        
        //for each frame
        for (int i = 0; i < iiHObstacle.length; i++) {
            iiHObstacle[i] = new ImageIcon(getClass().getResource("imageResources/obstacles/medHorizontal/obstacle" + (i + 1) + ".png"));
            framesMedium[i] = iiHObstacle[i].getImage().getScaledInstance(width, height, Image.SCALE_FAST);
        }
        
        //add medium obstacles
    }
    
    /**
     * gets the images of the horizontal obstacles
     * @return the array of images
     */
    public Image[] getFramesMedium(){
        return framesMedium.clone();
    }
    
    /**
     * sets the images of the obstacle
     * @param framesMedium the new array of images
     */
    public void setFramesMedium(Image[] framesMedium){
        HorizontalObstacle.framesMedium = framesMedium;
    }
    
    /**
     * gets the images of the horizontal obstacles
     * @return the array of images
     */
    public Image[] getFramesSmall(){
        return framesSmall.clone();
    }
    
    /**
     * sets the images of the obstacle
     * @param framesSmall the new array of images
     */
    public void setFramesSmall(Image[] framesSmall){
        HorizontalObstacle.framesSmall = framesSmall;
    }
    
    /**
     * draws the obstacle image onto the screen
     */
    public void draw(MainGUI m, Graphics g){
        //determine which size of obstacle to draw
        if (bigObstacle){
            super.draw(m, g, framesMedium);
        } else {
            super.draw(m, g, framesSmall);
        }
        
    }
    
    /**
     * puts attributes of obstacle into string
     * @return the string
     */
    public String toString(){
        return super.toString();
    }
    
    /**
     * determines if two obstacles are the same
     * @param h the second obstacle
     * @return true if the same, else false
     */
    public boolean equals(HorizontalObstacle h){
        return super.equals(h);
    }
    
    /**
     * clones the horizontal obstacle
     * @return the clone
     */
    public HorizontalObstacle clone(){
        return new HorizontalObstacle(xPos, yPos, height, width, type);
    }
}

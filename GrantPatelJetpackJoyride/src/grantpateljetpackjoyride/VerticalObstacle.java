/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for methods common to vertical obstacles
 */
package grantpateljetpackjoyride;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ehsan
 */
public class VerticalObstacle extends AbstractObstacle{
    
    //class variables
    private static Image[] framesSmall;
    private static Image[] framesMedium;
    
    //default constructor
    public VerticalObstacle(){
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
    public VerticalObstacle(int xPos, int yPos, int height, int width, String type){
        super(xPos, yPos, height, width, type);
    }
    
    /**
     * loads the image of the obstacle
     */
    public void loadImages(){
        //load the horizontal obstacle frames into an array, resize them to the width and height
        ImageIcon[] iiHObstacle = new ImageIcon[4];
        framesSmall = new Image[iiHObstacle.length];
        
        //for each frame
        for (int i = 0; i < iiHObstacle.length; i++) {
            iiHObstacle[i] = new ImageIcon(getClass().getResource("imageResources/obstacles/smallVertical/obstacle" + (i + 1) + ".png"));
            framesSmall[i] = iiHObstacle[i].getImage().getScaledInstance(width, height, Image.SCALE_FAST);
        }
        
        //add medium obstacles
    }
    
    /**
     * gets the images of the vertical obstacles
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
        VerticalObstacle.framesMedium = framesMedium;
    }
    
    /**
     * gets the images of the vertical obstacles
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
        VerticalObstacle.framesSmall = framesSmall;
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
     * @param v the second obstacle
     * @return true if the same, else false
     */
    public boolean equals(VerticalObstacle v){
        return super.equals(v);
    }
    
    /**
     * clones the vertical obstacle
     * @return the clone
     */
    public VerticalObstacle clone(){
        return new VerticalObstacle(xPos, yPos, height, width, type);
    }
}

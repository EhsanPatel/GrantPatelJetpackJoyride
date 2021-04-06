/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for diagonal obstacles
 */
package grantpateljetpackjoyride;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ehsan
 */
public class DiagonalObstacle extends AbstractObstacle{
    
    //class variables
    private static ImageIcon[] framesSmall = new ImageIcon[4];
    private static ImageIcon[] framesMedium = new ImageIcon[4];
    
    //instance variables
    private Image[] frames = new Image[4];
    
    //default constructor
    public DiagonalObstacle(){
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
    public DiagonalObstacle(int xPos, int yPos, int height, int width, String type){
        super(xPos, yPos, height, width, type);
        //make height equals width because they will be the same
        height = width;
        //determine if big obstacle, resize the images
        if (height > 500){ //determine which set of images to use
            resizeImages(framesMedium);
        } else {
            resizeImages(framesSmall);
        }
        
        
    }
    
    /**
     * determines if the player has collided with the obstacle
     * @param p the player
     * @return true if it has collided, else false
     */
    public boolean hasCollided(Player p){
        //do this later
        return true; //just so there aren't any errors
    }
    
    /**
     * loads the image of the obstacle
     */
    public void loadImages(){
        //for each frame for small obstacles
        for (int i = 0; i < framesSmall.length; i++) {
            framesSmall[i] = new ImageIcon(getClass().getResource("imageResources/obstacles/smallDiagonal/obstacle" + (i + 1) + ".png"));
            
        }
        
        //add medium obstacles
        //for each frame
        for (int i = 0; i < framesMedium.length; i++) {
            framesMedium[i] = new ImageIcon(getClass().getResource("imageResources/obstacles/mediumDiagonal/obstacle" + (i + 1) + ".png"));
        }
    }
    
    /**
     * resizes images to fit height and width
     * @param images array containing original images
     */
    public void resizeImages(ImageIcon[] images){
        //resize each frame
        for (int i = 0; i < images.length; i++) {
            frames[i] = images[i].getImage().getScaledInstance(width, height, Image.SCALE_FAST);
        }
    }
    
     /**
     * gets the images of the diagonal obstacles
     * @return the array of images
     */
    public Image[] getFrames(){
        return frames.clone();
    }
    
    /**
     * sets the images of the obstacle
     * @param frames the new array of images
     */
    public void setFrames(Image[] frames){
        this.frames = frames;
    }
    
    /**
     * draws the obstacle image onto the screen
     * @param m the screen
     * @param g the graphics component
     */
    public void draw(MainGUI m, Graphics g){
            super.draw(m, g, frames); //draw obstacle

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
     * @param d the second obstacle
     * @return true if the same, else false
     */
    public boolean equals(DiagonalObstacle d){
        return super.equals(d);
    }
    
    /**
     * clones the diagonal obstacle
     * @return the clone
     */
    public DiagonalObstacle clone(){
        return new DiagonalObstacle(xPos, yPos, height, width, type);
    }
}

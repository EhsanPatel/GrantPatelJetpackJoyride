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
    private static ImageIcon[] framesSmall = new ImageIcon[4];
    private static ImageIcon[] framesMedium = new ImageIcon[4];
    private static final int WIDTH = 110;

    //instance variables
    private Image[] frames = new Image[4];
    
    
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

        width = WIDTH; //vertical obstacle will always have same width
        //make either a big or small obstacle
        if ((int)(Math.random()* 2 + 1) == 1){ //determine which set of images to use
            height = 350;
            resizeImages(framesMedium, width, height);
        } else {
            height = 250;
            resizeImages(framesSmall, width, height);
        }
        
        //determine random yPos
        if ((int)(Math.random() * 3) + 1 == 1){
            this.setYPos(0); //top of screen
        } else if((int)(Math.random() * 3) + 1 == 2){
            this.setYPos(570 - height); //bottom of screen
        } else {
            this.setYPos(310 - (height / 2)); //middle of screen
        }
        
    }
    
    /**
     * loads the image of the obstacle
     */
    public static void loadImages(){
        //for each frame for small obstacles
        for (int i = 0; i < framesSmall.length; i++) {
            framesSmall[i] = new ImageIcon(VerticalObstacle.class.getResource("imageResources/obstacles/smallVertical/obstacle" + (i + 1) + ".png"));
        }

        //add medium obstacles
        //for each frame
        for (int i = 0; i < framesMedium.length; i++) {
            framesMedium[i] = new ImageIcon(VerticalObstacle.class.getResource("imageResources/obstacles/medVertical/obstacle" + (i + 1) + ".png"));
        }
        
        
    }
    
    /**
     * resizes images to fit height and width
     * @param images array containing original images
     */
    public void resizeImages(ImageIcon[] images, int width, int height){
        //resize each frame
        for (int i = 0; i < images.length; i++) {
            frames[i] = images[i].getImage().getScaledInstance(width, height, Image.SCALE_FAST);

        }
    }
    
    /**
     * gets the images of the vertical obstacles
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

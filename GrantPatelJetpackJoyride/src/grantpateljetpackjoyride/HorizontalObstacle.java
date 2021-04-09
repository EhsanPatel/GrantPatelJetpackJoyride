/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for horizontal obstacles
 */
package grantpateljetpackjoyride;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;

/**
 *
 * @author ehsan
 */
public class HorizontalObstacle extends AbstractObstacle{
    
    //class variables
    private static ImageIcon[] framesSmall = new ImageIcon[4];
    private static ImageIcon[] framesMedium = new ImageIcon[4];
    private static final int BIG_HEIGHT = 150;
    private static final int SMALL_HEIGHT = 100;
    
    //instance variables
    private Image[] frames = new Image[4];
    
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
        //make either a big or small obstacle
        if ((int)(Math.random()* 2 + 1) == 1){ //determine which set of images to use
            this.height = BIG_HEIGHT; //big horizontal obstacle has a different height and width than small obstacle
            this.width = 450;
            resizeImages(framesMedium);
        } else {
            this.height = SMALL_HEIGHT;
            this.width = 240;
            resizeImages(framesSmall);
        }
        
        //determine random yPos
        if ((int)(Math.random() * 3) + 1 == 1){
            this.yPos = 50; //top of screen
        } else if((int)(Math.random() * 3) + 1 == 2){
            this.yPos = 570 - this.height; //bottom of screen
        } else {
            this.yPos = 310 - (this.height / 2); //middle of screen
        }
        
    }
    
    /**
     * loads the image of the obstacle
     */
    public static void loadImages(){
        //for each frame for small obstacles
        for (int i = 0; i < framesSmall.length; i++) { //arrays of images have same length
            framesSmall[i] = new ImageIcon(HorizontalObstacle.class.getResource("imageResources/obstacles/smallHorizontal/obstacle" + (i + 1) + ".png"));
            framesMedium[i] = new ImageIcon(HorizontalObstacle.class.getResource("imageResources/obstacles/medHorizontal/obstacle" + (i + 1) + ".png"));
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
     * gets the images of the horizontal obstacles
     * @return the array of images
     */
    public Image[] getFramesMedium(){
        return frames.clone();
    }
    
    /**
     * sets the images of the obstacle
     * @param frames the new array of images
     */
    public void setFramesMedium(Image[] frames){
        this.frames = frames;
    }
    
    /**
     * draws the obstacle image onto the screen
     * @param m the screen
     * @param g the graphics component
     */
    @Override
    public void draw(MainGUI m, Graphics g){
            super.draw(m, g, frames); //draw obstacle

    }
    
    /**
     * puts attributes of obstacle into string
     * @return the string
     */
    @Override
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
    @Override
    public HorizontalObstacle clone(){
        return new HorizontalObstacle(xPos, yPos, height, width, type);
    }
}

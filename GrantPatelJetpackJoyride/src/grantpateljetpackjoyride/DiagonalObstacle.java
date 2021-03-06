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
    private static ImageIcon[] framesSmallRight = new ImageIcon[4];
    private static ImageIcon[] framesMediumRight = new ImageIcon[4];
    private static ImageIcon[] framesSmallLeft = new ImageIcon[4];
    private static ImageIcon[] framesMediumLeft = new ImageIcon[4];
    private static final int BIG_WIDTH = 375;
    private static final int SMALL_WIDTH = 250;
    
    //instance variables
    private Image[] frames = new Image[4];
    boolean left;
    
    //default constructor
    public DiagonalObstacle(){
        super();
    }
    
    /**
     * constructs obstacle with specified attributes
     * @param xPos the x position
     * @param yPos the y position
     * @param height the height
     * @param width the width
     * @param type the type of obstacle
     * @param left if the obstacle is facing left or not
     */
    public DiagonalObstacle(int xPos, int yPos, int height, int width, String type, boolean left){
        super(xPos, yPos, height, width, type);
        this.left = left;
        //determine if big obstacle, resize the images
        if ((int)(Math.random()*2 + 1) == 1){ //determine which set of images to use
            this.width = BIG_WIDTH;
            this.height = BIG_WIDTH;
            if (left){ //determine which way the obstacle is facing
                resizeImages(framesMediumLeft);
            } else {
                resizeImages(framesMediumRight);
            }
        } else {
            this.width = SMALL_WIDTH;
            this.height = SMALL_WIDTH;
            if (left){ //determine which way the obstacle is facing
                resizeImages(framesSmallLeft);
            } else {
                resizeImages(framesSmallRight);
            }
        }
        
        //determine random yPos
        if ((int)(Math.random() * 3) + 1 == 1){
            this.yPos = 50; //top of screen
        } else if((int)(Math.random() * 3) + 1 == 2){
            this.yPos = 570 - this.width; //bottom of screen
        } else {
            this.yPos = 310 - (this.width / 2); //middle of screen
        }
        
    }
    
    /**
     * determines if the player has collided with the obstacle
     * @param p the player
     * @return true if it has collided, else false
     */
    public boolean hasCollided(Player p){
        int boxHeight = width / 100; //height and width of one of the boxes
        
        //if obstacle is within 500 pixels, eliminate some processing
        if (Math.abs(this.getXPos() - p.getXPos()) < 500) {
            //if the obstacle is facing left, looks like \
            if (left) {
                for (int i = 5; i < 100; i++) { //for each box in the obstacle
                    //check if player is in the y space of each box
                    if (p.getYPos() + 10 < this.getYPos() + (i*boxHeight) + boxHeight - 20 && p.getYPos() - 10 + p.getHeight() > this.getYPos() + (i * boxHeight) + 20){
                        //check if player is in x space of each box
                        if (p.getXPos() + 10 < this.getXPos() + (i*boxHeight) + boxHeight - 20 && p.getXPos() - 10 + p.getHeight() > this.getXPos() + (i * boxHeight) + 20){
                            return true;
                        }
                    }
                }
            } else { //obstacle is facing right /
                for (int i = 12; i < 100; i++) { //for each box in the obstacle
                    //check if player is in the y space of each box
                    if (p.getYPos() + 10 < this.getYPos() + (i*boxHeight) + boxHeight - 20 && p.getYPos() - 10 + p.getHeight() > this.getYPos() + (i * boxHeight) + 20){
                        //check if player is in x space of each box
                        if (p.getXPos() + 10 < this.getXPos() + (width - i*boxHeight) + boxHeight - 30 && p.getXPos() - 10 + p.getHeight() > this.getXPos() + (width - i * boxHeight) + 30){
                            return true;
                        }
                    }
                }
            }
        }
        
        //no collision has happened
        return false;
    }
    
    /**
     * loads the image of the obstacle
     */
    public static void loadImages(){
        //for each frame for diagonal obstacles
        for (int i = 0; i < framesSmallLeft.length; i++) {
            framesSmallLeft[i] = new ImageIcon(DiagonalObstacle.class.getResource("imageResources/obstacles/smallDiagonalLeft/obstacle" + (i + 1) + ".png"));
            framesSmallRight[i] = new ImageIcon(DiagonalObstacle.class.getResource("imageResources/obstacles/smallDiagonalRight/obstacle" + (i + 1) + ".png"));
            framesMediumLeft[i] = new ImageIcon(DiagonalObstacle.class.getResource("imageResources/obstacles/medDiagonalLeft/obstacle" + (i + 1) + ".png"));
            framesMediumRight[i] = new ImageIcon(DiagonalObstacle.class.getResource("imageResources/obstacles/medDiagonalRight/obstacle" + (i + 1) + ".png"));
        }
        

    }
    
    /**
     * resizes images to fit height and width
     * @param images array containing original images
     */
    public void resizeImages(ImageIcon[] images){
        //resize each frame
        for (int i = 0; i < images.length; i++) {
            frames[i] = images[i].getImage().getScaledInstance(width, width, Image.SCALE_FAST);
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
    @Override
    public DiagonalObstacle clone(){
        return new DiagonalObstacle(xPos, yPos, height, width, type, left);
    }
}

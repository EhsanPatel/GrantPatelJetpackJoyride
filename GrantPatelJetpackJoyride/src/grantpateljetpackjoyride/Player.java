/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * A class that handles the attributes of the player
 */
package grantpateljetpackjoyride;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;


public class Player extends AbstractGameObject {

    private int costumeNum;
    private double score;
    private int coins;
    private Image[] runningFrames;
    private Image[] flyingFrames; 
    private Image fallingFrame;
    private double frame;
    private int heightOffGround;
    private boolean isFalling;
    private double fallSpeed;

    /**
     * Primary constructor for the player, makes a default character appear
     */
    public Player(){
        xPos = 360;
        yPos = 450;
        width = 90;
        height = 90;
        costumeNum = 3;
        
        //defaults for game variables that always start at 0
        frame = 0;
        heightOffGround = 0;
        isFalling = false;
        fallSpeed = 0;
        coins = 0;
        score = 0;
        
        loadImages();
    }
    
    /**
     * Secondary Constructor used for assigning initial values to the player
     * @param xPos - the x position of the player on the window
     * @param yPos - the y position of the player on the window
     * @param width - the width of the player
     * @param height - the height of the player
     * @param costumeNum - which costume the user has selected for the player
     */
    public Player(int xPos, int yPos, int width, int height, int costumeNum){
        //chains to primary constructor
        this();
        //other variables that were specified by the parameters
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.costumeNum = costumeNum;
        //reload images with the changed costume number
        loadImages();
    }

    /**
     * A method that draws the character on the screen
     * @param g - the graphics library to use for drawing
     * @param m - the JPanel to draw onto
     */
    public void draw(MainGUI m, Graphics g){
        //casts the graphics object to the better 2d version
        Graphics2D g2d = (Graphics2D) g;
        //stores the y position on the window which is different from the height off of what appears to be the ground on the background image
        yPos = (int)(600-(0.25*600)) - (int)heightOffGround;
        
        //Selects which image to draw
        Image imageToDraw = runningFrames[(int)frame];
        
        if(heightOffGround > 0){
            imageToDraw = flyingFrames[(int)frame];
        }
        if(isFalling){
            imageToDraw = fallingFrame;
        }
        
        //draws the character on the window at the x,y coordinates
        g2d.drawImage(imageToDraw,xPos,yPos,m);
        
        //set colour of the graphics
        g2d.setColor(Color.white);
        
        //set font of graphics
        g2d.setFont(new Font("Abel-Regular", Font.PLAIN, 50));
        
        //draws the score total
        g2d.drawString("" + (int)score, 10, 50);
        
        //draws coin total
        g2d.drawString("" + coins, 1025, 50);
    }
    
    /**
     * Moves the character based on if the user is holding the mouse or spacebar
     * @param holdEvent - if either the mouse or spacebar is being held
     * @param dt - the time difference between the current frame and last frame (dt = delta time)
     */
    public void move(boolean holdEvent, int dt){
        //if the user is holding down the spacebar or mouse
        if(holdEvent){
            isFalling = false;
            //maximum velocity upwards
            if(fallSpeed*dt <= 6000){
                //adds to the velocity
                fallSpeed += 2*dt;
            }
        //if the user is off the ground and not holding down on the mouse
        }else if(heightOffGround > 0){
            isFalling = true;
            //maximum velocity downwards
            if(fallSpeed*dt >= -6000){
                //decreases the velocity
                fallSpeed += -2*dt;
            }
        }

        //top and bottom barrier prevents character from leaving the screen
        if(heightOffGround < 0){
            isFalling = false;
            //sets the velocity to 0 and keeps resetting the height to the max or min
            heightOffGround = 0;
            fallSpeed = 0;
        }else if(heightOffGround > 400){
            heightOffGround = 400;
            fallSpeed = 0;
        }
        
        //Adds the instantaneous speed to the distance off the ground - scaled down by the /15
        heightOffGround += (fallSpeed/40);    
    }
    /**
     * gets the costume that the user has selected
     * @return the selected costume
     */
    public int getCostumeNum(){
        return costumeNum;
    }
    /**
     * sets the costume to show the animations for
     * @param costumeNum the costume number that corresponds with a set of frames
     */
    public void setCostumeNum(int costumeNum){
        this.costumeNum = costumeNum;
    }
    /**
     * gets the score that the player has achieved
     * @return the current score
     */
    public double getScore(){
        return score;
    }
    /**
     * sets the score for the player
     * @param score - the score counter for the player
     */
    public void setScore(double score){
        this.score = score;
    }
    /**
     * gets the number of coins collected by the player
     * @return the number of coins collected
     */
    public int getCoins(){
        return coins;
    }
    /**
     * sets the number of coins collected by the player
     * @param coins - the number of coins collected by the player
     */
    public void setCoins(int coins){
        this.coins = coins;
    }
    /**
     * Adds a coin to the total number collected
     */
    public void updateTotalCoins(){
        coins++;
    }
    /**
     * gets the current animation frame
     * @return the current frame
     */
    public double getFrame(){
        return frame;
    }
    /**
     * sets the current animation frame
     * @param frame - the frame of animation to display
     */
    public void setFrame(int frame){
        this.frame = frame;
    }
    /**
     * moves to the next frame by only a small amount, not each frame update (too fast)
     * @param amount - the amount to increase to the frame by
     */
    public void nextFrame(double amount){
        //increases the frame by a small amount
        frame += amount;
        score += amount;
        if((int)frame >= 4){
            frame = 0;
        }
    }
    /**
     * sets the images for the animation frames
     * @param runningFrames - an array of the frame images
     */
    public void setRunningFrames(Image[] runningFrames){
        this.runningFrames = runningFrames;
    }
    /**
     * gets the array of images for the animation frames
     * @return the image array of the frames
     */
    public Image[] getRunningFrames(){
        return runningFrames;
    }
    /**
     * gets the height off the ground (a certain part of the image is the ground)
     * @return the height off the ground
     */
    public int getHeightOffGround(){
        return heightOffGround;
    }
    /**
     * sets the height off the ground (a certain part of the image is the ground)
     * @param heightOffGround - the height off the ground
     */
    public void setHeightOffGround(int heightOffGround){
        this.heightOffGround = heightOffGround;
    }
    /**
     * gets the fall speed of the player
     * @return the fall speed of the player
     */
    public double getFallSpeed(){
        return fallSpeed;
    }
    /**
     * sets the fall speed of the player
     * @param fallSpeed - how fast the player is falling
     */
    public void setFallSpeed(double fallSpeed){
        this.fallSpeed = fallSpeed;
    }
    
    /**
     * gets the flying frames
     * @return the flying frames
     */
    public Image[] getFlyingFrames(){
        return flyingFrames;
    }
    
    /**
     * sets the flying frames
     * @param flyingFrames new flying frames
     */
    public void setFlyingFrames(Image[] flyingFrames){
        this.flyingFrames = flyingFrames;
    }
    
    /**
     * gets the falling frame
     * @return the falling frame
     */
    public Image getFallingFrame(){
        return fallingFrame;
    }
    
    /**
     * sets the falling frame
     * @param fallingFrame new falling frame
     */
    public void setFallingFrame(Image fallingFrame){
        this.fallingFrame = fallingFrame;
    }
    
    /**
     * gets if player is falling
     * @return if player is falling
     */
    public boolean getIsFalling(){
        return isFalling;
    }
    
    /**
     * sets if the player is falling
     * @param isFalling if player is falling
     */
    public void setIsFalling(boolean isFalling){
        this.isFalling = isFalling;
    }
    
    
    
    /**
     * loads the images into an array of frames to use for drawing
     */
    public void loadImages(){
        ImageIcon[] iiRunning = new ImageIcon[4];
        runningFrames = new Image[iiRunning.length];
        
        for(int i = 0; i < iiRunning.length; ++i){
            iiRunning[i] = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running"+(i+1)+".png"));
            runningFrames[i] = iiRunning[i].getImage().getScaledInstance(90, 90, Image.SCALE_FAST);
        }
        
        ImageIcon[] iiFlying = new ImageIcon[4];
        flyingFrames = new Image[iiFlying.length];
        
        for(int i = 0; i < iiFlying.length; ++i){
            iiFlying[i] = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/flying"+(i+1)+".png"));
            flyingFrames[i] = iiFlying[i].getImage().getScaledInstance(90, 204, Image.SCALE_FAST);
        }
        
        ImageIcon iiFalling = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/falling.png"));
        fallingFrame = iiFalling.getImage().getScaledInstance(90, 90, Image.SCALE_FAST);
        

    }
    /**
     * provides the values of the player variables collected in a string
     * @return the values of the player grouped in a string
     */
    @Override
    public String toString(){
        return super.toString() + "\nCostume Number:\t" + costumeNum;
    }
    
    /**
     * determines if two players are the same
     * @param p the second player
     * @return true if the same, else false
     */
    public boolean equals(Player p){
        return super.equals(p) && this.costumeNum == p.getCostumeNum();
    }
    /**
     * clones a player object
     * @return the clone
     */
    @Override
    public AbstractGameObject clone(){
        return this;
    }

}

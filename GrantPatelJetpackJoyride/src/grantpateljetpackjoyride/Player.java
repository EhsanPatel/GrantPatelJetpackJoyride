/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * A class that handles the attributes of the player
 */
package grantpateljetpackjoyride;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;


public class Player extends AbstractGameObject {

    private int costumeNum;
    private int score;
    private int coins;
    private Image[] characterFrames;
    private double frame;
    private int heightOffGround;
    private double fallSpeed;

    /**
     * Primary constructor for the player, makes a default character appear
     */
    public Player(){
        xPos = 360;
        yPos = 450;
        width = 90;
        height = 90;
        costumeNum = 1;
        
        //defaults for game variables that always start at 0
        frame = 0;
        heightOffGround = 0;
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
     * @param windowHeight - how tall the window is
     * @param m - the JPanel to draw onto
     */
    public void draw(Graphics g, int windowHeight, MainGUI m){
        //casts the graphics object to the better 2d version
        Graphics2D g2d = (Graphics2D) g;
        //stores the y position on the window which is different from the height off of what appears to be the ground on the background image
        yPos = (int)(windowHeight-(0.25*windowHeight)) - (int)heightOffGround;
        //draws the character on the window at the x,y coordinates
        g2d.drawImage(characterFrames[(int)frame],xPos,yPos,m);
    }
    
    /**
     * Moves the character based on if the user is holding the mouse or spacebar
     * @param holdEvent - if either the mouse or spacebar is being held
     * @param dt - the time difference between the current frame and last frame (dt = delta time)
     */
    public void move(boolean holdEvent, int dt){
        //if the user is holding down the spacebar or mouse
        if(holdEvent){
            //maximum velocity upwards
            if(fallSpeed <= 40){
                //adds to the velocity
                fallSpeed += 0.2*dt;
            }
        //if the user is off the ground and not holding down on the mouse
        }else if(heightOffGround > 0){
            //maximum velocity downwards
            if(fallSpeed >= -40){
                //decreases the velocity
                fallSpeed += -0.2*dt;
            }
        }

        //Adds the instantaneous speed to the distance off the ground - scaled down by the /15
        heightOffGround += (fallSpeed/20);

        //top and bottom barrier prevents character from leaving the screen
        if(heightOffGround < 0){
            //sets the velocity to 0 and keeps resetting the height to the max or min
            heightOffGround = 0;
            fallSpeed = 0;
        }else if(heightOffGround > 400){
            heightOffGround = 400;
            fallSpeed = 0;
        }
            
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
    public int getScore(){
        return score;
    }
    /**
     * sets the score for the player
     * @param score - the score counter for the player
     */
    public void setScore(int score){
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
        if((int)frame >= 4){
            frame = 0;
        }
    }
    /**
     * sets the images for the animation frames
     * @param characterFrames - an array of the frame images
     */
    public void setCharacterFrames(Image[] characterFrames){
        this.characterFrames = characterFrames;
    }
    /**
     * gets the array of images for the animation frames
     * @return the image array of the frames
     */
    public Image[] getCharacterFrames(){
        return characterFrames;
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
     * loads the images into an array of frames to use for drawing
     */
    @Override
    public void loadImages(){
        ImageIcon iiCostume1Frame1 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running1.png"));
        ImageIcon iiCostume1Frame2 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running2.png"));
        ImageIcon iiCostume1Frame3 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running3.png"));
        ImageIcon iiCostume1Frame4 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running4.png"));
        
        characterFrames = new Image[] {iiCostume1Frame1.getImage().getScaledInstance(90, 90, Image.SCALE_FAST),
            iiCostume1Frame2.getImage().getScaledInstance(90, 90, Image.SCALE_FAST),
            iiCostume1Frame3.getImage().getScaledInstance(90, 90, Image.SCALE_FAST),
            iiCostume1Frame4.getImage().getScaledInstance(90, 90, Image.SCALE_FAST)};
    }
    /**
     * provides the values of the player variables collected in a string
     * @return the values of the player grouped in a string
     */
    public String toString(){
        return "";
    }
    public boolean equals(AbstractGameObject p){
        return true;
    }
    public AbstractGameObject clone(){
        return this;
    }

}

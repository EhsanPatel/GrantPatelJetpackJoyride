/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for methods common to all powerups
 */
package grantpateljetpackjoyride;

/**
 *
 * @author ehsan
 */
abstract public class AbstractPowerUp extends AbstractGameObject{
    //instance variables
    protected int moveSpeed, amplitude;
    
    //default constructor
    public AbstractPowerUp(){
        super();
        moveSpeed = 0;
        amplitude = 0;
    }
    
    /**
     * constructs an abstract powerup with default attributes
     * @param xPos the x position
     * @param yPos the y position
     * @param height the height
     * @param width the width
     * @param moveSpeed the speed it moves at
     * @param amplitude the amplitude of its movement function
     */
    public AbstractPowerUp(int xPos, int yPos, int height, int width, int moveSpeed, int amplitude){
        super(xPos, yPos, width, height);
        this.moveSpeed = moveSpeed;
        this.amplitude = amplitude;
    }
    
    /**
     * gets the movement speed
     * @return the movement speed
     */
    public int getMoveSpeed(){
        return moveSpeed;
    }
    
    /**
     * sets the movement speed
     * @param moveSpeed the new movement speed
     */
    public void setMoveSpeed(int moveSpeed){
        this.moveSpeed = moveSpeed;
    }
    
    /**
     * gets the amplitude
     * @return the amplitude
     */
    public int getAmplitude(){
        return amplitude;
    }
    
    /**
     * sets the amplitude
     * @param amplitude the new amplitude
     */
    public void setAmplitude(int amplitude){
        this.amplitude = amplitude;
    }
    
    /**
     * defines how the powerup will move
     */
    public void move(){
        //do this later
    }
    
    /**
     * puts attributes of abstract power up in a string
     * @return the string
     */
    public String toString(){
        String str = super.toString() + "\nMovement Speed:\t" + moveSpeed + "\n" + 
                "Amplitude:\t" + amplitude;
        return str;
    }
    
    //methods that cannot be coded for in abstract class
    abstract public void draw();
    
    abstract public void loadImages();
    
    abstract public AbstractPowerUp clone();
}

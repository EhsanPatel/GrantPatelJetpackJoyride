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
    
    public int getMoveSpeed(){
        return moveSpeed;
    }
    
    public void setMoveSpeed(int moveSpeed){
        this.moveSpeed = moveSpeed;
    }
    
    public int getAmplitude(){
        return amplitude;
    }
    
    public void setAmplitude(int amplitude){
        this.amplitude = amplitude;
    }
    
    public void move(){
        //do this later
    }
    
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

/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for methods user for adding coins into the game
 */
package grantpateljetpackjoyride;

/**
 *
 * @author ehsan
 */
public class Coin extends AbstractGameObject{
    
    /**
     * Some things in this class don't function yet due to the image variable not being added in yet, 
     * clone method doesn't work as coin is assigned a new value in the constructor each time
     * 
     * 
     * 
     * 
     */
    
    
    //instance variables
    protected int value;
    //protected Image coinImage; will add in later
    
    //default constructor
    Coin(){
        super();
        value = 0;
    }
    
    /**
     * constructs coin with specified attributes, 1/20 chance of getting a coin with a value of 5
     * @param xPos
     * @param yPos
     * @param height
     * @param width 
     */
    public Coin(int xPos, int yPos, int height, int width){
        super(xPos, yPos, height, width);
        value = (int)(Math.random() * 20) + 1;
        
        //determining what the value of the coin will be
        if (value == 20){
            value = 5;
        } else {
            value = 1;
        }
    }
    
    /**
     * gets the value of the coin
     * @return the value
     */
    public int getValue(){
        return value;
    }
    
    /**
     * sets the value of the coin
     * @param value the new value
     */
    public void setValue(int value){
        this.value = value;
    }
    
    /**
     * draws the coin
     */
    public void draw(){
        //do this later
    }
    
    /**
     * loads the coin image depending on the value of the coin
     */
    public void loadImages(){
        if (value == 1){
            //load this image
        } else {
            //load this image
        }
    }
    
    /**
     * puts attributes of the coin into a string
     * @return the string
     */
    public String toString(){ //add coin image variable in too when it is ready
        String str = super.toString() + "\nValue:\t" + value;
        return str;
    }
    
    /**
     * determines if two coins are the same
     * @param c the second coin
     * @return true is the same, else false
     */
    public boolean equals(Coin c){ //add coin image variable in too
        return super.equals(c) && this.value == c.getValue();
    }
    
    /**
     * clones the coin
     * @return the cloned coin
     */
    public Coin clone(){ //clone doesn't work right now because the value could change
        return new Coin(xPos, yPos, height, width);
    }
}

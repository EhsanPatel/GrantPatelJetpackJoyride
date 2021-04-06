/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * codes for the double coins powerup
 */
package grantpateljetpackjoyride;

/**
 *
 * @author ehsan
 */
public class DoubleCoins extends AbstractPowerUp{
    
    //instance variables
    private String type;
    //private Image coinPowerImage; add in later, is it static?
    
    //default constructor
    public DoubleCoins(){
        super();
        type = "";
    }
    
    /**
     * creates double coin power up with specified attributes
     * @param xPos
     * @param yPos
     * @param height
     * @param width
     * @param moveSpeed
     * @param amplitude
     * @param type 
     */
    public DoubleCoins(int xPos, int yPos, int height, int width, int moveSpeed, int amplitude, String type){
        super(xPos, yPos, height, width, moveSpeed, amplitude);
        this.type = type;
    }
    
    /**
     * gets the type of powerup
     * @return the type
     */
    public String getType(){
        return type;
    }
    
    /**
     * sets powerup type
     * @param type the new type
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * draws the powerup on the screen
     */
    public void draw(){
        //do this later
    }
    
    /**
     * loads the image of the powerup
     */
    public void loadImages(){
        //later
    }
    
    /**
     * puts attributes of powerup in a string
     * @return the string
     */
    public String toString(){
        return super.toString() + "\nType: " + type;
    }
    
    /**
     * checks if two powerups are the same
     * @param dc the second powerup
     * @return true if same, else false
     */
    public boolean equals(DoubleCoins dc){
        return super.equals(dc) && this.type.equals(dc.getType());
    }
    
    /**
     * clones the powerup
     * @return the clone
     */
    public DoubleCoins clone(){
        return this;
    }
    
}

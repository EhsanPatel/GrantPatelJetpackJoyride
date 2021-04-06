/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for methods common to vertical obstacles
 */
package grantpateljetpackjoyride;

/**
 *
 * @author ehsan
 */
public class VerticalObstacle extends AbstractObstacle{
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
    }
    
    /**
     * loads the image of the obstacle
     */
    public void loadImages(){
        //load the image
    }
    
    /**
     * draws the obstacle image onto the screen
     */
    public void draw(){
        //draw the image onto the screen
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

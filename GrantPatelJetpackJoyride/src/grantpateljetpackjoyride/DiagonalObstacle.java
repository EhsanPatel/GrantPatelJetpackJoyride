/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for diagonal obstacles
 */
package grantpateljetpackjoyride;

/**
 *
 * @author ehsan
 */
public class DiagonalObstacle extends AbstractObstacle{
    
    //default constructor
    public DiagonalObstacle(){
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
    public DiagonalObstacle(int xPos, int yPos, int height, int width, String type){
        super(xPos, yPos, height, width, type);
    }
    
    /**
     * determines if the player has collided with the obstacle
     * @param p the player
     * @return true if it has collided, else false
     */
    public boolean hasCollided(Player p){
        //do this later
        return true; //just so there aren't any errors
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
    public DiagonalObstacle clone(){
        return new DiagonalObstacle(xPos, yPos, height, width, type);
    }
}

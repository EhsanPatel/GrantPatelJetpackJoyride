/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Abstract Obstacle Class - codes for methods common to all obstacles
 */
package grantpateljetpackjoyride;

/**
 *
 * @author ehsan
 */
abstract public class AbstractObstacle extends AbstractGameObject{
    
    //instance variables
    protected String type;
    
    //default constructor
    public AbstractObstacle(){
        super();
        type = "";
    }
    
    /**
     * constructs abstract obstacle with specified attributes
     * @param xPos the x position
     * @param yPos the y position
     * @param height the height
     * @param width the width
     * @param type the type of obstacle
     */
    public AbstractObstacle(int xPos, int yPos, int height, int width, String type){
        super(xPos, yPos, height, width);
        this.type = type;
    }
    
    /**
     * gets the type of obstacle
     * @return the type
     */
    public String getType(){
        return type;
    }
    
    /**
     * sets the type of obstacle
     * @param type the new type
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * puts attributes of object in a string
     * @return the string
     */
    public String toString(){
        String str = super.toString() + "\nType:\t" + type;
        return str;
    }
    
    /**
     * determines if two abstract obstacles are the same
     * @param a the second object
     * @return true if the same, else false
     */
    public boolean equals(AbstractObstacle a){
        return super.equals(a) && a.getType().equals(this.getType());
    }
    
    //methods that cannot be coded for in abstract class
    abstract public void loadImages();
    
    abstract public void draw();
    
}

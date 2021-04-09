/*
 * Ehsan Patel and Colin Grant
 * 3-Apr-2021
 * Creates a clickable button to redirect to a different screen
 */
package grantpateljetpackjoyride;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JFrame;

public class MenuButton {
    //variables for the menu button
    private int x;
    private int y;
    private int width;
    private int height;
    private String type;
    private JFrame destination;
    private Image image;
    
    /**
     * primary constructor for default button
    **/
    public MenuButton(){
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        type = "Redirect";
        destination = new GrantPatelJetpackJoyride();
    }

    /**
     * Secondary constructor with custom values
     * @param x - x coordinate
     * @param y - y coordinate
     * @param width - width of button
     * @param height - height of button
     * @param type - type of button (redirect or toggle)
     * @param image - image to display
     */
    public MenuButton(int x, int y, int width, int height, String type, Image image){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.image = image;
    }
    
    /**
     * chained to secondary, has the option to specify where it redirects to
     * @param x - x coordinate
     * @param y - y coordinate
     * @param width - width of button
     * @param height - height of button
     * @param type - type of button (redirect or toggle)
     * @param image - image to display
     * @param destination - where to redirect to
     */
    public MenuButton(int x, int y, int width, int height, String type, Image image, JFrame destination){
        //chains
        this(x,y,width,height,type,image);
        //specifies the redirect
        if(type.equals("Redirect")){
            this.destination = destination;
        }
        
    }
    
    /**
     * if the menu button is clicked, perform an action
     * @param mouseX
     * @param mouseY
     * @param m
     * @return 
     */
    public boolean buttonAction(int mouseX, int mouseY, MainGUI m){
        //checks for the click on the button
        if(mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height){
            if(type.equals("Redirect")){
                //redirects to other screen
                ((JFrame)m.getRootPane().getParent()).setVisible(false);
                destination.setVisible(true);
            }
            //returns true if the button was pressed
            return true;
        }
        return false;
    }
    
    /**
     * draws the menu button image
     * @param g2d - the graphics object
     * @param m - the window to draw on
     */
    public void draw(Graphics2D g2d, MainGUI m){
        g2d.drawImage(image, x, y, m);
    }
    
    
    /**
     * getter for the x coordinate
     * @return the x coordinate
     */
    public int getX(){
        return x;
    }
    /**
     * getter for the y coordinate
     * @return the y coordinate
     */
    public int getY(){
        return y;
    }
    /**
     * getter for the width
     * @return the width
     */
    public int getWidth(){
        return width;
    }
    /**
     * getter for the height
     * @return the height
     */
    public int getHeight(){
        return height;
    }
    /**
     * getter for the image
     * @return the image
     */
    public Image getImage(){
        return image;
    }
    /**
     * getter for the destination
     * @return the destination
     */
    public JFrame getDestination(){
        return destination;
    }
    /**
     * getter for the type
     * @return the type
     */
    public String getType(){
        return type;
    }
    
 
    /**
     * setter for the x coordinate
     * @param x - the x
     */
    public void setX(int x){
        this.x = x;
    }
    /**
     * setter for the y coordinate 
     * @param y - the y
     */
    public void setY(int y){
        this.y = y;
    }
    /**
     * setter for the width
     * @param width - the width
     */
    public void setWidth(int width){
        this.width = width;
    }
    /**
     * setter for the height
     * @param height - the height
     */
    public void setHeight(int height){
        this.height = height;
    }
    /**
     * setter for the image
     * @param image - the image
     */
    public void setImage(Image image){
        this.image = image;
    }
    /**
     * setter for the destination
     * @param destination - the destination
     */
    public void setDestination(JFrame destination){
        this.destination = destination;
    }
    /**
     * setter for the type
     * @param type - the type
     */
    public void setType(String type){
        this.type = type;
    }
    
    @Override
    public String toString(){
        return "x: " + x
                + "y: " + y
                + "width: " + width
                + "height: " + height
                + "image: " + image
                + "type: " + type
                + "destination: " + destination;
    }
    
    /**
     * compares this menu button with the inputted button
     * @param mb - the inputted menu button
     * @return if they are the same
     */
    public boolean equals(MenuButton mb){
        return (x == mb.getX() && y == mb.getY() && width == mb.getWidth() && height == mb.getHeight() && image == mb.getImage() && destination.equals(mb.getDestination()) && type.equals(mb.getType()));
    }
    
    @Override
    public MenuButton clone(){
        return new MenuButton(x,y,width,height,type,image,destination);
    }
}

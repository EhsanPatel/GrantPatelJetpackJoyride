/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for methods user for adding coins into the game
 */
package grantpateljetpackjoyride;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ehsan
 */
public class Coin extends AbstractGameObject {

    //instance variables
    private int value;
    private static Image coinImage1, coinImage2;

    //default constructor
    Coin() {
        super();
        value = 0;
    }

    /**
     * constructs coin with specified attributes
     *
     * @param xPos the x position of the coin
     * @param yPos the y position of the coin
     * @param height the height of the coin
     * @param width the width of the coin
     * @param value the value of the coin
     */
    public Coin(int xPos, int yPos, int height, int width, int value) {
        super(xPos, yPos, height, width);

        this.value = 1; //value will always be 1
        
        //making random y position for the coin
        this.yPos = (int)(Math.random() * 450) + 60;
    }
    
    /**
     * detects collisions between a coin and player
     * @param p the player
     * @return true if collided, else false
     */
    public boolean coinCollisions(Player p){

        //if the x distance between the coin and player is less than 100 on either side
        if (Math.abs(this.getXPos() - p.getXPos()) < 100) { //eliminates many coins to speed up processing

            //check if player is in the right y space
            if (p.getYPos() + 10 < this.getYPos() + this.getHeight() && p.getYPos() + p.getHeight() - 10 > this.getYPos()) {
                //check if player is in the right x space
                if (p.getXPos() + 10 < this.getXPos() + this.getWidth() && p.getXPos() + p.getWidth() - 10 > this.getXPos()) {
                    return true;
                }
            }
        }
        
        //collision did not happen
        return false;
    }

    /**
     * gets the value of the coin
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * sets the value of the coin
     *
     * @param value the new value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * draws the coin
     * @param m the main GUI
     * @param g the graphics component
     */
    @Override
    public void draw(MainGUI m, Graphics g) {
        //casts the graphics object to the better 2d version
        Graphics2D g2d = (Graphics2D) g;
        
        //draw coin to screen, depending on what the value is
        if (value == 1){
            g2d.drawImage(coinImage1, xPos, yPos, m);
        } else {
            g2d.drawImage(coinImage2, xPos, yPos, m);
        }
        
        //draws a coin in the corner to count coins
        g2d.drawImage(coinImage1, 975, 10, m);
        
    }

    /**
     * loads the coin image depending on the value of the coin
     */
    public static void loadImages() {
        coinImage1 = (new ImageIcon(Coin.class.getResource("imageResources/coin.png"))).getImage().getScaledInstance(40, 40, Image.SCALE_FAST);
        //coinImage2 = (new ImageIcon(getClass().getResource("imageResources/coin2.png"))).getImage().getScaledInstance(width, height, Image.SCALE_FAST);
        
    }

    /**
     * puts attributes of the coin into a string
     *
     * @return the string
     */
    @Override
    public String toString() { //add coin image variable in too when it is ready
        String str = super.toString() + "\nValue:\t" + value;
        return str;
    }

    /**
     * determines if two coins are the same
     *
     * @param c the second coin
     * @return true is the same, else false
     */
    public boolean equals(Coin c) { //add coin image variable in too
        return super.equals(c) && this.value == c.getValue();
    }

    /**
     * clones the coin
     *
     * @return the cloned coin
     */
    @Override
    public Coin clone() {
        return new Coin(xPos, yPos, height, width, value);
    }
}

/*
 * Ehsan Patel and Colin Grant
 * 1-Apr-2021
 * Codes for methods user for adding coins into the game
 */
package grantpateljetpackjoyride;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author ehsan
 */
public class Coin extends AbstractGameObject {

    /**
     * Some things in this class don't function yet due to the image variable
     * not being added in yet
     *
     *
     *
     *
     */
    //instance variables
    private int value;
    private static Image coinImage1, coinImage2;

    //default constructor
    Coin() {
        super();
        value = 0;
    }

    /**
     * constructs coin with specified attributes, 1/20 chance of getting a coin
     * with a value of 5
     *
     * @param xPos
     * @param yPos
     * @param height
     * @param width
     */
    public Coin(int xPos, int yPos, int height, int width, int value) {
        super(xPos, yPos, height, width);

        //determining if coin already has a value, if it is cloned it will have a value, else make a new value
        if (value == 0) {
            value = (int) (Math.random() * 20) + 1;

            //determining what the value of the coin will be
            if (value == 20) {
                value = 5;
            } else {
                value = 1;
            }
        }
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
     */
    public void draw(MainGUI m, Graphics g) {
        //casts the graphics object to the better 2d version
        Graphics2D g2d = (Graphics2D) g;
        
        //draw coin to screen, depending on what the value is
        if (value == 1){
            g2d.drawImage(coinImage1, xPos, yPos, m);
        } else {
            g2d.drawImage(coinImage2, xPos, yPos, m);
        }
    }

    /**
     * loads the coin image depending on the value of the coin
     */
    public void loadImages() {
        coinImage1 = (new ImageIcon(getClass().getResource("imageResources/coins/coin1.png"))).getImage().getScaledInstance(width, height, Image.SCALE_FAST);
        coinImage2 = (new ImageIcon(getClass().getResource("imageResources/coins/coin2.png"))).getImage().getScaledInstance(width, height, Image.SCALE_FAST);
        
    }

    /**
     * puts attributes of the coin into a string
     *
     * @return the string
     */
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
    public Coin clone() {
        return new Coin(xPos, yPos, height, width, value);
    }
}

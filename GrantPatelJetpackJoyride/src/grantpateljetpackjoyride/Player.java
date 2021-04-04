/*
 * Ehsan Patel
 * 1-Apr-2021
 * and open the template in the editor.
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
public class Player extends AbstractGameObject {
    private int costumeNum;
    private int score;
    private int coins;
    private Image[] characterFrames;
    private double frame;
    private int heightOffGround;
    private double fallSpeed;

            
    public Player(){
        costumeNum = 1;
        frame = 0;
        heightOffGround = 0;
        fallSpeed = 0;
    }
    public Player(int xPos, int yPos, int height, int width, int costumeNum, int score){
        //coins always starts as 0
    }

    
    public void draw(Graphics g, int windowHeight, MainGUI m){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(characterFrames[(int)frame],351,(windowHeight-150) - (int)heightOffGround,m);
    }
    
    public void move(boolean holdEvent, int dt){
        if(holdEvent){
                if(fallSpeed <= 120){
                    fallSpeed += 0.4*dt;
                }
            }else if(heightOffGround > 0){
                if(fallSpeed >= -120){
                    fallSpeed += -0.4*dt;
                }
            }

            //slows down the jetpack speed
            heightOffGround += (fallSpeed/15);
            
            
            //top and bottom barrier prevents character from leaving the screen
            if(heightOffGround < 0){
                heightOffGround = 0;
                fallSpeed = 0;
            }else if(heightOffGround > 400){
                heightOffGround = 400;
                fallSpeed = 0;
            }
    }
    public int getCostume(){
        return costumeNum;
    }
    public void setCostume(int costumeNum){
        this.costumeNum = costumeNum;
    }
    public int getScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getCoins(){
        return coins;
    }
    public void setCoins(int coins){
        this.coins = coins;
    }
    public double getFrame(){
        return frame;
    }
    public void setFrame(int frame){
        this.frame = frame;
    }
    public void nextFrame(double amount){
        frame += amount;
        if((int)frame >= 4){
            frame = 0;
        }
    }
    public void updateTotalCoins(){
        coins++;
    }
    public void loadImages(){
        ImageIcon iiCostume1Frame1 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running1.png"));
        ImageIcon iiCostume1Frame2 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running2.png"));
        ImageIcon iiCostume1Frame3 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running3.png"));
        ImageIcon iiCostume1Frame4 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running4.png"));
        
        characterFrames = new Image[] {iiCostume1Frame1.getImage().getScaledInstance(90, 90, Image.SCALE_FAST),
            iiCostume1Frame2.getImage().getScaledInstance(90, 90, Image.SCALE_FAST),
            iiCostume1Frame3.getImage().getScaledInstance(90, 90, Image.SCALE_FAST),
            iiCostume1Frame4.getImage().getScaledInstance(90, 90, Image.SCALE_FAST)};
    }
    public String toString(){
        return "";
    }
    public boolean equals(AbstractGameObject p){
        return true;
    }
    public AbstractGameObject clone(){
        return this;
    }

}

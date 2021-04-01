/*
 * Ehsan Patel
 * 26-Mar-2021
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author ehsan
 */
public class GameFrame extends JPanel implements ActionListener{
    
    //constants for the canvas
    private final int B_WIDTH = 1110;
    private final int B_HEIGHT = 600;
    private final int SPEED = 0;
    //keeps the game running and updating
    private Timer timer;

    //image declaration
    private Image startBG;
    private Image[] characterFrames;
    
    private double scrollX;
    private double animationFrame;
    private String gamestate;
    private int controlLimiter;
    
    boolean holdEvent;
    private int heightOffGround;
    private double fallSpeed;
    /**
     * primary constructor to build the board and create a window that can be interacted with by the user
     */
    public GameFrame() {

        //initializes the attributes of the board
        initBoard();
        
        //allows the board to recieve input from mouseclicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //what to do when mouse is clicked
                if(gamestate.equals("menu")){
                    gamestate = "playing";
                }else if(gamestate.equals("playing")){
                    holdEvent = true;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                //what to do when mouse is released
                if(gamestate.equals("playing")){
                    holdEvent = false;
                }
            }
        });
        
        
        scrollX = 0;
        animationFrame = 0;
        fallSpeed = 0;
        controlLimiter = 0;
        heightOffGround = 0;
        gamestate = "menu";
        holdEvent = false;
        
        
    }
    private void initBoard() {
        //setup the canvas
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        //load the image resources to use
        loadImages();
        
        //creates a timer to update the window
        timer = new Timer(SPEED, this);
        timer.setInitialDelay(500);
        timer.start();
        
    }
    
    //overrides the draw method to draw custom items on the window
    @Override
    public void paintComponent(Graphics g) {
        //calls the parent class' drawing method to setup for drawing
        super.paintComponent(g);
        drawLoop(g);
    }
    
    private void drawLoop(Graphics g) {
        //casts the regular graphics object into the updated 2d graphics object
        Graphics2D g2d = (Graphics2D) g;
        
        
        if(gamestate.equals("playing")){
            scrollX -= 0.5;
            animationFrame+= 0.018;
            if((int)animationFrame == 4){
                animationFrame = 0;
            }
            if(controlLimiter%6 == 0){
                if(holdEvent){
                fallSpeed += 0.8;
                }
                if(heightOffGround > 0){
                    fallSpeed += -0.5;
                }
                heightOffGround += (int)fallSpeed;
                if(heightOffGround < 0){
                    heightOffGround = 0;
                    fallSpeed = 0;
                }else if(heightOffGround > 400){
                    heightOffGround = 400;
                    fallSpeed = 0;
                }
            }
            controlLimiter++;
            
            
        }
        
        //draws the items on the window
        g2d.setColor(new Color(123,133,146));//light suare color
        g2d.fillRect(0, 0, 351, B_HEIGHT);
        
        g2d.drawImage(startBG,351 + (int)scrollX,0,this);
        g2d.drawImage(characterFrames[(int)animationFrame],351,B_HEIGHT-150 - heightOffGround,this);
        
        
        //synchronizes the graphics
        Toolkit.getDefaultToolkit().sync();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {       
        repaint();
    }
    
    /**
     * loads all of the images into the image arrays to store the resources
     */
    private void loadImages() {
        int costumeNum = 2;
        //finds the images relative path from the place that the board class is stored - works for the executable jar too
        ImageIcon iiStartBG = new ImageIcon(getClass().getResource("imageResources/FullImage.png"));
        ImageIcon iiCostume1Frame1 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running1.png"));
        ImageIcon iiCostume1Frame2 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running2.png"));
        ImageIcon iiCostume1Frame3 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running3.png"));
        ImageIcon iiCostume1Frame4 = new ImageIcon(getClass().getResource("imageResources/costume"+costumeNum+"/running4.png"));
        
        startBG = iiStartBG.getImage().getScaledInstance(4509, B_HEIGHT, Image.SCALE_AREA_AVERAGING);
        characterFrames = new Image[] {iiCostume1Frame1.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING),
            iiCostume1Frame2.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING),
            iiCostume1Frame3.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING),
            iiCostume1Frame4.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING)};
    }
    
}

/*
 * Ehsan Patel and Colin Grant
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author ehsan
 */
public class MainGUI extends JPanel implements ActionListener, KeyListener {
    
    //constants for the canvas
    private final int B_WIDTH = 1110;
    private final int B_HEIGHT = 600;
    private final int SPEED = 0;
    //keeps the game running and updating
    private Timer timer;

    //image declaration
    private Image startBG;
    private Image[] characterFrames;
    private Image[] menuButtons;
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
    public MainGUI() {

        //initializes the attributes of the board
        initBoard();

        //allows the window to recieve keyboard input
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
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

        //game and animation variables
        scrollX = 0;
        animationFrame = 0;
        fallSpeed = 0;
        controlLimiter = 0;
        heightOffGround = 0;
        gamestate = "menu";
        holdEvent = false;
        
    }
    
    
    @Override
    public void keyReleased(KeyEvent k){
        if(k.getKeyChar() == ' '){
            //what to do when mouse is released
            if (gamestate.equals("playing")) {
                holdEvent = false;
            }
        }
    }
    
    
    @Override
    public void keyPressed(KeyEvent k){
        if(k.getKeyChar() == ' '){
            if(gamestate.equals("menu")){
                gamestate = "playing";
            }else if(gamestate.equals("playing")){
                holdEvent = true;
            }
        }
    }
    
    
    @Override
    public void keyTyped(KeyEvent k){
    } 
    
    
    private void initBoard() {
        //load the image resources to use
        loadImages();
        //setup the canvas
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
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
        //draws background color and image
        g2d.setColor(new Color(123,133,146));//light grey/blue color
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        g2d.drawImage(startBG,351 + (int)scrollX,0,this);
        
        if(gamestate.equals("menu")){
            for(int i = 0; i < menuButtons.length; ++i){
                g2d.drawImage(menuButtons[i],15,i*90+120,this);
            }
        }else if(gamestate.equals("playing")){
            scrollX -= 0.5;
            animationFrame+= 0.018;
            if((int)animationFrame == 4){
                animationFrame = 0;
            }
            if(controlLimiter%8 == 0){
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
            //draws the character on the window
            g2d.drawImage(characterFrames[(int)animationFrame],351,B_HEIGHT-150 - heightOffGround,this);
        }
        
        
        
        
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
        
        startBG = iiStartBG.getImage().getScaledInstance(4509, B_HEIGHT, Image.SCALE_SMOOTH);
        characterFrames = new Image[] {iiCostume1Frame1.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING),
            iiCostume1Frame2.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING),
            iiCostume1Frame3.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING),
            iiCostume1Frame4.getImage().getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING)};
        
        ImageIcon iiStoreButton = new ImageIcon(getClass().getResource("imageResources/buttons/storeButton.png"));
        ImageIcon iiStatsButton = new ImageIcon(getClass().getResource("imageResources/buttons/statsButton.png"));
        ImageIcon iiTutorialButton = new ImageIcon(getClass().getResource("imageResources/buttons/tutorialButton.png"));
        ImageIcon iiCreditButton = new ImageIcon(getClass().getResource("imageResources/buttons/creditsButton.png"));
        
        menuButtons = new Image[] {iiStoreButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH),
            iiStatsButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH),
            iiTutorialButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH),
            iiCreditButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH),
        };

    }
    
}

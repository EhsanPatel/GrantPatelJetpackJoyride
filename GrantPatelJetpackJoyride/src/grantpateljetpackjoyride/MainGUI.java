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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author ehsan
 */
public class MainGUI extends JPanel implements ActionListener, KeyListener, MouseListener {
    
    //constants for the canvas
    private final int B_WIDTH = 1110;
    private final int B_HEIGHT = 600;
    private final int SPEED = 0;
    //keeps the game running and updating
    private Timer timer;

    //image declaration
    private Image startBG;
    private Image[] characterFrames;
    private MenuButton[] menuButtons;
    private double scrollX;
    private double animationFrame;
    private String gamestate;
    private int speedControl;
    
    boolean holdEvent;
    private int heightOffGround;
    private double fallSpeed;
    
    private long t1;
    private long t2;
    
    /**
     * primary constructor to build the JPanel and create a window that can be interacted with by the user
     */
    public MainGUI() {
        //initializes the attributes of the board
        initPanel();

        //allows the window to recieve keyboard input
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //allows the board to recieve input from mouseclicks
        addMouseListener(this);

        //game and animation variables
        t1 = 0;
        t2 = 0;
        scrollX = 0;
        animationFrame = 0;
        fallSpeed = 0;
        speedControl = 120;
                
        heightOffGround = 0;
        gamestate = "menu";
        holdEvent = false;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        //what to do when mouse is clicked
        if(gamestate.equals("menu")){
            for(int i = 0; i < menuButtons.length; ++i){
                if(menuButtons[i].buttonAction(e.getX(), e.getY(), this)){
                    return;
                }
                
            }
            gamestate = "playing";
        }else if(gamestate.equals("playing")){
            holdEvent = true;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        //what to do when mouse is released in gameplay
        if(gamestate.equals("playing")){
            holdEvent = false;
        }
    }
    
    

    
    //functions to implement keylistener
    @Override
    public void keyReleased(KeyEvent k){
        //what to do when a key is released
        if(k.getKeyChar() == ' '){
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

    
    private void initPanel() {
        //load the image resources to use
        loadImages();
        //setup the canvas
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        //creates a timer to update the window
        timer = new Timer(SPEED, this);
        timer.setInitialDelay(10);
        timer.start();
    }
    
    
    //overrides the draw method to draw custom items on the window
    @Override
    public void paintComponent(Graphics g) {
        //calls the parent class' drawing method to setup for drawing
        super.paintComponent(g);
        t2 = t1;
        t1 = System.currentTimeMillis();
        drawLoop(g, (int)(t1-t2));
        
    }
    
    
    private void drawLoop(Graphics g, int dt) {
        //casts the regular graphics object into the updated 2d graphics object
        Graphics2D g2d = (Graphics2D) g;
        //draws background color and image
        g2d.setColor(new Color(123,133,146));//light grey/blue color
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        g2d.drawImage(startBG,351 + (int)scrollX,0,this);
        
        //changing what to drawy based on the state of the game
        if(gamestate.equals("menu")){
            for(int i = 0; i < menuButtons.length; ++i){
                menuButtons[i].draw(g2d, this);
            }
        }else if(gamestate.equals("playing")){
            if(dt<=0){
                dt = 1;
            }
            
            //scrolling and animation of character (switching through frames)
            scrollX -= 0.3*dt;
            animationFrame+= 0.010*dt;
            if((int)animationFrame >= 4){
                animationFrame = 0;
            }
            
            //what to do if the mouse is held down - jetpack should lift player up
            if(holdEvent){
                if(fallSpeed <= speedControl){
                    fallSpeed += 0.4*dt;
                }
            }else if(heightOffGround > 0){
                if(fallSpeed >= -speedControl){
                    fallSpeed += -0.4*dt;
                }
            }

            //slows down the jetpack speed
            heightOffGround += (fallSpeed/speedControl*dt);
            
            
            //top and bottom barrier prevents character from leaving the screen
            if(heightOffGround < 0){
                heightOffGround = 0;
                fallSpeed = 0;
            }else if(heightOffGround > 400){
                heightOffGround = 400;
                fallSpeed = 0;
            }
            
            //draws the character on the window
            g2d.drawImage(characterFrames[(int)animationFrame],351,(B_HEIGHT-150) - (int)heightOffGround,this);
        }
        
        
        
        
        //synchronizes the graphics
        Toolkit.getDefaultToolkit().sync();
    }
    
    
    
    /**
     * Every action (triggered by the timer) runs this function
     * @param e the event that triggered the function
     */
    @Override
    public void actionPerformed(ActionEvent e) {       
        repaint();
    }
    
    
    /**
     * loads all of the images into the image arrays to store the resources
     */
    private void loadImages() {
        int costumeNum = 3;
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
        ImageIcon iiMusicToggleButton = new ImageIcon(getClass().getResource("imageResources/buttons/tutorialButton.png"));
        ImageIcon iiSFXToggleButton = new ImageIcon(getClass().getResource("imageResources/buttons/creditsButton.png"));
        
        
        //make buttons here 
       
        menuButtons = new MenuButton[4];
        menuButtons[0] = new MenuButton(15, 120, 320, 66, "Redirect", iiStoreButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new StoreGUI(this));
        menuButtons[1] = new MenuButton(15, 210, 320, 66, "Redirect", iiStatsButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new StatisticsGUI(this));
        menuButtons[2] = new MenuButton(15, 300, 320, 66, "Redirect", iiTutorialButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new TutorialGUI(this));
        menuButtons[3] = new MenuButton(15, 390, 320, 66, "Redirect", iiCreditButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new CreditsGUI(this));
//        menuButtons[4] = new MenuButton(15, 480, 320, 66, "MusicToggle", iiMusicToggleButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH));
//        menuButtons[5] = new MenuButton(15, 570, 320, 66, "SFXToggle", iiSFXToggleButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH));



    }
    
    
    
    
    //unused functions in order to implement mouse and keyboard listener
    @Override
    public void mouseExited(MouseEvent e){
    }
    @Override
    public void mouseEntered(MouseEvent e){
    }
    @Override
    public void mouseClicked(MouseEvent e){
    }
    @Override
    public void keyTyped(KeyEvent k){
    } 
    
}

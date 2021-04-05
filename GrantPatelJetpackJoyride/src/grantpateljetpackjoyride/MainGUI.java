/*
 * Ehsan Patel and Colin Grant
 * 26-Mar-2021
 * Main loop of the game that handles all the classes together and allows them to interact with one another
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainGUI extends JPanel implements ActionListener, KeyListener, MouseListener {
    
    //constants for the canvas
    private final int B_WIDTH = 1110;
    private final int B_HEIGHT = 600;
    private final int SPEED = 1;
    
    //keeps the game running and updating
    private Timer timer;
    
    private MediaPlayer mediaPlayer;
    private final Media gameMusic = new Media(getClass().getResource("sounds/gamePlay.mp3").toString());
    private final Media homeMusic = new Media(getClass().getResource("sounds/home.mp3").toString());

    //background image declaration
    private Image startBG;
    private Image mainBG;
    private Image randomBG;
    
    private MenuButton[] menuButtons;
    
    private double scrollX;
    private String gamestate;    
    boolean holdEvent;
    
    private Player player;
    
    private long t1;
    private long t2;
    
    /**
     * primary constructor to build the JPanel and create a window that can be interacted with by the user
     */
    public MainGUI() {

        //initializes the player
        player = new Player();
        
        //initializes the attributes of the board
        initPanel();
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
            
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(gameMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
      
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
        
        //allows the window to recieve keyboard input
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //allows the board to recieve input from mouseclicks
        addMouseListener(this);

        //processing time correction variables
        t1 = 0;
        t2 = 0;
        
        //game variables
        scrollX = 0;
        gamestate = "menu";
        holdEvent = false;
        
        mediaPlayer = new MediaPlayer(homeMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
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
        
        //draws the starting background image if it is within the frame
        if(351 + (int)scrollX + 4509 >0){
            g2d.drawImage(startBG,351 + (int)scrollX,0,this);
        }
        //infinite scrolling backgrounds - not infinite yet
        g2d.drawImage(mainBG,4860 + (int)scrollX,0,this);
        g2d.drawImage(randomBG,6669 + (int)scrollX,0,this);
        
        
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
            
            //controls the player's frame, movement, then draws
            player.nextFrame(0.010*dt);
            player.move(holdEvent, dt);            
            player.draw(g, B_HEIGHT, this);
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
        ImageIcon iiMainBG = new ImageIcon(getClass().getResource("imageResources/mainPanelBG.png"));
        ImageIcon iiRandomBG = new ImageIcon(getClass().getResource("imageResources/randomPanelBG.png"));
        
        startBG = iiStartBG.getImage().getScaledInstance(4509, B_HEIGHT, Image.SCALE_FAST);
        mainBG = iiMainBG.getImage().getScaledInstance(1809, B_HEIGHT, Image.SCALE_FAST);
        randomBG = iiRandomBG.getImage().getScaledInstance(1809, B_HEIGHT, Image.SCALE_FAST);
        
        //menu buttons          
        ImageIcon iiStoreButton = new ImageIcon(getClass().getResource("imageResources/buttons/storeButton.png"));
        ImageIcon iiStatsButton = new ImageIcon(getClass().getResource("imageResources/buttons/statsButton.png"));
        ImageIcon iiTutorialButton = new ImageIcon(getClass().getResource("imageResources/buttons/tutorialButton.png"));
        ImageIcon iiCreditButton = new ImageIcon(getClass().getResource("imageResources/buttons/creditsButton.png"));
        ImageIcon iiMusicToggleButton = new ImageIcon(getClass().getResource("imageResources/buttons/tutorialButton.png"));
        ImageIcon iiSFXToggleButton = new ImageIcon(getClass().getResource("imageResources/buttons/creditsButton.png"));

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

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
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class MainGUI extends JPanel implements ActionListener, KeyListener, MouseListener {
    
    //constants for the canvas
    private final int B_WIDTH = 1110;
    private final int B_HEIGHT = 600;
    private final int SPEED = 10;
    private static String saveAddress;
    
    //keeps the game running and updating
    private Timer timer;


    //background image declaration
    private Image startBG;
    private Image mainBG;
    private Image randomBG;
    
    private MenuButton[] menuButtons;
    
    private int scrollX;
    private String gamestate;    
    boolean holdEvent;
    
    private Player player;
    
    private long t1;
    private long t2;
    
    //music variables
    boolean mainMusicPlaying = false;
    boolean menuMusicPlaying = true;
    private final String filepathMain = "audio/JetpackJoyrideOST-MainTheme.wav";
    private final String filepathMenu = "audio/JetpackJoyrideOST-Home.wav";
    private AudioPlayer audioPlayer;
    
    //arrayLists containing game objects
    ArrayList<AbstractObstacle> obstacles = new ArrayList<AbstractObstacle>();
    //obstacle x position
    private int oXPos;
    
    //speed increase
    private double increase;
    
    
    
    
    /**
     * primary constructor to build the JPanel and create a window that can be interacted with by the user
     */
    public MainGUI(String saveAddress) {
        this.saveAddress = saveAddress;
        
        //initializes the attributes of the board
        initPanel();
        
        //reads the autosaved file and updates game objects
        readAutoSave();
        
        //allows the window to recieve keyboard input
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        //allows the board to recieve input from mouseclicks
        addMouseListener(this);
    }

    
    private void initPanel() {
        //load the image resources to use
        loadImages();
        //setup the canvas
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        
        //creates a timer to update the window
        timer = new Timer(SPEED, this);
        timer.setInitialDelay(100);
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
        oXPos = 1500;
        increase = 1.2;
        
        //play music
        playMusic(filepathMenu); 
        
        //initializes the player
        player = new Player();
        
        //loading obstacle images
        VerticalObstacle.loadImages();
        HorizontalObstacle.loadImages();
        DiagonalObstacle.loadImages();
        
        //randomize the obstacles
        randomizeObstacles();
        
    }
    

    private void drawLoop(Graphics g, int dt) {
        //prevents unexpected dt from changing game function
        if(dt<=0){
            dt = 1;
        }else if(dt > 25){
            dt = 25;
        }
        
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
        
        
        //changing what to draw based on the state of the game
        if(gamestate.equals("menu")){
            for(int i = 0; i < menuButtons.length; ++i){
                menuButtons[i].draw(g2d, this);
            }
        }else if(gamestate.equals("playing")){
            
           
            //scrolling and animation of character (switching through frames)
            scrollX -= 0.3*dt*increase;
            
            //increasing speed
            increase += 0.0002;
            
            //controls the player's frame, movement, then draws
            player.nextFrame(0.010*dt);
            player.move(holdEvent, dt);            
            player.draw(g, B_HEIGHT, this);
            
            
            //checks if first obstacle has already been passed and should be deleted
            if (obstacles.get(0).getXPos() + obstacles.get(0).getWidth() < 0){
                obstacles.remove(0);
            }
            
            //checks if more obstacles need to be added
            if (obstacles.size() <= 5){
                randomizeObstacles();
            }
            
            //moving obstacle frames
            AbstractObstacle.nextFrame(0.010*dt);
            
            //drawing obstacles
            for (int i = 0; i < obstacles.size(); i++) {
                obstacles.get(i).setXPos(obstacles.get(i).getXPos() - (int)(0.3*dt*increase));
                
                //determine which type of obstacle it is in order to cast and draw
                if (obstacles.get(i).getType().equals("vertical")){
                    ((VerticalObstacle)(obstacles.get(i))).draw(this, g);
                } else if (obstacles.get(i).getType().equals("horizontal")){
                    ((HorizontalObstacle)(obstacles.get(i))).draw(this, g);
                } else {
                    ((DiagonalObstacle)(obstacles.get(i))).draw(this, g);
                }
            }   
        }
        
        
        
        //synchronizes the graphics
        Toolkit.getDefaultToolkit().sync();
    }
    
    /**
     * generates an array list of obstacles
     */
    public void randomizeObstacles(){
        int oXPosDiff = oXPos;
        int change = 0;
        //add obstacles to an array
        while (obstacles.size() < 15) {
            if ((int)(Math.random() * 4) + 1 == 1 || change == 240){ //1 in 4 chance of an obstacle appearing
                //determine which type of obstacle
                if((int)(Math.random() * 3) + 1 == 1){
                    obstacles.add(new VerticalObstacle(oXPos, 0, 0, 0, "vertical"));
                } else if((int)(Math.random() * 3) + 1 == 2){
                    obstacles.add(new HorizontalObstacle(oXPos, 0, 0, 0, "horizontal"));
                } else {
                    if ((int)(Math.random() * 2) + 1 == 1){ //obstacle faces left
                        obstacles.add(new DiagonalObstacle(oXPos, 0, 0, 0, "diagonal", true));
                    } else { //obstacle faces right
                        obstacles.add(new DiagonalObstacle(oXPos, 0, 0, 0, "diagonal", false));
                    }
                }
                
                //changing values to set for next obstacle if generating has not finished
                if (obstacles.size() != 15){
                    oXPos += obstacles.get(obstacles.size() - 1).getWidth() + 30;
                    change = 0;
                }
            }
            //if generating has finished, no increment is added
            if (obstacles.size() != 15){
                oXPos += 30;
                change += 30;
            }
        }
        //resetting the obstacle x position 
        oXPosDiff = oXPos - oXPosDiff;
        oXPos = oXPos - oXPosDiff + obstacles.get(9).getWidth();
    }
    
    

    public void playGame(){
        gamestate = "playing";
        audioPlayer.stop();
        playMusic(filepathMain);
        mainMusicPlaying = true;
        menuMusicPlaying = false;
    }
    
    public void endRun(){
        //display score on a rectangle that covers the screen
        //stop frames from moving
        //reset all game variables
        //add coins to counter
        //add score to highscores txt file
        //save coins to achievements file
        //save any other settings
    }
    
        
    
    
    //mouse and keyboard functions
    @Override
    public void mousePressed(MouseEvent e) {
        //what to do when mouse is clicked
        if(gamestate.equals("menu")){
            for(int i = 0; i < menuButtons.length; ++i){
                if(menuButtons[i].buttonAction(e.getX(), e.getY(), this)){
                    return;
                }   
            }
            playGame();
            
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
                playGame(); 

            }else if(gamestate.equals("playing")){
                holdEvent = true;
            }
        }
    }
    
    /**
     * Reads the autosave file and changes the game setup accordingly
     * @param saveAddress - where the root folder of the save files is located
     */
    private void readAutoSave(){
        ArrayList<String> autosaveContents = new ArrayList();
        //reads the save file
        try {
            //new input stream for the auto save file
            FileInputStream in = new FileInputStream(saveAddress + "autosave.jjrs");
            //scanner to read the input stream
            Scanner scanner = new Scanner(in);
            
            //adds contents of file into arraylist
            while(scanner.hasNextLine()){
                autosaveContents.add(scanner.nextLine());                
            }
            
            //linear search through auto save file contents - different number of bought costumes will affect which line items are on
            for(int i = 0; i < autosaveContents.size(); ++i){
                //finds the equipped costume line in autosave
                if(autosaveContents.get(i).equals("Equipped Costume")){
                    //The next line is the costume to use, so set the players costume
                    player.setCostumeNum(Integer.parseInt(autosaveContents.get(i+1)));
                    //reload the correct images
                    player.loadImages();
                }
                
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void writeToAutoSave(){
        //writes to the file
    }

    
    /**
     * plays the music using the audioplayer object
     * @param filepath - the path to the audio file to play
     */
    public void playMusic(String filepath){
        try{
            audioPlayer = new AudioPlayer(filepath);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
        /**
     * overrides the draw method to draw custom items on the window
     * @param g - the tool to draw graphics on the window
     */
    @Override
    public void paintComponent(Graphics g) {
        //calls the parent class' drawing method to setup for drawing
        super.paintComponent(g);
        t2 = t1;
        t1 = System.currentTimeMillis();
        drawLoop(g, (int)(t1-t2));
        
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
        menuButtons[0] = new MenuButton(15, 120, 320, 66, "Redirect", iiStoreButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new StoreGUI(this,saveAddress));
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

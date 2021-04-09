/*
 * Ehsan Patel and Colin Grant
 * 26-Mar-2021
 * Main loop of the game that handles all the classes together and allows them to interact with one another
 */
package grantpateljetpackjoyride;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class MainGUI extends JPanel implements ActionListener, KeyListener, MouseListener {
    
    //constants for the canvas
    private final int B_WIDTH = 1110;
    private final int B_HEIGHT = 600;
    private final int SPEED = 10;
    private String saveAddress;
    
    //keeps the game running and updating
    private Timer timer;


    //background image declaration
    private Image startBG;
    private Image panel1;
    private Image panel2;
    
    //music and sfx toggle images
    private Image MusicToggleOn;
    private Image MusicToggleOff;
    private Image SFXToggleOn;
    private Image SFXToggleOff;
    
    //menu buttons for redirecting
    private MenuButton[] menuButtons;
    
    //panel x positions
    private int[] panelScrollX;
    private int startingBGX;
    
    //variable for what stage of the game
    private String gamestate;    
    
    //if the user is holding down mouse or spacebar
    boolean holdEvent;
    
    //the player
    private Player player;
    
    //for calculating delta time
    private long t1;
    private long t2;
    
    //music variables
    private boolean isMusicOn;
    private boolean isSFXOn;
    private final String filepathMain = "audio/JetpackJoyrideOST-MainTheme.wav";
    private final String filepathMenu = "audio/JetpackJoyrideOST-Home.wav";
    private final String filepathCoin = "audio/coin.wav";
    private final String filepathZapper = "audio/zapper.wav";
    private AudioPlayer audioPlayer;
    private AudioPlayer sfxPlayer;
    
    //arrayLists containing game objects
    ArrayList<AbstractObstacle> obstacles = new ArrayList();
    ArrayList<Coin> coins = new ArrayList();
    
    //game object x position
    private int oXPos;
    private int cXPos;
    
    //speed increase
    private double increase;
    
    //collision boolean variables
    private boolean cCollision, oCollision;
    
    //input stream to read the font file
    private static InputStream is = LoadingGUI.class.getResourceAsStream("fonts/Abel-Regular.ttf");
    //font variables
    private Font abel;
    private static Font scaledAbel1, scaledAbel2, scaledAbel3;
    private FontMetrics metrics, metricsAbel3;
    
    //arraylist for accessing saved content
    private ArrayList<String> autosaveContents = new ArrayList();

    
    
    /**
     * primary constructor to build the JPanel and create a window that can be interacted with by the user
     */
    public MainGUI(String saveAddress) {
        this.saveAddress = saveAddress;
        
        //initializes the attributes of the board
        initPanel();
        
        //reads the autosaved file and updates game objects
        readAutoSave();        
    }

    /**
     * initializes variables listeners and timers that would be in constructor
     */
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
        
        //stores the background panel x positions
        panelScrollX = new int[2];
        for(int i = 0; i < panelScrollX.length; ++i){
            panelScrollX[i] = 4860+ i*1809;
        }
        startingBGX = 0;
        
        //start the game at the main menu
        gamestate = "menu";
        
        //start with the player not holding down
        holdEvent = false;
        
        //start the obstacles and coins offscreen
        oXPos = 1500;
        cXPos = 1500;
        //increase the speed of the game
        increase = 1.2;
        
        //start with no collisions
        cCollision = false;
        oCollision = false;
        
        //initializes the player
        player = new Player();
        
        //loading game object images
        VerticalObstacle.loadImages();
        HorizontalObstacle.loadImages();
        DiagonalObstacle.loadImages();
        Coin.loadImages();
        
        
        //loads in a font to use for the text
        try{
            this.abel = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException | IOException e){
            System.out.println(e);
        }
        
        //sets fonts
        scaledAbel1 = abel.deriveFont(80f);
        scaledAbel2 = abel.deriveFont(50f);
        scaledAbel3 = abel.deriveFont(60f);
        
    }
    

    /**
     * draws to the main screen
     * @param g the graphics
     * @param dt the delta time used to adjust for slower/faster computer speeds
     */
    private void drawLoop(Graphics g, int dt) {
        //prevents unexpected dt from changing game function
        if(dt<=0){
            dt = 1;
        //caps the dt at 20
        }else if(dt > 20){
            dt = 20;
        }
        
        //casts the regular graphics object into the updated 2d graphics object
        Graphics2D g2d = (Graphics2D) g;
        
        //draws background color and image
        g2d.setColor(new Color(123,133,146));//light grey/blue color
        g2d.fillRect(0, 0, B_WIDTH, B_HEIGHT);
        
        //draws the starting background image if it is within the frame
        if(351 + (int)startingBGX + 4509 >0){
            g2d.drawImage(startBG,351 + (int)startingBGX,0,this);
        }
        
        //changing what to draw based on the state of the game
        if(gamestate.equals("menu")){
            //default value for metrics if it isn't added later
            metrics = g2d.getFontMetrics(scaledAbel1);
            
            //draws all menu buttons in array
            for(int i = 0; i < menuButtons.length; ++i){
                menuButtons[i].draw(g2d, this);
            }
            
            //selects which music and SFX images to display based on if the user is listening or not
            if(isMusicOn){
                g2d.drawImage(MusicToggleOn, 50, 480, this);
            }else{
                g2d.drawImage(MusicToggleOff, 50, 480, this);
            }
            if(isSFXOn){
                g2d.drawImage(SFXToggleOn, 180, 480, this);
            }else{
                g2d.drawImage(SFXToggleOff, 180, 480, this);
            }

        //if the gamestate is in the actual game mode
        }else if(gamestate.equals("playing")){
            //infinite scrolling backgrounds are drawn
            g2d.drawImage(panel1,(int)panelScrollX[0]-10,0,this);
            g2d.drawImage(panel2,(int)panelScrollX[1]-10,0,this);
            
            //increasing speed
            increase += 0.0002;
            
            //scrolling starting background
            startingBGX -= 0.3*dt*increase;

            //scrolls all the background panels
            for(int i = 0; i < panelScrollX.length; ++i){
                panelScrollX[i] -= (int)(0.3*dt*increase);
                if(panelScrollX[i] <= -1809){
                    panelScrollX[i] += 2*1809;
                }
            }


            
            //scrolling and animation of character (switching through frames)
            //controls the player's frame, movement, then draws
            player.nextFrame(0.010*dt);
            player.move(holdEvent, dt);            
            player.draw(this, g);
            
            
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
                
                //determine which type of obstacle it is in order to cast and draw and checks collisions
                if (obstacles.get(i).getType().equals("vertical")){
                    oCollision = obstacles.get(i).notDiagonalOCollisions(player);
                    ((VerticalObstacle)(obstacles.get(i))).draw(this, g);
                    
                } else if (obstacles.get(i).getType().equals("horizontal")){
                    oCollision = obstacles.get(i).notDiagonalOCollisions(player);
                    ((HorizontalObstacle)(obstacles.get(i))).draw(this, g);
                    
                } else {
                    oCollision = ((DiagonalObstacle)(obstacles.get(i))).hasCollided(player);
                    ((DiagonalObstacle)(obstacles.get(i))).draw(this, g);
                }
                
                //if the player has collided with the obstacle
                if (oCollision){
                    //switch the game state to the end screen so nothing in the gameplay state is drawn
                    gamestate = "gameover";
                    
                    //stops all audio players
                    if(audioPlayer != null){
                        audioPlayer.stop();
                    }
                    //plays the zap sound
                    playSFX(filepathZapper);

                    //wait before going to end screen
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //closes the audio player that was opened
                    if(sfxPlayer != null){
                        sfxPlayer.stop();
                    }
                }
            }   
            
            
            
            //drawing all the coins in the coin arraylist
            for (int i = 0; i < coins.size(); i++) {
                coins.get(i).setXPos(coins.get(i).getXPos() - (int)(0.3*dt*increase));
                //check if coins have collided with player
                cCollision = coins.get(i).coinCollisions(player);
                if (!(cCollision)){ //if the coin has not been hit, draw the coin
                    coins.get(i).draw(this, g);
                } else { //remove the coin
                    playSFX(filepathCoin);
                    coins.remove(i);
                    player.updateTotalCoins();
                }
            }
            
            //checks if coins need to be removed
            if (coins.get(0).getXPos() + coins.get(0).getWidth() < 0){
                coins.remove(0);
            }

            //checks if more coins need to be added
            if (coins.size() <= 10){
                randomizeCoins();
            }
            
        } else if (gamestate.equals("gameover")){ //if the player has died
            //draw a rectangle that covers the screen
            g2d.setColor(new Color(123,133,146));
            g2d.fillRect(0, 0, B_WIDTH + 20, B_HEIGHT + 20);
            
            //displaying statistics from run
            //setting font and color
            g2d.setColor(Color.white);
            g2d.setFont(scaledAbel1);
            metrics = g2d.getFontMetrics(scaledAbel1);
            
            //drawing text to screen
            g2d.drawString("You died", (B_WIDTH - metrics.stringWidth("You died")) / 2 , 150);
            
            //changing font
            g2d.setFont(scaledAbel2);
            metrics = g2d.getFontMetrics(scaledAbel2);
            
            //drawing run stats
            g2d.drawString("Score: " + (int)(player.getScore()), (B_WIDTH - metrics.stringWidth("Score: " + (int)(player.getScore()))) / 2, 250);
            g2d.drawString("Coins: " + player.getCoins(), (B_WIDTH - metrics.stringWidth("Coins: " + player.getCoins())) / 2, 350);
            
            //changing font
            g2d.setFont(scaledAbel3);
            metrics = g2d.getFontMetrics(scaledAbel3);
            metricsAbel3 = g2d.getFontMetrics(scaledAbel3);
            
            //drawing buttons for user to click on
            g2d.drawString("Main Menu", (B_WIDTH - metrics.stringWidth("Main Menu")) / 4, 500);
            g2d.drawString("Play Again", ((B_WIDTH - metrics.stringWidth("Play Again")) / 4) * 3, 500);
            
        }

        //synchronizes the graphics
        Toolkit.getDefaultToolkit().sync();
    }
    
    
    /**
     * generates an arrayList of coins
     */
    public void randomizeCoins(){
        //add coins to the array list of coins
        for (int i = 0; i < 20; i++) { //add 10 coins to the list
            coins.add(new Coin(cXPos, 0, 40, 40, 1));
            //moves the position where a coin can be placed over
            cXPos += 175;
        }
        //resets coin potential position
        cXPos = 1875;

    }
    
    /**
     * generates an array list of obstacles
     */
    public void randomizeObstacles(){
        //initialize with starting values
        int oXPosDiff = 0;
        int change = 0;
        //add obstacles to an array
        while (obstacles.size() < 15) {
            if ((int)(Math.random() * 5) + 1 == 1 || change == 400){ //1 in 5 chance of an obstacle appearing each 100 pixels, always generates after 400 pixels
                //determine which type of obstacle and adds a new one to the arraylist
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
                oXPos += 100;
                change += 100;
            }
            
        }
        //loops obstacles 10-14
        for (int i = 9; i < 14; i++) {
            //adds to the change between the one after - the current x
            oXPosDiff += obstacles.get(i + 1).getXPos() - obstacles.get(i).getXPos();
        }
        
        //reset the xpos to the new difference
        oXPos = oXPosDiff + 100;
    
    }
    
    
    /**
     * what to do when the user clicks to play the game
     */
    public void playGame(){
        //suggests running java garbage collector to free memory
        System.gc();
        //read the save file for the costume changes
        readAutoSave();
        //change gamestate to update what to draw
        gamestate = "playing";
        
        //play music
        playMusic(filepathMain);
        
        //randomize the game objects
        randomizeObstacles();
        randomizeCoins();
        
    }
    
    /**
     * what to do at the end of the run
     */
    public void endRun(){
        //suggests using java garbage collector to free memory
        System.gc();
        //write all game statistics to files
        addCoinsToSave(player.getCoins());
        appendToSaveFile("allCoins.jjrs", player.getCoins());
        appendToSaveFile("allScores.jjrs", (int)player.getScore());
        
        //resetting game variables
        startingBGX = 0;
        holdEvent = false;
        oXPos = 1500;
        cXPos = 1500;
        increase = 1.2;
        cCollision = false;
        oCollision = false;
        for(int i = 0; i < panelScrollX.length; ++i){
            panelScrollX[i] = 4860+ i*1809;
        }
        
        //remove all obstacles and coins
        obstacles.clear();
        coins.clear();
        
        //making new player object
        player = new Player();
    }
    
        
    
    
    //mouse and keyboard functions
    
    /**
     * What to do when the user presses a mouse button
     * @param e - the click event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //if the click happens in the menu screen
        if(gamestate.equals("menu")){
            //check the menu buttons for clicks
            for(int i = 0; i < menuButtons.length; ++i){
                if(menuButtons[i].buttonAction(e.getX(), e.getY(), this)){
                    return;//prevents starting game
                }   
            }
            //checks the music toggle
            if(e.getX() > 50 && e.getX() < 170 && e.getY() > 480 && e.getY() < 600){
                //toggles music
                isMusicOn = !isMusicOn;
                playMusic(filepathMenu);
                //updates save file
                soundAutoSave();
                return;//prevents starting game
            //checks the sfx toggle
            }else if(e.getX() > 170 && e.getX() < 290 && e.getY() > 480 && e.getY() < 600){
                isSFXOn = !isSFXOn;
                //updates the save file
                soundAutoSave();
                return;//prevents starting game
            }
            //plays the game if nothing else was clicked and exits out before
            playGame();
            
        //if the game is being played, each click triggers the jetpack
        }else if(gamestate.equals("playing")){
            holdEvent = true;
        } 
    }
    
    /**
     * If the mouse is released
     * @param e - the click event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //what to do when mouse is released in gameplay
        if(gamestate.equals("playing")){
            //stops jetpack
            holdEvent = false;
        //if the mouse is released on the gameover screen
        } else if (gamestate.equals("gameover")){
            //check if mouse is in correct y space
            if (e.getY() > 465 && e.getY() < 535){
                //check if mouse click is on either button
                if(metrics == null){
                    return;
                }
                //checks of the menu button was clicked
                if (e.getX() > (B_WIDTH - metrics.stringWidth("Main Menu")) / 4  - 5 && e.getX() < ((B_WIDTH - metrics.stringWidth("Main Menu")) / 4) + metrics.stringWidth("Main Menu") + 5){
                    //menu button was clicked
                    endRun(); //reset variables and write to file
                    gamestate = "menu";
                    playMusic(filepathMenu);
                    
                //checks if the play again button was clicked
                } else if (e.getX() > ((B_WIDTH - metrics.stringWidth("Play Again")) / 4) * 3 - 5 && e.getX() < ((B_WIDTH - metrics.stringWidth("Play Again")) / 4) * 3 + (B_WIDTH - metrics.stringWidth("Play Again")) + 5){
                    endRun();
                    playGame(); //restart game
                    
                }
            }
            
        }
    }

    
    //functions to implement keylistener
    /**
     * event for if a key was released
     * @param k - the key event
     */
    @Override
    public void keyReleased(KeyEvent k){
        //what to do when a key is released
        if(k.getKeyChar() == ' '){//checks if the key was the spacebar
            if (gamestate.equals("playing")) {
                holdEvent = false;
            }
        }
    }
    
    /**
     * event that is triggered upon a key being pressed
     * @param k - the key event
     */
    @Override
    public void keyPressed(KeyEvent k){
        //if the spacebar was pressed
        if(k.getKeyChar() == ' '){
            //if the game was on the menu screen
            if(gamestate.equals("menu")){
                playGame(); 
            //if the game was on the play screen
            }else if(gamestate.equals("playing")){
                holdEvent = true;
            }
        }
    }
    
    /**
     * Reads the autosave file and changes the game setup accordingly
     */
    private void readAutoSave(){
        //clears previous contents
        autosaveContents.clear();
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
                //checks for the music header
                if(autosaveContents.get(i).equals("Music")){
                    //toggles the music based on the value
                    if(autosaveContents.get(i+1).equals("on")){
                        isMusicOn = true;
                        playMusic(filepathMenu); 
                    }else{
                        isMusicOn = false;
                    }
                    
                //finds the equipped costume line in autosave
                }else if(autosaveContents.get(i).equals("Equipped Costume")){
                    //The next line is the costume to use, so set the players costume
                    player.setCostumeNum(Integer.parseInt(autosaveContents.get(i+1)));
                    //reload the correct images
                    player.loadImages();
                //finds the sfx header
                }else if(autosaveContents.get(i).equals("SFX")){
                    //toggles the sfx being on based on the value
                    if(autosaveContents.get(i+1).equals("on")){
                        isSFXOn = true;
                        playMusic(filepathMenu); 
                    }else{
                        isSFXOn = false;
                    }
                }
            }
            //closes the scanner and its input stream
            scanner.close();
            in.close();
            
        //catches if the file couldn't be read
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * adds the coins from the run to the save file
     * @param newCoins - the coins to add
     */
    private void addCoinsToSave(int newCoins){
        //attempts to write to file
        try{
            //selects the autosave file
            FileWriter myWriter = new FileWriter(saveAddress+"autosave.jjrs");
            //stores the unchanged autosave properties
            String restOfAutoSave = "";
            //adds properties after the 6th line to the storage variable
            for(int i = 6; i < autosaveContents.size(); ++i){
                restOfAutoSave += autosaveContents.get(i) + "\n";
            }
            //stores the music and sfx variables
            String music = "off";
            String sfx = "off";
            //convert to on/off from true/false
            if(isMusicOn){
                music = "on";
            }
            if(isSFXOn){
                sfx = "on";
            }
            //gets the old amount of coins from the save file
            int oldCoins = Integer.parseInt(autosaveContents.get(5));
            //writes to the file with the updated values
            myWriter.write("Music\n"+music+"\nSFX\n"+sfx+"\nCoins\n"+(oldCoins+newCoins)+"\n"+restOfAutoSave.trim());
            //closes the writer
            myWriter.close();
            
        //displays if the file couldn't be written to
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * writes to the autosave about the music, the 
     */
    private void soundAutoSave(){
        //attempts to open the file
        try{
            //opens file autosave.jjrs
            FileWriter myWriter = new FileWriter(saveAddress+"autosave.jjrs");
            //stores the unused properties
            String restOfAutoSave = "";
            //iterates through the unused portion
            for(int i = 4; i < autosaveContents.size(); ++i){
                restOfAutoSave += autosaveContents.get(i) + "\n";
            }
            //converts true/false from on/off
            String music = "off";
            String sfx = "off";
            if(isMusicOn){
                music = "on";
            }
            if(isSFXOn){
                sfx = "on";
            }
            //writes changes to the file
            myWriter.write("Music\n"+music+"\nSFX\n"+sfx+"\n"+restOfAutoSave.trim());
            myWriter.close();
        //displays if there was an error
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * adds a score or coins collected in a run to the end of a file
     * @param filename - which file to append to
     * @param value - the value to add to the end of the file
     */
    private void appendToSaveFile(String filename, int value){
        //attempts to open the file
        try{
            //opens filewriter in append mode
            FileWriter myWriter = new FileWriter(saveAddress+filename, true);
            //writes the value and a newline
            myWriter.write(value+"\n");
            //closes the file
            myWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * plays the music using the audioplayer object
     * @param filepath - the path to the audio file to play
     */
    private void playMusic(String filepath){
        //stops current player
        if(audioPlayer != null){
            audioPlayer.stop();
        }
        //plays if the music is toggled on
        if(isMusicOn){
            try{
                audioPlayer = new AudioPlayer(filepath, true);

            //shows error otherwise
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        
    }
    
    /**
     * plays the sfx using the audioplayer object
     */
    private void playSFX(String filepath){
        //stops current player
        if(sfxPlayer != null){
            sfxPlayer.stop();
        }
        //plays sfx if the sfx is toggled on
        if(isSFXOn){
            try{
                sfxPlayer = new AudioPlayer(filepath, false);
            //output the error
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
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
        //stores the last frame time
        t2 = t1;
        //gets the current frame time
        t1 = System.currentTimeMillis();
        //draws the game depending on the state
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
        
        //converts image icons to images to store
        startBG = iiStartBG.getImage().getScaledInstance(4509, B_HEIGHT, Image.SCALE_FAST);
        panel1 = iiMainBG.getImage().getScaledInstance(1809, B_HEIGHT, Image.SCALE_FAST);
        panel2 = iiRandomBG.getImage().getScaledInstance(1809, B_HEIGHT, Image.SCALE_FAST);
        
        //menu button image icons are loaded
        ImageIcon iiStoreButton = new ImageIcon(getClass().getResource("imageResources/buttons/storeButton.png"));
        ImageIcon iiStatsButton = new ImageIcon(getClass().getResource("imageResources/buttons/statsButton.png"));
        ImageIcon iiTutorialButton = new ImageIcon(getClass().getResource("imageResources/buttons/tutorialButton.png"));
        ImageIcon iiCreditButton = new ImageIcon(getClass().getResource("imageResources/buttons/creditsButton.png"));
        
        //music toggle button icons
        ImageIcon iiMusicToggleOff = new ImageIcon(getClass().getResource("imageResources/buttons/musicToggleOff.png"));
        ImageIcon iiMusicToggleOn = new ImageIcon(getClass().getResource("imageResources/buttons/musicToggleOn.png"));
        ImageIcon iiSFXToggleOn = new ImageIcon(getClass().getResource("imageResources/buttons/SFXToggleOn.png"));
        ImageIcon iiSFXToggleOff = new ImageIcon(getClass().getResource("imageResources/buttons/SFXToggleOff.png"));

        //create an array of menu buttons using the icons
        menuButtons = new MenuButton[4];
        menuButtons[0] = new MenuButton(15, 120, 320, 66, "Redirect", iiStoreButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new StoreGUI(this,saveAddress));
        menuButtons[1] = new MenuButton(15, 210, 320, 66, "Redirect", iiStatsButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new StatisticsGUI(this,saveAddress));
        menuButtons[2] = new MenuButton(15, 300, 320, 66, "Redirect", iiTutorialButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new TutorialGUI(this));
        menuButtons[3] = new MenuButton(15, 390, 320, 66, "Redirect", iiCreditButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new CreditsGUI(this));
        
        //music toggle button images
        SFXToggleOff = iiSFXToggleOff.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        SFXToggleOn = iiSFXToggleOn.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        MusicToggleOff = iiMusicToggleOff.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        MusicToggleOn = iiMusicToggleOn.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);

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

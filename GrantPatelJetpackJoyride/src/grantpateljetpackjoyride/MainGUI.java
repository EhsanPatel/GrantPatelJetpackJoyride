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
    
    private MenuButton[] menuButtons;
    
    private int[] panelScrollX;
    private int startingBGX;
    private String gamestate;    
    boolean holdEvent;
    
    private Player player;
    
    private long t1;
    private long t2;
    
    //music variables
    private boolean isMusicOn;
    boolean mainMusicPlaying = false;
    boolean menuMusicPlaying = true;
    private final String filepathMain = "audio/JetpackJoyrideOST-MainTheme.wav";
    private final String filepathMenu = "audio/JetpackJoyrideOST-Home.wav";
    private final String filepathCoin = "audio/coin.wav";
    private final String filepathZapper = "audio/zapper.wav";
    private AudioPlayer audioPlayer;
    private AudioPlayer coinAudio;
    private AudioPlayer zapperAudio;
    
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
        
        panelScrollX = new int[2];
        //game variables
        for(int i = 0; i < panelScrollX.length; ++i){
            panelScrollX[i] = 4860+ i*1809;
        }
        
        startingBGX = 0;
        gamestate = "menu";
        holdEvent = false;
        oXPos = 1500;
        cXPos = 1500;
        increase = 1.2;
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
            for(int i = 0; i < menuButtons.length; ++i){
                menuButtons[i].draw(g2d, this);
            }
        }else if(gamestate.equals("playing")){
            //infinite scrolling backgrounds - not infinite yet
            g2d.drawImage(panel1,(int)panelScrollX[0]-10,0,this);
            g2d.drawImage(panel2,(int)panelScrollX[1]-10,0,this);
            
            //increasing speed
            increase += 0.0002;
            
            //scrolling starting background
            startingBGX -= 0.3*dt*increase;

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
                
                if (oCollision){ //if the player has collided with the obstacle
                        gamestate = "gameover";
                        audioPlayer.stop();
                        playSFX(zapperAudio, filepathZapper);
                        //wait before going to end screen
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }   
            
            
            
            //drawing coins
            for (int i = 0; i < coins.size(); i++) {
                coins.get(i).setXPos(coins.get(i).getXPos() - (int)(0.3*dt*increase));
                //check if coins have collided with player
                cCollision = coins.get(i).coinCollisions(player);
                if (!(cCollision)){ //if the coin has not been hit, draw the coin
                    coins.get(i).draw(this, g);
                } else { //remove the coin
                    playSFX(coinAudio, filepathCoin);
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
            cXPos += 175;
        }
        
        cXPos = 1875;

    }
    
    /**
     * generates an array list of obstacles
     */
    public void randomizeObstacles(){
        int oXPosDiff = 0;
        int change = 0;
        //add obstacles to an array
        while (obstacles.size() < 15) {
            if ((int)(Math.random() * 5) + 1 == 1 || change == 400){ //1 in 5 chance of an obstacle appearing each 100 pixels, always generates after 400 pixels
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
                oXPos += 100;
                change += 100;
            }
            
        }
        for (int i = 9; i < 14; i++) {
            oXPosDiff += obstacles.get(i + 1).getXPos() - obstacles.get(i).getXPos();
        }
        
        oXPos = oXPosDiff + 100;
    
    }
    
    

    public void playGame(){
        readAutoSave();
        gamestate = "playing";
        //play music
        if(isMusicOn){
            playMusic(filepathMain);
            mainMusicPlaying = true;
            menuMusicPlaying = false;
        }
        
        //randomize the game objects
        randomizeObstacles();
        randomizeCoins();
        
    }
    
    public void endRun(){
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
        
        //write all game statistics to files
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
        } else if (gamestate.equals("gameover")){
            //check if mouse is in correct y space
            if (e.getY() > 465 && e.getY() < 535){
                //check if mouse click is on either button
                if (e.getX() > (B_WIDTH - metrics.stringWidth("Main Menu")) / 4  - 5 && e.getX() < ((B_WIDTH - metrics.stringWidth("Main Menu")) / 4) + metrics.stringWidth("Main Menu") + 5){
                    //menu button was clicked
                    endRun(); //reset variables and write to file
                    gamestate = "menu";
                    playMusic(filepathMenu);
                } else if (e.getX() > ((B_WIDTH - metrics.stringWidth("Play Again")) / 4) * 3 - 5 && e.getX() < ((B_WIDTH - metrics.stringWidth("Play Again")) / 4) * 3 + (B_WIDTH - metrics.stringWidth("Play Again")) + 5){
                    endRun();
                    playGame(); //restart game
                    
                }
            }
            
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
                if(autosaveContents.get(i).equals("Music"))
                    if(autosaveContents.get(i+1).equals("on")){
                        isMusicOn = true;
                        playMusic(filepathMenu); 
                    }else{
                        isMusicOn = false;
                    }
                    
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
        if(audioPlayer != null){
            audioPlayer.stop();
        }
        try{
            audioPlayer = new AudioPlayer(filepath, true);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    /**
     * plays the sfx using the audioplayer object
     * @param audioPlayer - which sfx object to use
     */
    public void playSFX(AudioPlayer audioPlayer, String filepath){
        if(audioPlayer != null){
            audioPlayer.stop();
        }
        try{
            audioPlayer = new AudioPlayer(filepath, false);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    /**
     * plays the music using the audioplayer object
     * @param audioPlayer
     */
    public void stopSFX(AudioPlayer audioPlayer){
        if(audioPlayer != null){
            audioPlayer.stop();
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
        panel1 = iiMainBG.getImage().getScaledInstance(1809, B_HEIGHT, Image.SCALE_FAST);
        panel2 = iiRandomBG.getImage().getScaledInstance(1809, B_HEIGHT, Image.SCALE_FAST);
        
        //menu buttons          
        ImageIcon iiStoreButton = new ImageIcon(getClass().getResource("imageResources/buttons/storeButton.png"));
        ImageIcon iiStatsButton = new ImageIcon(getClass().getResource("imageResources/buttons/statsButton.png"));
        ImageIcon iiTutorialButton = new ImageIcon(getClass().getResource("imageResources/buttons/tutorialButton.png"));
        ImageIcon iiCreditButton = new ImageIcon(getClass().getResource("imageResources/buttons/creditsButton.png"));
        ImageIcon iiMusicToggleButton = new ImageIcon(getClass().getResource("imageResources/buttons/tutorialButton.png"));
        ImageIcon iiSFXToggleButton = new ImageIcon(getClass().getResource("imageResources/buttons/creditsButton.png"));

        menuButtons = new MenuButton[4];
        menuButtons[0] = new MenuButton(15, 120, 320, 66, "Redirect", iiStoreButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new StoreGUI(this,saveAddress));
        menuButtons[1] = new MenuButton(15, 210, 320, 66, "Redirect", iiStatsButton.getImage().getScaledInstance(320, 66, Image.SCALE_SMOOTH), new StatisticsGUI(this,saveAddress));
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

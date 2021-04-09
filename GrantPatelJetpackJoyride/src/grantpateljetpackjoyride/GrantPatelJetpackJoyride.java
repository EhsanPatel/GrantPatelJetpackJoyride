/*
 * Ehsan Patel and Colin Grant
 * 26-Mar-2021
 * The Jframe that holds the main game jpanel and handles directory and file creation
 */
package grantpateljetpackjoyride;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Creates the JFrame to use
 */
public class GrantPatelJetpackJoyride extends JFrame {

    /**
     * primary constructor for JFrame
     */
    public GrantPatelJetpackJoyride() {
        //initializes the components
        initUI();
    }
    
    /**
     * initializes the components on creation of the JFrame window
     */
    private void initUI() {
        //finds the directory for the user's home and then goes to Documents then Jetpack Joyride
        String saveAddress = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Jetpack Joyride" + File.separator;
        //finds the directory above and creates if necessary
        findSaveDirectory(saveAddress);
        //finds files and creates if necessary
        findSaveFile(saveAddress, "autosave.jjrs");
        findSaveFile(saveAddress, "allCoins.jjrs");
        findSaveFile(saveAddress, "allScores.jjrs");
        //verify that the save file is up to date, recreate if broken
        verifyAutoSave(saveAddress);
        
        //adds the mainGUI panel to the form
        add(new MainGUI(saveAddress));
        
        //Window properties are set
        setResizable(false);
        pack();
        setTitle("Jetpack Joyride");
        ImageIcon icon = new ImageIcon(getClass().getResource("imageResources/costume1/running2.png"));
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * finds the save directory at the folder to store the save files
     * @param saveAddress 
     */
    private void findSaveDirectory(String saveAddress){
        //searches for directory and creates one if necessary
        File file = new File(saveAddress);
        if (!file.exists()) {
            //creates then outputs (system out for testing, not something the user would need to see)
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }
    
    /**
     * finds or creates the save files
     * @param saveAddress - the location to put the save file
     * @param filename - what to call the save file
     */
    private void findSaveFile(String saveAddress, String filename){        
        
        //searches for a file and creates one if necessary
        try {
            //creates a file object at the address
            File myObj = new File(saveAddress+filename);
            //creates file
            if (myObj.createNewFile()) {
                //names the file
              if(filename.equals("autosave.jjrs")){
                  //writes in default values to start
                  writeSaveDefaults(saveAddress);
              }
            }
          //output error (system out for testing)
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    /**
     * Writes to the autosave file with the default values that a player should start with
     * @param saveAddress 
     */
    private void writeSaveDefaults(String saveAddress){
        //tries to write to the file
        try{
            //creates an object to write
            FileWriter myWriter = new FileWriter(saveAddress+"autosave.jjrs");
            //writes default values
            myWriter.write("Music\non\nSFX\non\nCoins\n0\nBought Costumes\n1\nEquipped Costume\n1");
            //closes writer
            myWriter.close();
        //show error if the file couldn't be written to
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * Reads the autosave file and changes the game setup accordingly
     * @param saveAddress - where the root folder of the save files is located
     */
    private void verifyAutoSave(String saveAddress){
        //stores the contents
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
            
            //stors the number of properties
            int savePropertyCount = 0;
            //linear search through auto save file contents - different number of bought costumes will affect which line items are on
            for(int i = 0; i < autosaveContents.size(); ++i){
                if(autosaveContents.get(i).equals("Music")
                    || autosaveContents.get(i).equals("SFX")
                    || autosaveContents.get(i).equals("Coins")
                    || autosaveContents.get(i).equals("Bought Costumes")
                    || autosaveContents.get(i).equals("Equipped Costume"))
                {
                    savePropertyCount++;
                }
            }
            //checks for the correct number of the properties
            if(savePropertyCount != 5){
                //fixes file if broken
                writeSaveDefaults(saveAddress);
            }
            //closes resources
            scanner.close();
            in.close();
        //dislpays if there was an error finding file
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
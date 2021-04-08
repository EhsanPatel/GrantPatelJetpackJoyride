/*
 * Ehsan Patel and Colin Grant
 * 26-Mar-2021
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GrantPatelJetpackJoyride extends JFrame {

    public GrantPatelJetpackJoyride() {
        
        initUI();
    }
    
    private void initUI() {
        String saveAddress = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Jetpack Joyride" + File.separator;
        findSaveDirectory(saveAddress);
        findSaveFile(saveAddress, "autosave.jjrs");
        findSaveFile(saveAddress, "allCoins.jjrs");
        findSaveFile(saveAddress, "allScores.jjrs");
        verifyAutoSave(saveAddress);
        add(new MainGUI(saveAddress));
        
        setResizable(false);
        pack();
        
        setTitle("Jetpack Joyride");
        ImageIcon icon = new ImageIcon(getClass().getResource("imageResources/costume1/running2.png"));
        setIconImage(icon.getImage());
        
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void findSaveDirectory(String saveAddress){
        //searches for directory and creates one if necessary
        File file = new File(saveAddress);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }
    

    private void findSaveFile(String saveAddress, String filename){        
//        System.out.println(saveAddress+filename);
        
        //searches for a file and creates one if necessary
        try {
            File myObj = new File(saveAddress+filename);
            if (myObj.createNewFile()) {
              if(filename.equals("autosave.jjrs")){
                  writeSaveDefaults(saveAddress);
              }
            } else {
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void writeSaveDefaults(String saveAddress){
        try{
            FileWriter myWriter = new FileWriter(saveAddress+"autosave.jjrs");
            myWriter.write("Music\non\nSFX\non\nCoins\n0\nBought Costumes\n1\nEquipped Costume\n1");
            myWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /**
     * Reads the autosave file and changes the game setup accordingly
     * @param saveAddress - where the root folder of the save files is located
     */
    private void verifyAutoSave(String saveAddress){
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
            
            int savePropertyCount = 0;
            //linear search through auto save file contents - different number of bought costumes will affect which line items are on
            for(int i = 0; i < autosaveContents.size(); ++i){
                if(autosaveContents.get(i).equals("Music")
                    || autosaveContents.get(i).equals("Coins")
                    || autosaveContents.get(i).equals("Bought Costumes")
                    || autosaveContents.get(i).equals("Equipped Costume"))
                {
                    savePropertyCount++;
                }
            }
            
            if(savePropertyCount != 4){
                System.out.println("Fixing Save File");
                writeSaveDefaults(saveAddress);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
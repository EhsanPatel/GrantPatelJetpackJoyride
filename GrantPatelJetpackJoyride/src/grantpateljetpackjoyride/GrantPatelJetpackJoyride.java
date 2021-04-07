/*
 * Ehsan Patel and Colin Grant
 * 26-Mar-2021
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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
            myWriter.write("Coins\n0\nBought Costumes\n1\nEquipped Costume\n1");
            myWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
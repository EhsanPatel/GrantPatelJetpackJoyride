/*
 * Ehsan Patel and Colin Grant
 * 26-Mar-2021
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class GrantPatelJetpackJoyride extends JFrame {

    public GrantPatelJetpackJoyride() {
        
        initUI();
    }
    
    private void initUI() {
        add(new MainGUI());
        createSaveFile();
        setResizable(false);
        pack();
        
        setTitle("Jetpack Joyride");
        ImageIcon icon = new ImageIcon(getClass().getResource("imageResources/costume1/running2.png"));
        setIconImage(icon.getImage());
        
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void createSaveFile(){
        System.out.println(System.getProperty("os.name").toLowerCase());
        String saveAddress = System.getProperty("user.home") + "\\Documents\\Jetpack Joyride\\";
        
        //searches for directory and creates one if necessary
        File file = new File(saveAddress);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        
        System.out.println(saveAddress+"autosave.jjrs");
        
        //searches for a file and creates one if necessary
        try {
            File myObj = new File(saveAddress+"autosave.jjrs");
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter(saveAddress+"autosave.jjrs");
            myWriter.write("New Test output");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
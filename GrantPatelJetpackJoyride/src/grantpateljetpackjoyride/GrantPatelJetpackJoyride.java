/*
 * Ehsan Patel and Colin Grant
 * 26-Mar-2021
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;

import com.sun.javafx.application.PlatformImpl;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class GrantPatelJetpackJoyride extends JFrame {

    public GrantPatelJetpackJoyride() {
        
        initUI();
    }
    
    private void initUI() {
        PlatformImpl.startup(() -> {});
        add(new MainGUI());
        
        setResizable(false);
        pack();
        
        setTitle("Jetpack Joyride");
        ImageIcon icon = new ImageIcon(getClass().getResource("imageResources/costume1/running2.png"));
        setIconImage(icon.getImage());
        
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
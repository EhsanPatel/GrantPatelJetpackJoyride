/*
 * Ehsan Patel
 * 26-Mar-2021
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class GrantPatelJetpackJoyride extends JFrame {

    public GrantPatelJetpackJoyride() {
        
        initUI();
    }
    
    private void initUI() {

        add(new GameFrame());
        
        setResizable(false);
        pack();
        
        setTitle("Jetpack Joyride");
        //ImageIcon kingIcon = new ImageIcon(getClass().getResource("imageResources/king_white.png"));
        //setIconImage(kingIcon.getImage());
        
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GrantPatelJetpackJoyride ex = new GrantPatelJetpackJoyride();
            ex.setVisible(true);
        });
    }
}
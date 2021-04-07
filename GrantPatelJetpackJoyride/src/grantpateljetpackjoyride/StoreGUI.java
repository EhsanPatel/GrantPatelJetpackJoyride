/*
 * Ehsan Patel
 * 1-Apr-2021
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author ehsan
 */
public class StoreGUI extends javax.swing.JFrame {
    private static InputStream is = LoadingGUI.class.getResourceAsStream("fonts/Abel-Regular.ttf");
    private  Font abelFont;
    private static Font scaledAbelFont;
    private static MainGUI mainWindow;
    private static JLabel[] equippedLabels;
    private static JLabel[] priceLabels;
    private String saveAddress;
    /**
     * Creates new form StoreGUI
     * @param m
     * @param saveAddress
     */
    public StoreGUI(MainGUI m, String saveAddress) {
        this.saveAddress = saveAddress;
        //loads in a font to use for the text
        try{
            this.abelFont = Font.createFont(Font.TRUETYPE_FONT, is);
            scaledAbelFont = abelFont.deriveFont(24f);
        }catch(FontFormatException | IOException e){
            System.out.println(e);
        }
        mainWindow = m;
        initComponents();
        //changes attributes of the display window containing the form
        setTitle("FULLBRICK STUDIOS: Jetpack Joyride");
        ImageIcon icon = new ImageIcon(getClass().getResource("imageResources/costume1/running2.png"));
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        clearLabels();
        
        //search save file for which ones are bought and update store
        readAutoSave(saveAddress);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        equippedLabel1 = new javax.swing.JLabel();
        equippedLabel3 = new javax.swing.JLabel();
        equippedLabel2 = new javax.swing.JLabel();
        priceLabel2 = new javax.swing.JLabel();
        priceLabel3 = new javax.swing.JLabel();
        priceLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1110, 600));
        setSize(new java.awt.Dimension(1110, 600));

        jPanel1.setBackground(new java.awt.Color(109, 118, 136));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/grantpateljetpackjoyride/imageResources/buttons/backFromStore.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/grantpateljetpackjoyride/imageResources/costume1/running2.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/grantpateljetpackjoyride/imageResources/costume2/running2.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/grantpateljetpackjoyride/imageResources/costume3/running2.png"))); // NOI18N

        equippedLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        equippedLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/grantpateljetpackjoyride/imageResources/equipped.png"))); // NOI18N

        equippedLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        equippedLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/grantpateljetpackjoyride/imageResources/equipped.png"))); // NOI18N

        equippedLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        equippedLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/grantpateljetpackjoyride/imageResources/equipped.png"))); // NOI18N

        priceLabel2.setBackground(new java.awt.Color(255, 255, 255));
        priceLabel2.setFont(scaledAbelFont);
        priceLabel2.setForeground(new java.awt.Color(255, 255, 255));
        priceLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        priceLabel2.setText("5,000 Coins");

        priceLabel3.setBackground(new java.awt.Color(255, 255, 255));
        priceLabel3.setFont(scaledAbelFont);
        priceLabel3.setForeground(new java.awt.Color(255, 255, 255));
        priceLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        priceLabel3.setText("10,000 Coins");

        priceLabel1.setBackground(new java.awt.Color(255, 255, 255));
        priceLabel1.setFont(scaledAbelFont);
        priceLabel1.setForeground(new java.awt.Color(255, 255, 255));
        priceLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        priceLabel1.setText("0 Coins");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(priceLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(equippedLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(priceLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(equippedLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(priceLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(equippedLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(equippedLabel1)
                            .addComponent(equippedLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(equippedLabel3)
                        .addGap(289, 289, 289)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(priceLabel2)
                        .addComponent(priceLabel1))
                    .addComponent(priceLabel3))
                .addGap(71, 71, 71))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        ((JFrame)mainWindow.getRootPane().getParent()).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        clearLabels();
        readAutoSave(saveAddress);
    }//GEN-LAST:event_jPanel1MouseEntered

    
    private void clearLabels(){
        //set all equipped labels as hidden until read by file
        equippedLabels = new JLabel[]{equippedLabel1,equippedLabel2,equippedLabel3};
        
        for (int i = 0; i < equippedLabels.length; i++) {
            equippedLabels[i].setVisible(false);
        }
        
        //set all price labels as hidden until read by file
        priceLabels = new JLabel[]{priceLabel1,priceLabel2,priceLabel3};
        for (int i = 0; i < priceLabels.length; ++i) {
            priceLabels[i].setVisible(true);
        }
    }
    
    /**
     * Reads the autosave file and updates the store accordingly
     * @param saveAddress - where the root folder of the save files is located
     */
    private void readAutoSave(String saveAddress){
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
            
            int endOfBought = 0;
            //linear search through auto save file contents - different number of bought costumes will affect which line items are on
            for(int i = 0; i < autosaveContents.size(); ++i){
                //finds the equipped costume line in autosave
                if(autosaveContents.get(i).equals("Equipped Costume")){
                    equippedLabels[Integer.parseInt(autosaveContents.get(i+1))-1].setVisible(true);
                    endOfBought = i-1;
                }
                
            }
            //linear search through auto save file contents - different number of bought costumes will affect which line items are on
            for(int i = 0; i < autosaveContents.size(); ++i){
                if(autosaveContents.get(i).equals("Bought Costumes")){
                    for(int j = i; j < endOfBought; ++j){
                        priceLabels[Integer.parseInt(autosaveContents.get(j+1))-1].setVisible(false);
                    }
                }
                
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel equippedLabel1;
    private javax.swing.JLabel equippedLabel2;
    private javax.swing.JLabel equippedLabel3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel priceLabel1;
    private javax.swing.JLabel priceLabel2;
    private javax.swing.JLabel priceLabel3;
    // End of variables declaration//GEN-END:variables
}

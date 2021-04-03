/*
 * Ehsan Patel
 * 3-Apr-2021
 * and open the template in the editor.
 */
package grantpateljetpackjoyride;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JFrame;

/**
 *
 * @author ehsan
 */
public class MenuButton {
    private int x;
    private int y;
    private int width;
    private int height;
    private String type;
    private JFrame destination;
    private Image image;
    public MenuButton(){
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        type = "Redirect";
        destination = new GrantPatelJetpackJoyride();
    }

    public MenuButton(int x, int y, int width, int height, String type, Image image){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.image = image;
    }
    public MenuButton(int x, int y, int width, int height, String type, Image image, JFrame destination){
        this(x,y,width,height,type,image);
        if(type.equals("Redirect")){
            this.destination = destination;
        }
        
    }
    
    public boolean buttonAction(int mouseX, int mouseY, MainGUI m){
        //checks for the click on the button
        if(mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height){
            if(type.equals("Redirect")){
                ((JFrame)m.getRootPane().getParent()).setVisible(false);
                
                destination.setVisible(true);
            }else if(type.equals("MusicToggle") && destination == null){
                //music toggle action
            }
            else if(type.equals("SFXToggle") && destination == null){
                //sfx toggle action
                
            }
            return true;
        }
        return false;
    }
    
    public void draw(Graphics2D g2d, MainGUI m){
        g2d.drawImage(image, x, y, m);
    }
}

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

// This function is an extension of JPanel to specifically support our images.
public class GamePanel extends JPanel {
    private Image crowdImage;
    private Boolean showCrowd;    
    private Boolean showPrize;
    private Boolean showMoney;
    private int crowdImageX;
    private Image[] prizeImages = new Image[5];
    private int currentPrize;
    private Image moneyImage;

    // Instantiating the GamePanel will setup all the images
    GamePanel(){        
        showCrowd = false;
        showPrize = false;
        showMoney = false;
        crowdImage = new ImageIcon("images\\crowd.jpg").getImage();        
        moneyImage = new ImageIcon("images\\money.png").getImage();
        this.crowdImageX = 50;
        this.prizeImages[0]=new ImageIcon("images\\potted_plant.png").getImage();
        this.prizeImages[1]=new ImageIcon("images\\chocolate_bar.png").getImage();
        this.prizeImages[2]=new ImageIcon("images\\cat_duck.png").getImage();
        this.prizeImages[3]=new ImageIcon("images\\cdplayer.png").getImage();
        this.prizeImages[4]=new ImageIcon("images\\parrot_mime.png").getImage();        

    }
    
    public void setShowCrowd(Boolean inputStatus) {
        this.showCrowd = inputStatus;
    }

    public void setShowPrize(Boolean inputStatus) {
        this.showPrize = inputStatus;
    }

    public void setShowMoney(Boolean inputStatus) {
        this.showMoney = inputStatus;
    }

    public void setCrowdImageX(int inputX) {
        this.crowdImageX = inputX;
    }

    public int getCrowdImageX() {
        return this.crowdImageX;
    }

    public void setCurrentPrize(int inputPrizeNo) {
        this.currentPrize = inputPrizeNo;
    }

    public int getCurrentPrize() {
        return this.currentPrize;
    }

    // Paint method to manage graphics
    // Images are displayed based on flags. When those flags are set to true then the image 
    // associated with the flag is shown using drawImage.
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        
        if ( showCrowd ) {
            g2D.drawImage(crowdImage,crowdImageX,50,null);
        } else if ( showPrize ) {
            g2D.drawImage(prizeImages[currentPrize],300,50,null);
        } else if ( showMoney ) {
            g2D.drawImage(moneyImage,300,50,null);
        }
    
    }
    
}


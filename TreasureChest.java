import java.awt.*;
import javax.swing.*;
public class TreasureChest extends Entity{
    private Image chestImage;
    private ImageIcon chestGIF;
    private boolean isOpen = false;
    public TreasureChest(int x, int y, String imageName, String gifName) {
        super(x, y);
        this.chestImage = ImageManager.getImage(imageName);
        this.chestGIF = ImageManager.getGIF(gifName);
    }

    @Override
    public void draw(Graphics g) {
        if (isOpen) {
            //System.out.println("Chest opened!");
            g.drawImage(chestGIF.getImage(), x, y, null);
        } else {
            g.drawImage(chestImage, x, y, null);
        }
    }

    public void openChest() {
        isOpen = true;
    }
    
}

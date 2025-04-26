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
    public void draw(Graphics g, int cameraX, int cameraY) {
        int screenX = x - cameraX;
        int screenY = y - cameraY;
        
        if (isOpen) {
            //System.out.println("Chest opened!");
            g.drawImage(chestGIF.getImage(), screenX , screenY, null);
        } else {
            g.drawImage(chestImage, screenX, screenY, null);
        }
    }

    public void openChest() {
        isOpen = true;
    }
    
}

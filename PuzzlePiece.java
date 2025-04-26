import java.awt.*;
import javax.swing.*;
public class PuzzlePiece extends Entity {
    private ImageIcon icon;

    public PuzzlePiece(int x, int y, String GIFName) {
        super(x, y);
        this.icon = ImageManager.getGIF(GIFName);
    }

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        int screenX = x - cameraX;
        int screenY = y - cameraY;
        
        g.drawImage(icon.getImage(), screenX, screenY, null);
    }
    
}

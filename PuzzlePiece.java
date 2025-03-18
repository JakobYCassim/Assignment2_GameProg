import java.awt.*;
import javax.swing.*;
public class PuzzlePiece extends Entity {
    private ImageIcon icon;

    public PuzzlePiece(int x, int y, String GIFName) {
        super(x, y);
        this.icon = ImageManager.getGIF(GIFName);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(icon.getImage(), x, y, null);
    }
    
}

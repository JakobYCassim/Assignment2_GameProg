
import java.awt.*;

public class Trap extends Entity {
    private Image trapImage;
    private int visibilityTimer;
    private boolean visible;

    public Trap(int x, int y, String imageName, int visibilityDuration) {
        super(x, y);
        this.visible = true;
        this.visibilityTimer = visibilityDuration;
        this.trapImage = ImageManager.getImage(imageName);
    }

    public Trap(int x, int y, String imageName) {
        super(x, y);
        this.visible = true;
        this.visibilityTimer = 75;
        this.trapImage = ImageManager.getImage(imageName);
    }

    public void update() {
        if(visible) {
            visibilityTimer--;
            if(visibilityTimer <= 0) {
                visible = false;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (trapImage != null && visible) {
            g.drawImage(trapImage, x, y, null);
        }
    }

    public boolean isVisible() {
        return visible;
    }
    
}

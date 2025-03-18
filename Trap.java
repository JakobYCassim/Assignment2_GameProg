
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
        this.visibilityTimer = 120;
        this.trapImage = ImageManager.getImage(imageName);
    }

    public void update() {
        if(visible) {
            visibilityTimer--;
            //System.out.println("Trap at (" + x + ", " + y + ") visibilityTimer: " + visibilityTimer);
            if(visibilityTimer <= 0) {
                visible = false;
                System.out.println("Trap at (" + x + ", " + y + ") is now invisible");
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (trapImage != null && visible) {
            g.drawImage(trapImage, x, y, null);
            System.out.println("Drawing trap at (" + x + ", " + y + ")");
        }

        if (trapImage == null) {
            System.out.println("Trap at (" + x + ", " + y + ") has no image");
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean vis) {
        visible = vis;
    }
    
}

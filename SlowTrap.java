
import java.awt.*;

public class SlowTrap extends Entity{
    private Image trapImage;
    private boolean activated = false;
    private int duration = 150;
    private float slowFactor = 0.5f;

    public SlowTrap(int x, int y, String imageName) {
        super(x, y);
        this.trapImage = ImageManager.getImage(imageName);
    }

    @Override
    public void draw(Graphics g) {
        if (trapImage != null) {
            g.drawImage(trapImage, x, y, null);
        }

        if(activated) {
            g.setColor(new Color(0, 0, 255, 100));
            g.fillRect(0, 0, 800, 600);
        }
       
       
    }

    @Override
    public void update() {
        if(activated) {
            duration -= 1;
            if(duration <= 0) {
                activated = false;
                duration = 150;
            }
        }
    }

    public void activate() {
        activated = true;
        duration = 150;
    }

    public boolean isActivated() {
        return activated;
    }

    public float getSlowFactor() {
        return slowFactor;
    }
    
}

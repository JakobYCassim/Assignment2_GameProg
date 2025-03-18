import java.awt.*;
import java.util.Random;

public class PoisonBubbleParticle extends Particle{
    
    public PoisonBubbleParticle(int x, int y) {
        super(x, y);
        color = new Color(0, 255, 0, new Random().nextInt(156) + 100);
    }

    @Override
    public void update() {
        x+= new Random().nextInt(5) - 2;
        y-=2;
        lifespan--;
    }

    
}

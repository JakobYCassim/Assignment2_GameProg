import java.awt.*;
import java.util.Random;

public class DustParticle extends Particle {
    private Random rand;


    public DustParticle(int x, int y) {
        super(x, y);
        rand = new Random();
        size = rand.nextInt(4) + 2;
        lifespan = rand.nextInt(10) + 5;
        color = new Color(169, 169, 169, rand.nextInt(100) + 100);
    }

    @Override
    public void update() {
        x += rand.nextInt(5) - 2;
        y += rand.nextInt(3);
        lifespan--;
    }
    
}

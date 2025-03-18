import java.awt.*;
import java.util.Random;

public class SparkleParticle extends Particle {
    private Random rand;

    public SparkleParticle(int x, int y) {
        super(x,y);
        rand = new Random();
        size = rand.nextInt(5) + 2;
        lifespan= rand.nextInt(20) + 10;
        color = new Color(255, 215, 0, rand.nextInt(156) + 100);
    }

    @Override
    public void update() {
        x += rand.nextInt(5) - 2;
        y += rand.nextInt(5) - 2;
        lifespan --;
    }
}

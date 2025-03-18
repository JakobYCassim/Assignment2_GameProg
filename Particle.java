import java.awt.*;
import java.util.Random;

public class Particle {
    protected int x, y;
    protected int size;
    protected Color color;
    protected int lifespan;
    protected Random rand;

    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
        rand = new Random();
        size = rand.nextInt(5) + 2;
        lifespan = rand.nextInt(20) + 10;
    }

    public void update() {
        y-=2;
        lifespan--;        
    }

    public void draw(Graphics g) {
        if (lifespan > 0) {
            g.setColor(color);
            g.fillOval(x, y, size, size);
        }
    }

    public boolean isAlive() {
        return lifespan > 0;
    }
}

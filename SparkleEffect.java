import java.util.Random;

public class SparkleEffect extends ParticleEffect{
    
    public SparkleEffect(int x, int y, int duration) {
        super(x, y, duration);
    }

    public SparkleEffect(int x , int y) {
        super(x, y, 200);
    }

    @Override
    public void createParticles() {
        Random rand = new Random();
        for(int i =0; i < 15; i++) {
            int px = x + rand.nextInt(40) - 20;
            int py = y + rand.nextInt(40) - 20;
            particles.add(new SparkleParticle(px, py));
        }
    }
    
}

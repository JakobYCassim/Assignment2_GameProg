import java.util.Random;

public class DustEffect extends ParticleEffect{
    
    public DustEffect(int x, int y, int duration) {
        super(x, y, duration);
    }

    public DustEffect(int x, int y) {
        super(x, y, 20);
    }

    @Override
    public void createParticles() {
        Random rand = new Random();
        for(int i = 0; i < 2; i++) {
            int px = x + rand.nextInt(20) - 10;
            int py = y + rand.nextInt(5);

            particles.add(new DustParticle(px, py));
        }
    }
    
}

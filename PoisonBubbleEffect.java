import java.util.Random;

public class PoisonBubbleEffect extends ParticleEffect{
    
    public PoisonBubbleEffect(int x, int y) {
        super(x, y, 100);
    }

    @Override
    public void createParticles() {
        Random rand = new Random();
        for(int i = 0; i < 20; i++) {
            int px = x + rand.nextInt(40) - 20;
            int py = y + rand.nextInt(40) - 20;
            particles.add(new PoisonBubbleParticle(px, py));
        }
    }
    
}

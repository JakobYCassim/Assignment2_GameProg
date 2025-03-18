import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class ParticleEffect {
    protected int x, y;
    protected List<Particle> particles;
    protected int duration;
    protected int frameCount;

    public ParticleEffect(int x, int y, int duration) {
        this.x = x;
        this.y = y;
        this.duration = duration;
        this.particles = new ArrayList<Particle>();
        this.frameCount = 0;
        createParticles();
    }

    public void createParticles() {
        for(int i = 0; i < 10; i++) {
            particles.add(new Particle(x, y));
        }
    }

    public void update() {
        frameCount++;
        for (Particle p : particles) {
            p.update();
        }
        if (frameCount > duration) {
            particles.clear();
        }
    }

    public void draw(Graphics g) {
        for (Particle p : particles) {
            p.draw(g);
        }
    }

    public boolean isFinished() {
        return frameCount >= duration;
    }

}

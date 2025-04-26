import java.awt.*;

public abstract class Entity {
    protected int x, y;
    protected int WORLD_WIDTH = 1600, WORLD_HEIGHT = 1200;
    protected int width = 30, height = 30;
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(Graphics g, int cameraX, int cameraY);
    public void update() {}
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

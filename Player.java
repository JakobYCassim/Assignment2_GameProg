import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;


public class Player extends Entity implements KeyListener {
    private int speed = 5;
    private boolean isMoving = false;
    private String direction = "idle";
    private SoundManager soundManager;
    private Animation walkUpAnimation;
    private Animation walkDownAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    private Animation idleAnimation;
    private ImageIcon collapseGIF;
    private boolean isCollapsed = false;
    private boolean inputEnabled = true;
    private int screen_width = 800;
    private int screen_height = 600;

    public Player(int x, int y) {
        super(x, y, 64, 64);
        soundManager = SoundManager.getInstance();
    
        walkUpAnimation = new Animation(true);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up1"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up2"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up3"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up4"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up5"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up6"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up7"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up8"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("walk_up9"), 100);

        walkDownAnimation = new Animation(true);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down1"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down2"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down3"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down4"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down5"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down6"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down7"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down8"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("walk_down9"), 100);
        walkLeftAnimation = new Animation(true);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left1"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left2"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left3"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left4"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left5"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left6"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left7"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left8"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("walk_left9"), 100);
        
        
        walkRightAnimation = new Animation(true);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right1"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right2"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right3"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right4"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right5"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right6"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right7"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right8"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("walk_right9"), 100);
        
        idleAnimation = new Animation(true);
        idleAnimation.addFrame(ImageManager.getImage("idle0"), 100);
        idleAnimation.addFrame(ImageManager.getImage("idle1"), 100);
        idleAnimation.addFrame(ImageManager.getImage("idle2"), 100);
        idleAnimation.addFrame(ImageManager.getImage("idle3"), 100);
        idleAnimation.addFrame(ImageManager.getImage("idle4"), 100);
        idleAnimation.addFrame(ImageManager.getImage("idle5"), 100);
        idleAnimation.addFrame(ImageManager.getImage("idle6"), 100);
        idleAnimation.addFrame(ImageManager.getImage("idle7"), 100);

        collapseGIF = ImageManager.getGIF("collapse");
    
    
    
    
    
    
    
    
    }

    public void update() {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!inputEnabled) System.out.println("Input disabled");
        if(!inputEnabled || isCollapsed) return;
        int key = e.getKeyCode();
        if(!isMoving) {
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                isMoving = true;
                soundManager.setVolume("walking", 0.65f);
                soundManager.playClip("walking", true);
            }
        }
        if (key == KeyEvent.VK_UP) {
            if(y - speed <= 0) return;
             y -= speed;
             direction = "up";
        }
        if (key == KeyEvent.VK_DOWN) {
            if(y + speed >= 536) return;
             y += speed;
             direction = "down";
        }
        if (key == KeyEvent.VK_LEFT) {
            if(x - speed <= 0) return;
             x -= speed;
             direction = "left";
        }
        if (key == KeyEvent.VK_RIGHT) {
            if(x + speed >= 736) return;
            x += speed;
            direction = "right";
        }

        if (key == KeyEvent.VK_F) {
            collapse();
        }


        //System.out.println("Player Position x: " + x + ", y: " + y);
    }

    @Override
    public void draw(Graphics g) {
        if(isCollapsed) {
            g.drawImage(collapseGIF.getImage(), x, y, null);
        }else if(isMoving) {
            Animation currentAnimation = getAnimationForDirection(direction);
            if (!currentAnimation.isStillActive()) currentAnimation.start();
            currentAnimation.update();
            g.drawImage(currentAnimation.getImage(), x, y, null);
        } else {
            Animation currentAnimation = idleAnimation;
            if(!currentAnimation.isStillActive()) currentAnimation.start();
            currentAnimation.update();
            g.drawImage(currentAnimation.getImage(), x, y, null);
        }
    }

    private Animation getAnimationForDirection(String direction) {
        return switch (direction) {
            case "up" -> walkUpAnimation;
            case "down" -> walkDownAnimation;
            case "left" -> walkLeftAnimation;
            case "right" -> walkRightAnimation;
            default -> idleAnimation;
        };
    }

    public void collapse() {
        isCollapsed = true;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setCollapsed(boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(isMoving) {
            
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                isMoving = false;
                soundManager.stopClip("walking");
            }
        }
    }

    public void setInputEnabled(boolean inputEnabled) {
        this.inputEnabled = inputEnabled;
    }

    public boolean isMoving() {
        return isMoving;
    }

}

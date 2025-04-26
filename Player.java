import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
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
    private Animation attackAnimation; //new
    private ImageIcon collapseGIF;
    private boolean isCollapsed = false;
    private boolean inputEnabled = true;
    private int screen_width = 800;
    private int screen_height = 600;

    private int health = 100;
    private int maxHealth = 100;
    private int attackDamage = 25;
    private int attackRange = 50;
    private boolean attacking = false;  
    private float speedModifier = 1.0f;
    private long lastAttackTime = 0;
    private static final long ATTACK_COOLDOWN = 800;


    private List<Enemy> enemiesAlive = new ArrayList<>();


    private boolean isBlocking = false;
    private BlockAbility blockAbility;
    private HealAbility healAbility;
    private Animation blockAnimation;
    private Animation healAnimation;
    private int HEAL_AMOUNT = 25;
    private boolean isHealing = false;


    public Player(int x, int y) {
        super(x, y, 64, 64);
        soundManager = SoundManager.getInstance();
        blockAbility = new BlockAbility(this);
        healAbility = new HealAbility(this);
        
        
        blockAnimation = new Animation(false);
        blockAnimation.addFrame(ImageManager.getImage("block1"), 100);
        blockAnimation.addFrame(ImageManager.getImage("block2"), 100);
       
        healAnimation = new Animation(false);
        healAnimation.addFrame(ImageManager.getImage("heal1"), 100);
        healAnimation.addFrame(ImageManager.getImage("heal2"), 100);
        healAnimation.addFrame(ImageManager.getImage("heal3"), 100);
        

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


        collapseGIF = ImageManager.getGIF("collapse");
    
        attackAnimation = new Animation(false);
        attackAnimation.addFrame(ImageManager.getImage("attack1"), 100);
        attackAnimation.addFrame(ImageManager.getImage("attack2"), 100);
        attackAnimation.addFrame(ImageManager.getImage("attack3"), 100);
        attackAnimation.addFrame(ImageManager.getImage("attack4"), 100);
        attackAnimation.addFrame(ImageManager.getImage("attack5"), 100);
        attackAnimation.addFrame(ImageManager.getImage("attack6"), 100);
    
        

    
    
    
    
    }

    public void takeDamage(int damage) {

        //System.out.println(isBlocking);
        if(!isBlocking) {
            health -= damage;
            soundManager.playClip("take_damage", false);
        }
     
        if(health <= 0) {
            health = 0;
            collapse();
        }
    }

    public void attack(List<Enemy> enemies) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime < ATTACK_COOLDOWN) return;
        
        attacking = true;
        lastAttackTime = currentTime;

        

        

         for (Enemy enemy : enemies) {
            double distance = calculateDistance(x, y, enemy.getX(), enemy.getY());
            if (distance <= attackRange) {
                soundManager.playClip("player_attack", false);
                enemy.takeDamage(attackDamage);
            
            } 
        }
    } 

    private double calculateDistance(int x, int y, int x2, int y2) {
        // Calculate the distance between two points
        return Math.sqrt(Math.pow(x2 - x, 2) + Math.pow(y2 - y, 2));
    }

    public void setSpeedModifier(float speedModifier) {
        this.speedModifier = speedModifier;
    }

    public void resetSpeedModifier() {
        this.speedModifier = 1.0f;
    }

    public void update(List<Enemy> enemies) {   
        //update enemies list
        this.enemiesAlive = enemies;
        blockAbility.update();
        healAbility.update();

    
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

        int effectiveSpeed = (int) (speed * speedModifier);
        if (key == KeyEvent.VK_UP) {
           // if(y - effectiveSpeed <= 0) return;
             y -= effectiveSpeed;
             if(y < 0) y = WORLD_HEIGHT;
             direction = "up";
        }
        if (key == KeyEvent.VK_DOWN) {
            //if(y + effectiveSpeed >= 536) return;
             y += effectiveSpeed;
             if (y > WORLD_HEIGHT) y = 0;
             direction = "down";
        }
        if (key == KeyEvent.VK_LEFT) {
            //if(x - effectiveSpeed <= 0) return;
             x -= effectiveSpeed;
             if (x < 0) x = WORLD_WIDTH;
             direction = "left";
        }
        if (key == KeyEvent.VK_RIGHT) {
            //if(x + effectiveSpeed >= 736) return;
            x += effectiveSpeed;
            if (x > WORLD_WIDTH) x = 0;
            direction = "right";
        }

        if (key == KeyEvent.VK_F) {
            collapse();
        }

        if (key == KeyEvent.VK_SPACE) {
            attack(enemiesAlive);
        }

        if (key == KeyEvent.VK_B) {
            blockAbility.activate();
        }

        if (key == KeyEvent.VK_H) {
            healAbility.activate();
        }


        //System.out.println("Player Position x: " + x + ", y: " + y);
    }

    @Override
    public void draw(Graphics g, int cameraX, int cameraY) {
        int screenX = x - cameraX;
        int screenY = y - cameraY;
        
        if(isCollapsed) {
            g.drawImage(collapseGIF.getImage(), screenX, screenY, null);
        }else if(isMoving) {
            Animation currentAnimation = getAnimationForDirection(direction);
            if (!currentAnimation.isStillActive()) currentAnimation.start();
            currentAnimation.update();
            g.drawImage(currentAnimation.getImage(), screenX, screenY, null);
        }else if(isHealing) {   
            Animation currentAnimation = healAnimation;
            if(!currentAnimation.isStillActive()) currentAnimation.start();
            currentAnimation.update();
            if(!currentAnimation.isStillActive()) isHealing = false;
            g.drawImage(currentAnimation.getImage(), screenX, screenY, null);
        }else if(isBlocking) {  
            Animation currentAnimation = blockAnimation;
            if(!currentAnimation.isStillActive()) currentAnimation.start();
            currentAnimation.update();
            g.drawImage(currentAnimation.getImage(), screenX, screenY, null);
        }else if(attacking) {
            Animation currentAnimation = attackAnimation;
            if(!currentAnimation.isStillActive()) currentAnimation.start();
            currentAnimation.update();
            if(!currentAnimation.isStillActive()) attacking = false;
            g.drawImage(currentAnimation.getImage(), screenX, screenY, null);
        }else {
            Animation currentAnimation = idleAnimation;
            if(!currentAnimation.isStillActive()) currentAnimation.start();
            currentAnimation.update();
            g.drawImage(currentAnimation.getImage(), screenX, screenY, null);
        }

        g.setColor(Color.RED);
        g.fillRect(screenX, screenY -10, width, 5);
        g.setColor(Color.GREEN);
        g.fillRect(screenX, screenY - 10, (int) (width * (health / (float) maxHealth)), 5);
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

    public void setBlocking(boolean blocking) {
        this.isBlocking = blocking;
        if(blocking) {
            blockAnimation.start();
        }
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public boolean isHealing() {
        return isHealing;
    }


    public void heal() {
        if(health >= maxHealth) return;

        isHealing = true;
        healAbility.useCharge();
        if(health + HEAL_AMOUNT > maxHealth) {
            health = maxHealth;
        } else {
            health += HEAL_AMOUNT;
        }
    }

    public BlockAbility getBlockAbility() {
        return blockAbility;
    }

    public HealAbility getHealAbility() {
        return healAbility;
    }

    public void collectPuzzlePiece() {
        healAbility.addCharge();
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

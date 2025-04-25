import java.awt.*;
import java.util.Random;

public class Enemy extends Entity{
    private static final int PATROL_RANGE = 150;
    private static final int DETECTION_RANGE = 200;
    private static final int ATTACK_RANGE = 100;
    private static final int ATTACK_COOLDOWN = 1500;
   
    private int health = 100;
    private int maxHealth = 100;
    private int attackDamage = 10;
    private int speed = 1;
    private boolean patrolHorizontal;
    private int startX, startY;
    private int direction = 1; // 1 for right/down, -1 for left/up
    private boolean playerDetected = false;
    private boolean isAttacking = false;
    private long lastAttackTime = 0;

    private Animation walkAnimation;
    private Animation attackAnimation;
    private Animation deathAnimation;

    private Random rand = new Random();
   
   
    public Enemy(int x, int y, boolean patrolHorizontal) {
        super(x, y, 64, 64);
        this.startX = x;
        this.startY = y;
        this.patrolHorizontal = patrolHorizontal;

        walkAnimation = new Animation(true);
        attackAnimation = new Animation(false);
        deathAnimation = new Animation(false);

        setupAnimations();
    }

    private void setupAnimations() {
        walkAnimation.addFrame(ImageManager.getImage("walk_down1"), 100);
        walkAnimation.addFrame(ImageManager.getImage("walk_down2"), 100);
        walkAnimation.addFrame(ImageManager.getImage("walk_down3"), 100);
        

        attackAnimation.addFrame(ImageManager.getImage("idle7"), 100);
        attackAnimation.addFrame(ImageManager.getImage("idle4"), 100);
        walkAnimation.start();
    
    }
    public void update(Player player) {
        //System.out.println("Calling enemy update");
        if(isAttacking) {
            attackAnimation.update();
            //System.out.println("Enemy at (" + x + ", " + y + ") is attacking");
            if (!attackAnimation.isStillActive()) {
                isAttacking = false;
                walkAnimation.start();
            }
            //System.out.println("Attack animation is still active: " + attackAnimation.isStillActive());
            return;

    
        }
        
        //System.out.println("Enemy at (" + x + ", " + y + ") is Not attacking");
        double distToPlayer = calculateDistance(x, y, player.getX(), player.getY());

        playerDetected = distToPlayer <= DETECTION_RANGE;

        if (playerDetected) {
            moveTowardPlayer(player);

            if (distToPlayer <= ATTACK_RANGE) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastAttackTime >= ATTACK_COOLDOWN) {
                    attack(player);
                    lastAttackTime = currentTime;
                }
            }
        } else {
            patrol();
        }

        walkAnimation.update();
    }

    private void moveTowardPlayer(Player player) {
        int targetX = player.getX();
        int targetY = player.getY();

        if(x < targetX) x += speed;
        else if(x > targetX) x -= speed;

        if(y < targetY) y += speed;
        else if(y > targetY) y -= speed;
    }

    private void patrol() {
       // System.out.println("Enemy at (" + x + ", " + y + ") is Patrolling");
        if (patrolHorizontal) {
            x += direction * speed;
            
            if (x > startX + PATROL_RANGE || x < startX - PATROL_RANGE) {
                direction *= -1;
            }
        } else {
            y += direction * speed;
            
            if (y > startY + PATROL_RANGE || y < startY - PATROL_RANGE) {
                direction *= -1;
            }
        }
    }

    private void attack(Player player) {
        isAttacking = true;
        walkAnimation.stop();
        attackAnimation.start();
        
        player.takeDamage(attackDamage);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            deathAnimation.start();
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public void draw(Graphics g) {
        if (isDead()) return;

        if (isAttacking) {
            g.drawImage(attackAnimation.getImage(), x, y, null);
        } else {
            g.drawImage(walkAnimation.getImage(), x, y, null);
        }

        g.setColor(Color.RED);
        g.fillRect(x, y - 10, width, 5);
        g.setColor(Color.GREEN);
        g.fillRect(x, y - 10, (int) (width * (health / (float) maxHealth)), 5);
    }
    
}

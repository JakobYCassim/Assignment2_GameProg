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

    private Animation walkUpAnimation;
    private Animation walkDownAnimation;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    private Animation attackAnimation;
    private Animation walkAnimation;
    private Animation deathAnimation;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction facingDirection = Direction.DOWN;
    private Direction previousFacingDirection = Direction.DOWN;

    private Random rand = new Random();
   
   
    public Enemy(int x, int y, boolean patrolHorizontal) {
        super(x, y, 64, 64);
        this.startX = x;
        this.startY = y;
        this.patrolHorizontal = patrolHorizontal;

       

        setupAnimations();
        walkAnimation = walkDownAnimation;
        walkAnimation.start();
    }

    private void setupAnimations() {
        walkUpAnimation = new Animation(true);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up1"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up2"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up3"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up4"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up5"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up6"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up7"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up8"), 100);
        walkUpAnimation.addFrame(ImageManager.getImage("enemy_walk_up9"), 100);

        walkDownAnimation = new Animation(true);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down1"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down2"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down3"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down4"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down5"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down6"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down7"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down8"), 100);
        walkDownAnimation.addFrame(ImageManager.getImage("enemy_walk_down9"), 100);

        walkLeftAnimation = new Animation(true);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left1"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left2"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left3"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left4"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left5"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left6"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left7"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left8"), 100);
        walkLeftAnimation.addFrame(ImageManager.getImage("enemy_walk_left9"), 100);

        walkRightAnimation = new Animation(true);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right1"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right2"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right3"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right4"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right5"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right6"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right7"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right8"), 100);
        walkRightAnimation.addFrame(ImageManager.getImage("enemy_walk_right9"), 100);
        
        attackAnimation = new Animation(false);
        attackAnimation.addFrame(ImageManager.getImage("enemy_attack1"), 100);
        attackAnimation.addFrame(ImageManager.getImage("enemy_attack2"), 100);
        attackAnimation.addFrame(ImageManager.getImage("enemy_attack3"), 100);
        attackAnimation.addFrame(ImageManager.getImage("enemy_attack4"), 100);
        attackAnimation.addFrame(ImageManager.getImage("enemy_attack5"), 100);
        attackAnimation.addFrame(ImageManager.getImage("enemy_attack6"), 100);
        attackAnimation.addFrame(ImageManager.getImage("enemy_attack7"), 100);
        
        deathAnimation = new Animation(false);
        deathAnimation.addFrame(ImageManager.getImage("enemy_death1"), 100);
        deathAnimation.addFrame(ImageManager.getImage("enemy_death2"), 100);
        deathAnimation.addFrame(ImageManager.getImage("enemy_death3"), 100);
        deathAnimation.addFrame(ImageManager.getImage("enemy_death4"), 100);
        deathAnimation.addFrame(ImageManager.getImage("enemy_death5"), 100);
        deathAnimation.addFrame(ImageManager.getImage("enemy_death6"), 100);
    
    }
    public void update(Player player) {
        if (isDead()) {
            deathAnimation.update();
            return;
        }
        if(isAttacking) {
            attackAnimation.update();
            if (!attackAnimation.isStillActive()) {
                isAttacking = false;
                walkAnimation.start();
            }
            return;
        }
        
       
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

        if (facingDirection != previousFacingDirection) {
            walkAnimation.stop();
            switch (facingDirection) {
                case UP -> walkAnimation = walkUpAnimation;
                case DOWN -> walkAnimation = walkDownAnimation;
                case LEFT -> walkAnimation = walkLeftAnimation;
                case RIGHT -> walkAnimation = walkRightAnimation;
            }
            walkAnimation.start();
            previousFacingDirection = facingDirection;

        }

        walkAnimation.update();
    }

    private void moveTowardPlayer(Player player) {
        int targetX = player.getX();
        int targetY = player.getY();

        int dx = targetX - x;
        int dy = targetY - y;

        if (Math.abs(dx) > Math.abs(dy)) {
            facingDirection = (dx > 0) ? Direction.RIGHT : Direction.LEFT;
        } else {
            facingDirection = (dy > 0) ? Direction.DOWN : Direction.UP;
        }

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
            facingDirection = (direction == 1) ? Direction.RIGHT : Direction.LEFT;
        } else {
            y += direction * speed;
            
            if (y > startY + PATROL_RANGE || y < startY - PATROL_RANGE) {
                direction *= -1;
            }
            facingDirection = (direction == 1) ? Direction.DOWN : Direction.UP;
        }
    }

    private void attack(Player player) {
        isAttacking = true;
        walkAnimation.stop();
        attackAnimation.start();
        SoundManager.getInstance().playClip("enemy_attack", false);
        SoundManager.getInstance().setVolume("enemy_attack", 0.75f);
        player.takeDamage(attackDamage);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            SoundManager.getInstance().playClip("hit", false);
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
        if (isDead()) {
            deathAnimation.update();
            g.drawImage(deathAnimation.getImage(), x, y, null);
             return;
        }

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

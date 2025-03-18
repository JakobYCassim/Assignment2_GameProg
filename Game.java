import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class Game extends JPanel {
    private Player player;
    private Map map;
    private boolean gameOver;
    private boolean levelComplete;
    private boolean isPaused;
    private SoundManager soundManager; 
    private static final double TRAP_WARNING_DISTANCE = 100.0;
    private List<ParticleEffect> particleEffects;
    private int dustCooldown = 0;
    private static final int DUST_COOLDOWN_MAX = 10;
    private String scoreText;
    private List<Level> levels;
    private int currentLevel;


        public Game() {
            setDoubleBuffered(true);
            loadImages();
            initializeLevels();
            currentLevel = 0;
            startLevel(currentLevel);
            setPreferredSize(new Dimension(800,600));
            setFocusable(true);
            scoreText = "Remaining Pieces: " + getTotalPuzzlePieces();
            particleEffects = new ArrayList<>();
            soundManager = SoundManager.getInstance();
            soundManager.setVolume("background", 0.38f);
            soundManager.playClip("background", true);
        
        }

        private void initializeLevels() {
            levels = new ArrayList<>();
            levels.add(new Level(1, 4, 2));
            levels.add(new Level(2, 6, 4));
            levels.add(new Level(3, 8, 6));
        }

        private void startLevel(int levelIndex) {
            Level level = levels.get(levelIndex);
            player = new Player(50, 50);
            player.setInputEnabled(true);
            addKeyListener(player);
            map = new Map(level.getNumPuzzlePieces(), level.getNumTraps(), player);
            gameOver = false;
            levelComplete = false;
            isPaused = false;
            scoreText = "Remaining Pieces: " + getTotalPuzzlePieces();
            if (particleEffects != null) particleEffects.clear();
            requestFocusInWindow();
        }
        private void loadImages() {

            ImageManager.loadImage("background", "resources/background.png");


            ImageManager.loadImage("walk_up1", "resources/walk_up1.png");
            ImageManager.loadImage("walk_up2", "resources/walk_up2.png");
            ImageManager.loadImage("walk_up3", "resources/walk_up3.png");
            ImageManager.loadImage("walk_up4", "resources/walk_up4.png");
            ImageManager.loadImage("walk_up5", "resources/walk_up5.png");
            ImageManager.loadImage("walk_up6", "resources/walk_up6.png");
            ImageManager.loadImage("walk_up7", "resources/walk_up7.png");
            ImageManager.loadImage("walk_up8", "resources/walk_up8.png");
            ImageManager.loadImage("walk_up9", "resources/walk_up9.png");
        
            ImageManager.loadImage("walk_down1", "resources/walk_down1.png");
            ImageManager.loadImage("walk_down2", "resources/walk_down2.png");
            ImageManager.loadImage("walk_down3", "resources/walk_down3.png");
            ImageManager.loadImage("walk_down4", "resources/walk_down4.png");
            ImageManager.loadImage("walk_down5", "resources/walk_down5.png");
            ImageManager.loadImage("walk_down6", "resources/walk_down6.png");
            ImageManager.loadImage("walk_down7", "resources/walk_down7.png");
            ImageManager.loadImage("walk_down8", "resources/walk_down8.png");
            ImageManager.loadImage("walk_down9", "resources/walk_down9.png");
        
            ImageManager.loadImage("walk_left1", "resources/walk_left1.png");
            ImageManager.loadImage("walk_left2", "resources/walk_left2.png");
            ImageManager.loadImage("walk_left3", "resources/walk_left3.png");
            ImageManager.loadImage("walk_left4", "resources/walk_left4.png");
            ImageManager.loadImage("walk_left5", "resources/walk_left5.png");
            ImageManager.loadImage("walk_left6", "resources/walk_left6.png");
            ImageManager.loadImage("walk_left7", "resources/walk_left7.png");
            ImageManager.loadImage("walk_left8", "resources/walk_left8.png");
            ImageManager.loadImage("walk_left9", "resources/walk_left9.png");
        
            ImageManager.loadImage("walk_right1", "resources/walk_right1.png");
            ImageManager.loadImage("walk_right2", "resources/walk_right2.png");
            ImageManager.loadImage("walk_right3", "resources/walk_right3.png");
            ImageManager.loadImage("walk_right4", "resources/walk_right4.png");
            ImageManager.loadImage("walk_right5", "resources/walk_right5.png");
            ImageManager.loadImage("walk_right6", "resources/walk_right6.png");
            ImageManager.loadImage("walk_right7", "resources/walk_right7.png");
            ImageManager.loadImage("walk_right8", "resources/walk_right8.png");
            ImageManager.loadImage("walk_right9", "resources/walk_right9.png");
        
            ImageManager.loadImage("idle0", "resources/idle0.png");
            ImageManager.loadImage("idle1", "resources/idle1.png");
            ImageManager.loadImage("idle2", "resources/idle2.png");
            ImageManager.loadImage("idle3", "resources/idle3.png");
            ImageManager.loadImage("idle4", "resources/idle4.png");
            ImageManager.loadImage("idle5", "resources/idle5.png");
            ImageManager.loadImage("idle6", "resources/idle6.png");
            ImageManager.loadImage("idle7", "resources/idle7.png");
            
            ImageManager.loadGIF("collapse", "resources/hurt.gif");
        
            ImageManager.loadImage("trap_1", "resources/traps1.png");
            ImageManager.loadImage("trap_2", "resources/traps2.png");
            
            ImageManager.loadImage("treasure", "resources/treasure_chest.png");
            ImageManager.loadGIF("open_chest", "resources/chest.gif");
            
            ImageManager.loadGIF("puzzle_piece", "resources/puzzlePiece.gif");
        }
    
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            map.draw(g);
            player.draw(g);
    
            particleEffects.removeIf(ParticleEffect::isFinished);
            for (ParticleEffect effect : particleEffects) {
                effect.draw(g);
            }

            g.setColor(Color.WHITE);
            g.setFont( new Font("Arial", Font.BOLD, 24));
            g.drawString(scoreText, 20, 60);
            g.drawString("Level: " + (currentLevel + 1), 20, 30);
    
            if (gameOver) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.drawString("Game Over", 300, 300);
                repaint();
                player.setInputEnabled(false);
            }
    
            if(levelComplete) {
                g.setColor(Color.GREEN);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.drawString("Level Complete", 300, 300);
                repaint();
                player.setInputEnabled(false);
            }
        }
    
        public void update() {
            if(!gameOver && !levelComplete) {    
                //player.update();
                //System.out.println("Player Bounds: " + player.getBounds());
                for(Entity trap : map.getTraps()) {
                   ( (Trap) trap).update(); 
                }
    
                if(player.isMoving() && dustCooldown <= 0) {
                    particleEffects.add(new DustEffect(player.getX() + 32, player.getY() + 60));
                    dustCooldown = DUST_COOLDOWN_MAX;
                }
    
                if(dustCooldown > 0) {
                    dustCooldown --;
                }
    
                for(ParticleEffect effect : particleEffects) {
                    effect.update();
                }
    
                
                checkCollisions();
                
            }
    
        
    
        }
    
        private void checkCollisions() {
            List<Entity> traps = map.getTraps();
            for (Entity trap : traps) {
                if(player.getBounds().intersects(trap.getBounds())) {
                    particleEffects.add(new PoisonBubbleEffect(trap.getX(), trap.getY()));
                    soundManager.stopClip("trap_warning");
                    player.collapse();
                    soundManager.playClip("game_over", false);
                    //System.out.println("Collision with trap!");
                    gameOver = true;
                    break;
                }
    
                double distance = calculateDistance(trap.getX(), trap.getY(), player.getX(), player.getY());
    
                if (distance <= TRAP_WARNING_DISTANCE) {
    
                    float volume = (float) (1 - (distance / TRAP_WARNING_DISTANCE));
                    float minVolume = 0.5f;
                    float maxVolume = 1.0f;
                    volume = minVolume + (volume * (maxVolume - minVolume));
                    if(!soundManager.getClip("trap_warning").isRunning()) {
                        soundManager.playClip("trap_warning", false);
                    }
    
                    soundManager.setVolume("trap_warning", volume);
                    
                    
                    //System.out.println("Trap Warning!");
                } else {
                    soundManager.stopClip("trap_warning");
                }
            }
    
            List<Entity> puzzlePieces = map.getPuzzlePieces();
            puzzlePieces.removeIf(piece -> {
                if (player.getBounds().intersects(piece.getBounds())) {
                    particleEffects.add(new SparkleEffect(piece.getX(), piece.getY()));
                    soundManager.playClip("puzzle_piece", false);
                    //System.out.println("Collision with puzzle piece!");
                    return true;
                }
                return false;
            });
            collectPuzzlePiece();
            
    
    
    
            if(puzzlePieces.isEmpty() && player.getBounds().intersects(map.getTreasure().getBounds())) {
                TreasureChest treasure = map.getTreasure();
                treasure.openChest();
                soundManager.playClip("treasure", false);
                //System.out.println("Reached Treasure Chest!");
                if(currentLevel < levels.size() - 1) {
                    currentLevel++;
                    startLevel(currentLevel);
                } else {
                    levelComplete = true;
                }
            }
        }

        public void collectPuzzlePiece() {
            if(map.getPuzzlePieces().isEmpty()) {
                scoreText = "Grab The Chest!";
                return;
            }
            scoreText = "Remaining Pieces: " + getTotalPuzzlePieces();
        }
    

    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void startGame() {
        if (gameOver || levelComplete) {
            resetGame();
        }
        isPaused = false;
        requestFocusInWindow();
        new Thread(this::GameLoop).start();
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resetGame() {
        startLevel(currentLevel);
        soundManager.stopClip("game_over");
        soundManager.stopClip("trap_warning");
        soundManager.stopClip("puzzle_piece");
        soundManager.stopClip("treasure");
        repaint();
    }



    public int getTotalPuzzlePieces() {
        return map.getPuzzlePieces().size();
    }


    public void GameLoop() {
        while (!gameOver && !levelComplete) {
            if(!isPaused) {
                update();
                repaint();
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
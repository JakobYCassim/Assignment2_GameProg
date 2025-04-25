import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Map {
    private List<Entity> traps;
    private List<Entity> puzzlePieces;
    private List<Enemy> enemies;
    private List<Entity> slowTraps;
    private TreasureChest treasure;
    private Image backgroundImage;
    private Random rand;
    private Player player;
    public static final int PLAYER_WIDTH = 80;
    public static final int PLAYER_HEIGHT = 80;
    public Map(int numPuzzlePieces, int numTraps, Player player) {
        traps = new ArrayList<>();
        backgroundImage = ImageManager.getImage("background");

        this.player = player;
        puzzlePieces = new ArrayList<>();
        rand = new Random();
        //Load Traps
        for(int i=0; i < numTraps; i++) {
            
            int x,y;
            do {
                x = rand.nextInt(660) + 100;
                y = rand.nextInt(460) + 50;
            } while(isOverlapping(x, y, traps));
            if(i % 2 == 0) {
                traps.add(new Trap(x, y, "trap_2"));
            }else {
                traps.add(new Trap(x, y, "trap_1"));
            }
        }
        //Load Slow Traps
        slowTraps = new ArrayList<>();  
        for(int i=0; i < numTraps; i++) {
            
            int x,y;
            do {
                x = rand.nextInt(660) + 100;
                y = rand.nextInt(460) + 50;
            } while(isOverlapping(x, y, slowTraps));
            slowTraps.add(new SlowTrap(x, y, "trap_2"));
        }

        //Load Enemies
        enemies = new ArrayList<>();
        for(int i=0; i < numTraps; i++) {
            
            int x,y;
            do {
                x = rand.nextInt(660) + 100;
                y = rand.nextInt(460) + 50;
            } while(isEnemyOverlapping(x, y, enemies));
            if(i % 2 == 0) {
                enemies.add(new Enemy(x, y, true));
            }else {
                enemies.add(new Enemy(x, y, false));
            }
        }

        for(int i=0; i < numPuzzlePieces; i++) {
            int x,y;
            do {
                x = rand.nextInt(660) + 100;
                y = rand.nextInt(460) + 50;
            } while(isOverlapping(x, y, puzzlePieces));
            puzzlePieces.add(new PuzzlePiece(x, y, "puzzle_piece"));
        }

        int treasureX, treasureY;
        do {
            treasureX = rand.nextInt(660) + 100;
            treasureY = rand.nextInt(460) + 50;
        } while(isOverlapping(treasureX, treasureY, puzzlePieces));
        treasure = new TreasureChest(700, 500, "treasure", "open_chest");

    }

    private boolean isEnemyOverlapping(int x, int y, List<Enemy> enemyList) {
        Rectangle newEnemyBounds = new Rectangle(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        for(Enemy e: enemyList) {
            Rectangle enemyBounds = e.getBounds();
            if(newEnemyBounds.intersects(enemyBounds)) {
                return true;
            }
        }

        if (player != null) {
            Rectangle playerBounds = player.getBounds();
            if(newEnemyBounds.intersects(playerBounds)) {
                return true;
            }
        }

        return false;
    }
        

    private boolean isOverlapping(int x, int y, List<Entity> entities) {
        Rectangle newEntityBounds = new Rectangle(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);
        for(Entity entity: entities) {
            Rectangle entityBounds = entity.getBounds();
            if(newEntityBounds.intersects(entityBounds)) {
                return true;
            }
        }

        if(player != null) {
            Rectangle playerBounds = player.getBounds();
            if(newEntityBounds.intersects(playerBounds)) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {

        if(backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, 800, 600, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 800, 600);
        }
        for(Entity trap: traps) {
            trap.draw(g);
           // System.out.println("Drawing trap: " + trap.getX() + " " + trap.getY());
        }

        for(Entity piece: puzzlePieces) {
            piece.draw(g);
        }

        for(Entity trap: slowTraps) {
            trap.draw(g);
        }

        for (Enemy enemy: enemies) {            
            enemy.draw(g);
        }

        if(puzzlePieces.isEmpty()) {
            treasure.draw(g);
        }
    }

    public List<Entity> getTraps() {
        return traps;
    }

    public List<Entity> getSlowTraps() {
        return slowTraps;
    }

     public List<Enemy> getEnemies() {    
        return enemies;
    }
    
    public List<Entity> getPuzzlePieces() {
        return puzzlePieces;
    }

    public TreasureChest getTreasure() {
        return treasure;
    }
}

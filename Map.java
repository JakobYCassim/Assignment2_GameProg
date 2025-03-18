import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class Map {
    private List<Entity> traps;
    private List<Entity> puzzlePieces;
    private TreasureChest treasure;
    private Image backgroundImage;

    public Map() {
        traps = new ArrayList<>();
        backgroundImage = ImageManager.getImage("background");
      //  traps.add(new Trap(100, 180, "trap_1"));
        traps.add(new Trap(200, 200, "trap_2"));
        traps.add(new Trap(300, 300, "trap_1"));
        traps.add(new Trap(400, 400, "trap_2"));

        puzzlePieces = new ArrayList<>();

        puzzlePieces.add(new PuzzlePiece(200, 300, "puzzle_piece"));
        puzzlePieces.add(new PuzzlePiece(400, 250, "puzzle_piece"));
        puzzlePieces.add(new PuzzlePiece(600, 400, "puzzle_piece"));
        puzzlePieces.add(new PuzzlePiece(500, 300, "puzzle_piece"));

        treasure = new TreasureChest(700, 500, "treasure", "open_chest");

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
        }

        for(Entity piece: puzzlePieces) {
            piece.draw(g);
        }

        if(puzzlePieces.isEmpty()) {
            treasure.draw(g);
        }
    }

    public List<Entity> getTraps() {
        return traps;
    }

    public List<Entity> getPuzzlePieces() {
        return puzzlePieces;
    }

    public TreasureChest getTreasure() {
        return treasure;
    }
}

public class Level {
    private int levelNumber;
    private int numPuzzlePieces;
    private int numTraps;
    

    public Level(int levelNumber, int numPuzzlePieces, int numTraps) {
        this.levelNumber = levelNumber;
        this.numPuzzlePieces = numPuzzlePieces;
        this.numTraps = numTraps;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getNumPuzzlePieces() {
        return numPuzzlePieces;
    }

    public int getNumTraps() {
        return numTraps;
    }
}

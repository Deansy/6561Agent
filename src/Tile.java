/**
 * Created by camsh on 17/11/2015.
 */
public class Tile {
    int value;
    Board.TileColor tileColor;

    int xPos;
    int yPos;

    public Tile(Board.TileColor tileColor, int xPos, int yPos) {
        this.tileColor = tileColor;
        this.xPos = xPos;
        this.yPos = yPos;
        this.value = 1;
    }

    public Tile(Board.TileColor tileColor, int xPos, int yPos, int value) {
        this.tileColor = tileColor;
        this.xPos = xPos;
        this.yPos = yPos;

        this.value = value;
    }
}

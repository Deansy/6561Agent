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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Tile otherTile = (Tile) obj;

        if (otherTile.tileColor != tileColor) {
            return false;
        }

        if (otherTile.value != value) {
            return false;
        }

        if (otherTile.xPos != xPos) {
            return false;
        }

        if (otherTile.yPos != yPos) {
            return false;
        }




        return true;
    }
}

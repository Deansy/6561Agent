/**
 * Created by camsh on 22/12/2015.
 */
enum TileColor {
    BLUE, RED, GREY, EMPTY
}
public class Tile {
    int value;
    TileColor tileColor;

    //int xPos;
    //int yPos;

    public Tile(TileColor tileColor, int xPos, int yPos) {
        this.tileColor = tileColor;
        //this.xPos = xPos;
        //this.yPos = yPos;
        this.value = 1;
    }

    public Tile(TileColor tileColor, int xPos, int yPos, int value) {
        this.tileColor = tileColor;
       // this.xPos = xPos;
       // this.yPos = yPos;

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

      //  if (otherTile.xPos != xPos) {
      //      return false;
      //  }

      //  if (otherTile.yPos != yPos) {
     //       return false;
      //  }




        return true;
    }
}

public class Main {
    public static void main(String [] args) {



        Board b = new Board();

        //b.placeTile(Board.TileColor.BLUE, 1, 0);
        //b.placeTile(Board.TileColor.BLUE, 3, 0);
        b.placeTile(Board.TileColor.BLUE, 2, 0);

        b.printBoard();

        b.slideLeft();

        b.printBoard();
    }
}

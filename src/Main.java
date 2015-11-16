
public class Main {
    public static void main(String [] args) {



        Board b = new Board();

        b.placeTile(Board.TileColor.BLUE, 2, 0, "1");
        b.placeTile(Board.TileColor.BLUE, 3, 0, "2");

        b.placeTile(Board.TileColor.BLUE, 1, 1, "2");
        b.placeTile(Board.TileColor.BLUE, 2, 1, "1");

        b.placeTile(Board.TileColor.BLUE, 2, 2, "2");

        b.placeTile(Board.TileColor.BLUE, 3, 3, "2");


        b.printBoard();


        b.slideLeft();


        b.printBoard();
    }
}

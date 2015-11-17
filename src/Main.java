
public class Main {
    public static void main(String [] args) {



        Board b = new Board();

        b.placeTile(Board.TileColor.BLUE, 0, 0, "1");
        b.placeTile(Board.TileColor.BLUE, 1, 0, "1");
        b.placeTile(Board.TileColor.BLUE, 2, 0, "1");
        b.placeTile(Board.TileColor.BLUE, 3, 0, "1");


        b.placeTile(Board.TileColor.BLUE, 1, 1, "2");
        b.placeTile(Board.TileColor.BLUE, 2, 1, "2");

        b.placeTile(Board.TileColor.BLUE, 2, 2, "2");

        b.placeTile(Board.TileColor.BLUE, 3, 3, "1");


        b.printBoard();


        //b.slideLeft();
        //b.slideLeft();
        b.slideDown();
        b.slideDown();
        b.printBoard();
    }
}


public class Main {
    public static void main(String [] args) {



        Board b = new Board();

        b.placeTile(Board.TileColor.RED, 0, 1, 3);
        b.placeTile(Board.TileColor.RED, 0, 2, 3);
        b.placeTile(Board.TileColor.BLUE, 0, 3, 3);

        b.placeTile(Board.TileColor.GREY, 1, 0, 1);
        b.placeTile(Board.TileColor.BLUE, 1, 1, 1);
        b.placeTile(Board.TileColor.BLUE, 1, 2, 1);
        b.placeTile(Board.TileColor.RED, 1, 3, 1);


        b.placeTile(Board.TileColor.BLUE, 2, 1, 9);
        b.placeTile(Board.TileColor.BLUE, 2, 3, 9);


        b.placeTile(Board.TileColor.RED, 3, 0, 9);
        b.placeTile(Board.TileColor.GREY, 3, 1, 3);
        b.placeTile(Board.TileColor.GREY, 3, 2, 1);
        b.placeTile(Board.TileColor.GREY, 3, 3, 1);


        b.printBoard();

        b.slideUp();


        b.printBoard();


    }

}

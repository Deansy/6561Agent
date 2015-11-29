
public class Main {
    public static void main(String [] args) {



        Board mainBoard = new Board();

        mainBoard.placeTile(Board.TileColor.RED, 0, 1, 3);
        mainBoard.placeTile(Board.TileColor.RED, 0, 2, 3);
        mainBoard.placeTile(Board.TileColor.BLUE, 0, 3, 3);

        mainBoard.placeTile(Board.TileColor.GREY, 1, 0, 1);
        mainBoard.placeTile(Board.TileColor.BLUE, 1, 1, 1);
        mainBoard.placeTile(Board.TileColor.BLUE, 1, 2, 1);
        mainBoard.placeTile(Board.TileColor.RED, 1, 3, 1);


        mainBoard.placeTile(Board.TileColor.BLUE, 2, 1, 9);
        mainBoard.placeTile(Board.TileColor.BLUE, 2, 3, 9);


        mainBoard.placeTile(Board.TileColor.RED, 3, 0, 9);
        mainBoard.placeTile(Board.TileColor.GREY, 3, 1, 3);
        mainBoard.placeTile(Board.TileColor.GREY, 3, 2, 1);
        mainBoard.placeTile(Board.TileColor.GREY, 3, 3, 1);


        Board testBoard = new Board();

        testBoard.placeTile(Board.TileColor.RED, 0, 0, 9);
        testBoard.placeTile(Board.TileColor.BLUE, 2, 0, 27);
        testBoard.placeTile(Board.TileColor.RED, 3, 0, 9);

        testBoard.placeTile(Board.TileColor.BLUE, 0, 1, 3);
        testBoard.placeTile(Board.TileColor.GREY, 3, 1, 3);

        testBoard.placeTile(Board.TileColor.GREY, 3, 2, 3);


        mainBoard.slideUp();


        if (mainBoard.equals(testBoard)) {
            System.out.println("Test passed");
        }
        else {
            System.out.println("Test failed");
            mainBoard.printBoard();
        }


    }

}

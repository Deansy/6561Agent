public class ExampleTest {
    public static void main(String [] args) {



        Board mainBoard = new Board();

        mainBoard.placeTile(TileColor.RED, 2, 1, 3);
        mainBoard.placeTile(TileColor.RED, 3, 1, 3);
        mainBoard.placeTile(TileColor.BLUE, 4, 1, 3);

        mainBoard.placeTile(TileColor.GREY, 1, 2, 1);
        mainBoard.placeTile(TileColor.BLUE, 2, 2, 1);
        mainBoard.placeTile(TileColor.BLUE, 3, 2, 1);
        mainBoard.placeTile(TileColor.RED, 4, 2, 1);


        mainBoard.placeTile(TileColor.BLUE, 2, 3, 9);
        mainBoard.placeTile(TileColor.BLUE, 4, 3, 9);


        mainBoard.placeTile(TileColor.RED, 1, 4, 9);
        mainBoard.placeTile(TileColor.GREY, 2, 4, 3);
        mainBoard.placeTile(TileColor.GREY, 3, 4, 1);
        mainBoard.placeTile(TileColor.GREY, 4, 4, 1);



        Board testBoard = new Board();

        testBoard.placeTile(TileColor.RED, 1, 1, 9);
        testBoard.placeTile(TileColor.BLUE, 1, 3, 27);
        testBoard.placeTile(TileColor.RED, 1, 4, 9);

        testBoard.placeTile(TileColor.BLUE, 2, 1, 3);
        testBoard.placeTile(TileColor.GREY, 2, 4, 3);

        testBoard.placeTile(TileColor.GREY, 3, 4, 3);


        mainBoard.slideBoard(Board.MOVE.UP);


        if (mainBoard.equals(testBoard)) {
            System.out.println("Test passed");
        }
        else {
            System.out.println("Test failed");
            mainBoard.printBoard(false);
        }


    }


}
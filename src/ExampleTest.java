
public class ExampleTest {
    public static void main(String [] args) {


        Board mainBoard = new Board();

        mainBoard.placeTile(TileColor.RED, 1, 1, 1);
        mainBoard.placeTile(TileColor.GREY, 2, 1, 1);
        mainBoard.placeTile(TileColor.BLUE, 3, 1, 1);

        mainBoard.printBoard(false);
        mainBoard.slideBoard(Board.MOVE.DOWN);

        Board testBoard = new Board();
        testBoard.placeTile(TileColor.RED, 4,1, 1);


        if (mainBoard.equals(testBoard)) {
            System.out.println("Test passed");
        }
        else {
            System.out.println("Test failed");
            mainBoard.printBoard(false);
        }

//
     }


}

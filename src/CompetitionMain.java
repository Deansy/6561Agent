import java.util.*;


public class CompetitionMain {

    // The current state of the game board
    private Board currentBoard;
    // Are we player A or B?
    private String player;

    private int currentMove;

    // Will hold all previous game boards
    private List<Board> previousStates;

    private Player currentPlayer;

    // ?, Will hold the type of move to get to that state?
    // Will allow easy analysis of previous moves?
    // This could be used to determine if current move is a place/move
    // Map<MOVE, Board> previousStates

    public static void main(String [] args) {


        CompetitionMain main = new CompetitionMain();

        main.gameLoop();


    }



    private void gameLoop() {

        currentBoard = new Board();

        Scanner in = new Scanner(System.in);

        currentMove = 1;
        previousStates = new ArrayList<>();

        player = in.next();

        //currentPlayer = new RandomMoveAgent(currentBoard);
        currentPlayer = new DepthLimitedDFSAgent(currentBoard);





        System.err.println("I am player: " + player);

        // If we are going first
        if (player.equals("A")) {
            // place a tile
            performPlaceTurn();
        }




        while (true) {

            if (in.hasNext()) {
                // Get the other players move
                String input = in.next();

                // The competition software wants us to exit.
                if (input.equals("Quit")) {

                    System.exit(0);
                }


                if (input.equals("U") || input.equals("D")|| input.equals("L")|| input.equals("R")) {
                    handleMoveTurn(input);
                }
                else {
                    handlePlaceTurn(input);
                }


                // The moves in the rotation which are tile places
                // All other moves are move turns
                List<Integer> placeTurns = Arrays.asList(1,2,3,6,7,8);


                if (placeTurns.contains((currentMove % 10))) {
                    //System.err.println("Doing Place: Turn: " + currentMove);
                    performPlaceTurn();
                }
                else {
                    //System.err.println("Doing Move: Turn: " + currentMove);
                    performMoveTurn();
                }

            }


        }


    }

    // Handle a place turn from the opponent
    private void handlePlaceTurn(String moveInfo) {
        currentMove++;
        currentPlayer.handlePlaceTurn(moveInfo);
    }


    // Handle a place turn from the opponent
    private void handleMoveTurn(String moveInfo) {
        currentMove++;
        currentPlayer.handleMoveTurn(moveInfo);
    }

    // Compute and perform a place turn
    private void performPlaceTurn() {
        currentMove++;
        currentPlayer.performPlaceTurn();
    }

    // Compute and perform a move turn
    private void performMoveTurn() {
        currentMove++;
        currentPlayer.performMoveTurn();
    }

    private TileColor getTileColorForCurrentMove() {
        TileColor colorToPlace = TileColor.EMPTY;
        // The move number in the current rotation
        int rotationID = currentMove % 10;

        if (rotationID == 1 || rotationID == 6) {
            colorToPlace = TileColor.BLUE;
        }
        else if (rotationID == 2 || rotationID == 7) {
            colorToPlace = TileColor.RED;
        }
        else if (rotationID == 3 || rotationID == 8) {
            colorToPlace = TileColor.GREY;
        }
        return colorToPlace;
    }

    public static TileColor getTileColorForMove(int move) {
        TileColor colorToPlace = TileColor.EMPTY;
        // The move number in the current rotation
        int rotationID = move % 10;

        if (rotationID == 1 || rotationID == 6) {
            colorToPlace = TileColor.BLUE;
        }
        else if (rotationID == 2 || rotationID == 7) {
            colorToPlace = TileColor.RED;
        }
        else if (rotationID == 3 || rotationID == 8) {
            colorToPlace = TileColor.GREY;
        }
        return colorToPlace;
    }


}

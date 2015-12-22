import java.util.*;


public class CompetitionMain {

    // The current state of the game board
    private Board currentBoard;
    // Are we player A or B?
    private String player;

    private int currentMove;
    private String previousMove;

    // Will hold all previous game boards
    private List<Board> previousStates;

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
                    System.err.println("Doing Place: Turn: " + currentMove);
                    performPlaceTurn();
                }
                else {
                    System.err.println("Doing Move: Turn: " + currentMove);
                    performMoveTurn();
                }

            }


        }


    }

    // Handle a place turn from the opponent
    private void handlePlaceTurn(String moveInfo) {
        // Logging this move
        previousMove = moveInfo;

        try {
            //System.err.println("Handle Place: " + currentMove );
            // I think this is sending the wrong values to my board, Hence the - 1
            int xPos = Integer.parseInt(moveInfo.substring(0,1));
            int yPos = Integer.parseInt(moveInfo.substring(1,2));
            currentBoard.placeTile(getTileColorForCurrentMove(),xPos - 1,yPos - 1, 1);
            currentMove++;
        }
        // TODO: Better error handling
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Handle a place turn from the opponent
    private void handleMoveTurn(String moveInfo) {
        // Logging this move
        previousMove = moveInfo;

        //System.err.println("Handle Move: " + currentMove );
        if (moveInfo.equals("U")) {
            currentBoard.slideUp();
        }
        else if (moveInfo.equals("D")) {
            currentBoard.slideDown();
        }
        else if (moveInfo.equals("L")) {
            currentBoard.slideLeft();
        }
        else if (moveInfo.equals("R")) {
            currentBoard.slideRight();
        }


        currentMove++;
    }

    // Compute and perform a place turn
    private void performPlaceTurn() {
        // TODO: Log the previous state

        // Place a random tile
        Random rand = new Random();
        int x = rand.nextInt(4);
        int y = rand.nextInt(4);

        // TODO: Fix the coordiante issue, make my grid implementation match the games
        if (currentBoard.isEmpty(x, y)) {

            x = x+1;
            y = y+1;

            String move = x + "" + y;
            if (!move.equals(previousMove)) {
                currentBoard.placeTile(getTileColorForCurrentMove(), x-1, y-1, 1);
                System.out.println(move);
                System.err.println("Move " + currentMove + " - X:" + x + " Y:" + y);
                currentMove++;
            }
            else {
                System.err.println("Duplicate move, Retrying");
                performPlaceTurn();
            }



            System.out.flush();
        }
        else {
            // Couldn't place a move, Try again
            System.err.println("Couldn't place a move, Try again");
            performPlaceTurn();
            //System.exit(0);
        }
    }
    // Compute and perform a move turn
    private void performMoveTurn() {
        // TODO: Log the previous state
        // TODO: Check we're not playing the same shift as the last one
        //      This is an invalid move

        // Perform a random move
        Random rand = new Random();
        int n = rand.nextInt(4);

        switch (n) {
            case 0:
                // UP
                if (previousMove.equals("U")) {
                    System.err.println("Duplicate move, Retrying");
                    performMoveTurn();
                }
                else {
                    System.out.println("U");
                    System.err.println("Move " + currentMove + "- U");
                    currentMove++;
                }
                break;
            case 1:
                // DOWN
                if (previousMove.equals("D")) {
                    System.err.println("Duplicate move, Retrying");
                    performMoveTurn();
                }
                else {
                    System.out.println("D");
                    System.err.println("Move " + currentMove + "- D");
                    currentMove++;
                }
                break;
            case 2:
                // LEFT
                if (previousMove.equals("L")) {
                    System.err.println("Duplicate move, Retrying");
                    performMoveTurn();
                }
                else {
                    System.out.println("L");
                    System.err.println("Move " + currentMove + "- L");
                    currentMove++;
                }
                break;
            case 3:
                // RIGHT
                if (previousMove.equals("R")) {
                    System.err.println("Duplicate move, Retrying");
                    performMoveTurn();
                }
                else {
                    System.out.println("R");
                    System.err.println("Move " + currentMove + "- R");
                    currentMove++;
                }
                break;
            default:
                System.exit(0);
                break;
        }



        System.out.flush();
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


}

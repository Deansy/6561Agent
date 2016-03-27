import java.util.Random;

/**
 * Created by camsh on 01/01/2016.
 */
public class RandomMoveAgent implements Player {

    int currentMove;
    Board currentBoard;
    private String previousMove;

    public RandomMoveAgent(Board board) {
        currentMove = 1;
        currentBoard = board;
    }

    public RandomMoveAgent(Board board, int currentMove) {
        this.currentMove = currentMove;
        currentBoard = board;
    }

    @Override
    public void handlePlaceTurn(String moveInfo) {
        previousMove = moveInfo;

        try {
            int xPos = Integer.parseInt(moveInfo.substring(0,1));
            int yPos = Integer.parseInt(moveInfo.substring(1,2));
            currentBoard.placeTile(CompetitionMain.getTileColorForMove(currentMove),xPos,yPos, 1);
            System.err.println("Move " + currentMove + " - X:" + xPos + " Y:" + yPos);
            currentMove++;
        }
        // TODO: Better error handling
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMoveTurn(String moveInfo) {
        previousMove = moveInfo;

        if (moveInfo.equals("U")) {
            currentBoard.slideBoard(Board.MOVE.UP);
        }
        else if (moveInfo.equals("D")) {
            currentBoard.slideBoard(Board.MOVE.DOWN);
        }
        else if (moveInfo.equals("L")) {
            currentBoard.slideBoard(Board.MOVE.LEFT);
        }
        else if (moveInfo.equals("R")) {
            currentBoard.slideBoard(Board.MOVE.RIGHT);
        }


        //System.err.println("Move " + currentMove + " - " + moveInfo);
        currentMove++;
    }

    @Override
    public void performPlaceTurn() {
        // Place a random tile
        Random rand = new Random();
        int x = rand.nextInt(4) + 1;
        int y = rand.nextInt(4) + 1;

        // TODO: Fix the coordiante issue, make my grid implementation match the games
        if (currentBoard.isEmpty(x, y)) {


            //x++;
            //y++;


            String move = x + "" + y;
            if (!move.equals(previousMove)) {
                currentBoard.placeTile(CompetitionMain.getTileColorForMove(currentMove), x, y, 1);
               // System.out.println(move);
                //System.err.println("Move " + currentMove + " - X:" + x + " Y:" + y);
                currentMove++;
            }
            else {
                //System.err.println("Duplicate move, Retrying");
                performPlaceTurn();
            }



            System.out.flush();
        }
        else {
            // Couldn't place a move, Try again
            //System.err.println("Couldn't place a move, Try again:" + x + "" + y);
            //performPlaceTurn();
            //System.exit(0);
        }
    }

    @Override
    public void performMoveTurn() {
        // Perform a random move
        Random rand = new Random();
        int n = rand.nextInt(4);

        String move = " ";

        switch (n) {
            case 0:
                // UP
                move = "U";
                break;
            case 1:
                // DOWN
                move = "D";
                break;
            case 2:
                // LEFT
                move = "L";
                break;
            case 3:
                // RIGHT
                move = "R";
                break;
            default:
                System.exit(0);
                break;
        }


        //if (previousMove.equals(move)) {
        //    System.err.println("Duplicate move, Retrying");
        //    performMoveTurn();
        //}
       // else {
            Board beforeMove = new Board(currentBoard);


            switch (move) {
                case "U" :
                    currentBoard.slideBoard(Board.MOVE.UP);
                    break;
                case "D":
                    currentBoard.slideBoard(Board.MOVE.DOWN);
                    break;
                case "L":
                    currentBoard.slideBoard(Board.MOVE.LEFT);
                    break;
                case "R":
                    currentBoard.slideBoard(Board.MOVE.RIGHT);
                    break;
            }

            //if (currentBoard.equals(beforeMove)) {
            //    System.err.println("Move had no effect");
          //      performMoveTurn();
            //}
           // else {
                //System.out.println(move);
                //System.err.println("Move " + currentMove + " - " + move);
                currentMove++;
           // }

       // }

        System.out.flush();
    }
}



import java.util.List;
import java.util.Random;

/**
 * Created by camsh on 09/01/2016.
 */
public class DepthLimitedDFSAgent implements Player {

    int currentMove;
    Board currentBoard;
    String previousMove;

    public DepthLimitedDFSAgent(Board board) {
        currentMove = 1;
        currentBoard = board;
    }

    // TODO: Move the generic handle code into an abstract class
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


    // TODO: Move the generic handle code into an abstract class
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
            currentBoard.slideBoard(Board.MOVE.UP);
        }
        else if (moveInfo.equals("R")) {
            currentBoard.slideBoard(Board.MOVE.RIGHT);
        }


        System.err.println("Move " + currentMove + " - " + moveInfo);
        currentMove++;
    }

    @Override
    public void performPlaceTurn() {
        System.err.println("Performing place turn, Move:" + currentMove);

        double score = 0;

        int depth = 4;
        int sampleSize = 1;

        Pair<Integer, Integer> moveToMake = new Pair<>(-1, -1);

        // Could cause a slow down by calling getPlaces twice?
         if (!currentBoard.getPlaces(CompetitionMain.getTileColorForMove(currentMove)).isEmpty()) {
             for (Pair<Integer, Integer> coord : currentBoard.getPlaces(CompetitionMain.getTileColorForMove(currentMove))) {
                 double resultCounter = 0;
                 int x = coord.first;
                 int y = coord.second;

                 Board newBoard = new Board(currentBoard);
                 newBoard.placeTile(CompetitionMain.getTileColorForMove(currentMove), x, y, 1);

                 for (int i = 0; i < sampleSize; i++) {
                     double result = DepthLimitedSearch(newBoard, depth);
                     resultCounter += result;
                 }


                 double averageScore = resultCounter / sampleSize;

                 if (averageScore >= score) {
                     score = averageScore;
                     moveToMake = new Pair<>(x, y);
                 }

             }
         }

        int x = moveToMake.first;
        int y = moveToMake.second;

        //TODO: Fix is empty
        if (currentBoard.isEmpty(x-1, y-1)) {

            currentBoard.placeTile(CompetitionMain.getTileColorForMove(currentMove), x, y, 1);
            System.out.println("" + x + "" + y);
            System.err.println("" + x + "" + y);
            currentMove++;

            System.out.flush();
        }
        else {
            System.err.println("Tile not empty, Trying again");
            this.performPlaceTurn();
        }
    }

    @Override
    public void performMoveTurn() {
        System.err.println("Performing move turn, Move: " + currentMove);

        double score = 0;
        Board.MOVE move = null;

        int depth = 4;
        int sampleSize = 1;

        // Could cause a slow down by calling getSlides twice?
        if (!currentBoard.getSlides().isEmpty()) {
            for (Board.MOVE m : currentBoard.getSlides()) {
                double resultCounter = 0;

                Board newBoard = new Board(currentBoard);
                newBoard.slideBoard(m);

                //Sample size was used in 2048 to compensate for the random aspect of 2048
                //This is not apart of 6561 so could be removed?
                for (int i = 0; i < sampleSize; i++) {
                    double result = DepthLimitedSearch(newBoard, depth);
                    resultCounter += result;
                }
                double averageScore = resultCounter / sampleSize;

                if (averageScore >= score) {
                    score = averageScore;
                    move = m;
                }
            }
        } else {
            System.err.println("No slides available");
        }

        System.err.println("Attempting to slide");

        // Perform the chosen move
        currentBoard.slideBoard(move);

        System.err.println(move);

        switch (move) {
            case LEFT:
                //currentBoard.slideLeft();
                System.out.println("L");
                break;
            case RIGHT:
                //currentBoard.slideRight();
                System.out.println("R");
                break;
            case UP:
                //currentBoard.slideUp();
                System.out.println("U");
                break;
            case DOWN:
                //currentBoard.slideDown();
                System.out.println("D");
                break;
        }


        currentMove++;
        System.out.flush();
    }

    private double DepthLimitedSearch(Board b, int depth) {
        double score = 0.0;


        if (depth == 0) {
            score = boardTotalHeuristic(b);
        }
        else {
            if (Board.placeTurns.contains((currentMove + depth) % 10)) {
                // Place Turn
                TileColor color = CompetitionMain.getTileColorForMove((currentMove + depth) % 10);
                // Color currently does nothing
                List<Pair<Integer, Integer>> places = b.getPlaces(color);


                for (Pair<Integer, Integer> pair : places) {
                    int x = pair.first;
                    int y = pair.second;

                    Board newBoard = new Board(b);

                    newBoard.placeTile(color,x,y,1);

                    double current = DepthLimitedSearch(newBoard, depth - 1);
                    score = Math.max(score, current);

                }
            }
            else {
                // Shift Turn
                List<Pair<Board.MOVE, Board>> slides = b.getSlidesWithBoard();

                for (Pair<Board.MOVE, Board> pair: slides) {
                        Board board = pair.second;
                        double current = DepthLimitedSearch(board, depth - 1);
                        score = Math.max(score, current);
                }
            }
        }
        return score;
    }


    private int boardTotalHeuristic(Board b) {
        int total = 0;
        for (int i = 0; i < b.boardWidth; i++) {
            for (int j = 0; j < b.boardHeight; j++) {
                total += b.getBoard()[i][j].value;
            }
        }

        return total;
    }
}

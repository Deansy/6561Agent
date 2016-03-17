

import java.util.ArrayList;
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
            //System.err.println("Move " + currentMove + " - X:" + xPos + " Y:" + yPos);
            System.err.println(moveInfo);
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
            currentBoard.slideBoard(Board.MOVE.LEFT);
        }
        else if (moveInfo.equals("R")) {
            currentBoard.slideBoard(Board.MOVE.RIGHT);
        }


        System.err.println(moveInfo);
        //System.err.println("Move " + currentMove + " - " + moveInfo);
        currentMove++;
    }

    @Override
    public void performPlaceTurn() {
        System.err.println("Performing place turn, Move:" + currentMove);

        double score = 0;

        int depth = 4;
        int sampleSize = 1;

        Pair<Integer, Integer> moveToMake = new Pair<>(-1, -1);


        int placesToGet = 5;

        // Could cause a slow down by calling getPlaces twice?
         if (!currentBoard.getPlaces(CompetitionMain.getTileColorForMove(currentMove), placesToGet).isEmpty()) {
             for (Pair<Integer, Integer> coord : currentBoard.getPlaces(CompetitionMain.getTileColorForMove(currentMove), placesToGet)) {
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

        if (currentBoard.isEmpty(x, y)) {

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

        // Perform the chosen move
        currentBoard.slideBoard(move);

        System.err.println(move);

        switch (move) {
            case LEFT:
                System.out.println("L");
                break;
            case RIGHT:
                System.out.println("R");
                break;
            case UP:
                System.out.println("U");
                break;
            case DOWN:
                System.out.println("D");
                break;
        }

        currentMove++;
        System.out.flush();
    }

    private double DepthLimitedSearch(Board b, int depth) {
        double score = 0.0;

        int placesToGet = 5;
        int hardCodedDepth = 4;


        if (depth == 0) {
            score = boardTotalHeuristic(b);
        }
        else {
            if (Board.placeTurns.contains((currentMove + ((hardCodedDepth + 1) - 1)) % 10)) {
                // Place Turn
                TileColor color = CompetitionMain.getTileColorForMove((currentMove + ((hardCodedDepth + 1) - 1)) % 10);
                // Color currently does nothing
                List<Pair<Integer, Integer>> places = b.getPlaces(color, placesToGet);


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


    private double boardTotalHeuristic(Board b) {

        int boardScore = boardScore(b);

        // We want to give a high priority to ensuring there is as many of the one tile as possible
        double oneColor = oneColorMajority(b);


        return (boardScore * 1.5)+ (oneColor * 20); //+ (maximisingBoard(b) * 1);//; //+ (largeCorners(b) * 40);// + (maximisingBoard(b) * 40);

    }

    private int boardScore(Board b) {
        int total = 0;
        for (int i = 0; i < b.boardWidth; i++) {
            for (int j = 0; j < b.boardHeight; j++) {
                total += b.getBoard()[i][j].value;
            }
        }

        return total;
    }

    // Encourage having the larger values in a corner
    private double largeCorners (Board b) {
        int score = 0;
        int largest = 0;
        int positionX = 0;
        int positionY = 0;

        for (int i = 0; i < 4; i+=3) {
            for (int j = 0; j < 4; j+=3) {
                if (b.getBoard()[i][j].tileColor != TileColor.EMPTY) {
                    int current = b.getBoard()[i][j].value;

                    if (current > largest) {
                        largest = current;
                        positionX = i;
                        positionY = j;
                    }
                }
            }
        }

        if (positionX == 0 && positionY == 0) {
            score += 1;
        }
        else if (positionX == 3 && positionY == 0) {
            score += 1;
        }
        else if (positionX == 0 && positionY == 3) {
            score += 1;
        }
        else if (positionX == 3 && positionY == 3) {
            score += 1;
        }

        return score;
    }

    // Checks the board for the the potential of slides that will increase the score

    private double maximisingBoard(Board b) {
        double runningScore = 0;
        for (int x = 0; x < b.boardHeight; x++) {
            for (int y = 0; y < b.boardWidth; y++) {
                Tile currentTile = b.getBoard()[x][y];
                List<Tile> adjacentTiles = new ArrayList<>();

                // Theres probably a better way of doing this but I can't think of it.

                if (x == 0 && y == 0) {
                    Tile rightTile = b.getBoard()[x][y+1];
                    Tile downTile = b.getBoard()[x+1][y];

                    adjacentTiles.add(rightTile);
                    adjacentTiles.add(downTile);
                } else if (x == 0 && y == 3) {
                    Tile leftTile = b.getBoard()[x][y-1];
                    Tile downTile = b.getBoard()[x+1][y];

                    adjacentTiles.add(leftTile);
                    adjacentTiles.add(downTile);
                }
                else if (x == 3 && y == 0) {
                    Tile rightTile = b.getBoard()[x][y+1];
                    Tile upTile = b.getBoard()[x-1][y];

                    adjacentTiles.add(rightTile);
                    adjacentTiles.add(upTile);
                }
                else if (x == 3 && y == 3) {
                    Tile leftTile = b.getBoard()[x][y-1];
                    Tile upTile = b.getBoard()[x-1][y];

                    adjacentTiles.add(leftTile);
                    adjacentTiles.add(upTile);
                }
                else if (y == 0) {
                    Tile rightTile = b.getBoard()[x][y+1];
                    Tile upTile = b.getBoard()[x-1][y];
                    Tile downTile = b.getBoard()[x+1][y];

                    adjacentTiles.add(rightTile);
                    adjacentTiles.add(upTile);
                    adjacentTiles.add(downTile);
                }
                else if (y == 3) {
                    Tile leftTile = b.getBoard()[x][y-1];
                    Tile upTile = b.getBoard()[x-1][y];
                    Tile downTile = b.getBoard()[x+1][y];

                    adjacentTiles.add(leftTile);
                    adjacentTiles.add(upTile);
                    adjacentTiles.add(downTile);
                }
                else if (x == 0) {
                    Tile rightTile = b.getBoard()[x][y+1];
                    Tile leftTile = b.getBoard()[x][y-1];
                    Tile downTile = b.getBoard()[x+1][y];

                    adjacentTiles.add(rightTile);
                    adjacentTiles.add(leftTile);
                    adjacentTiles.add(downTile);
                }
                else if (x == 3) {
                    Tile rightTile = b.getBoard()[x][y+1];
                    Tile leftTile = b.getBoard()[x][y-1];
                    Tile upTile = b.getBoard()[x-1][y];

                    adjacentTiles.add(rightTile);
                    adjacentTiles.add(leftTile);
                    adjacentTiles.add(upTile);
                }
                else {
                    Tile rightTile = b.getBoard()[x][y+1];
                    Tile leftTile = b.getBoard()[x][y-1];
                    Tile upTile = b.getBoard()[x-1][y];
                    Tile downTile = b.getBoard()[x+1][y];

                    adjacentTiles.add(rightTile);
                    adjacentTiles.add(leftTile);
                    adjacentTiles.add(upTile);
                    adjacentTiles.add(downTile);
                }


                for (Tile tile : adjacentTiles) {
                    if (tile.value == currentTile.value) {
                        if (tile.tileColor == currentTile.tileColor) {
                            // Merge
                            runningScore += tile.value * 1;
                        }
                        else {
                            // Same value, Different color, Deletion
                            //runningScore -= tile.value;
                        }
                    }
                }
            }
        }

        return runningScore;
    }



    // Heuristic to reward a majority of one colors
    private double oneColorMajority(Board b) {
        int redTiles = 0;
        int blueTiles = 0;
        int greyTiles = 0;

        for (int x = 0; x < b.boardHeight; x++) {
            for (int y = 0; y < b.boardWidth; y++) {
                Tile t = b.getBoard()[x][y];

                if (t.tileColor == TileColor.RED) {
                    redTiles++;
                }
                if (t.tileColor == TileColor.BLUE) {
                    blueTiles++;
                }
                if (t.tileColor == TileColor.GREY) {
                    greyTiles++;
                }
            }
        }

        int totalTiles = redTiles + blueTiles + greyTiles;
        if (totalTiles > 0) {

            int redBlue = Math.max(redTiles, blueTiles);
            int blueGrey = Math.max(blueTiles, greyTiles);

            int mixMax = Math.max(redBlue, blueGrey);

            if (mixMax == redTiles) {
                return redTiles / totalTiles;
            } else if (mixMax == blueTiles) {
                return blueTiles / totalTiles;
            } else if (mixMax == greyTiles) {
                return greyTiles / totalTiles;
            }
        }

        return 0;
    }

    private int numEmptyTiles(Board b) {
        int emptyTiles = 0;

        for (int x = 0; x < b.boardHeight; x++) {
            for (int y = 0; y < b.boardWidth; y++) {
                Tile t = b.getBoard()[x][y];
                if (t.tileColor == TileColor.EMPTY) {
                    emptyTiles++;
                }
            }
        }

        return emptyTiles;
    }
}

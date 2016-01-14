import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by camsh on 22/12/2015.
 */
public class GameSimulator {

    public static void main(String[] args) {
        GameSimulator gp = new GameSimulator();

    }

    public GameSimulator() {

        Board game = new Board();
        Player p = new DepthLimitedDFSAgent(game);

        try {

            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String line;

            while ((line = stdin.readLine()) != null && line.length() != 0) {
                String[] tokens = line.split(" ");

                if (line.startsWith("Round")) {
                    // Get round
                    int moveNumber = Integer.parseInt(tokens[1]);
                    String move = tokens[4];

                   if (move.equals("U")) {
                       game.slideBoard(Board.MOVE.UP);

                   } else if (move.equals("D")) {
                       game.slideBoard(Board.MOVE.DOWN);

                   } else if (move.equals("L")) {
                       game.slideBoard(Board.MOVE.LEFT);

                   } else if (move.equals("R")) {
                       game.slideBoard(Board.MOVE.RIGHT);
                   }
                    else {
                       // Move
                       int x = Integer.parseInt(move.substring(0, 1));
                       int y = Integer.parseInt(move.substring(1, 2));


                       game.placeTile(getTileColorForCurrentMove(moveNumber), x, y ,1);
                   }


                }


                // Check exit
                if (line.equals("exit")) {
                    System.exit(0);
                }
                // Is print command

                if (line.equals("print")) {
                    game.printBoard(false);
                }

                if (line.startsWith("place")) {

                    if (tokens[3].toLowerCase().equals("R".toLowerCase())) {
                        game.placeTile(TileColor.RED, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[4]));
                    }

                    if (tokens[3].toLowerCase().equals("B".toLowerCase())) {
                        game.placeTile(TileColor.BLUE, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[4]));

                    }

                    if (tokens[3].toLowerCase().equals("G".toLowerCase())) {
                        game.placeTile(TileColor.GREY, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[4]));
                    }


                }

                if (line.startsWith("move")) {
                    if (tokens[1].equals("U")) {
                        game.slideBoard(Board.MOVE.UP);
                    }
                    if (tokens[1].equals("D")) {
                        game.slideBoard(Board.MOVE.DOWN);
                    }
                    if (tokens[1].equals("L")) {
                        game.slideBoard(Board.MOVE.LEFT);
                    }
                    if (tokens[1].equals("R")) {
                        game.slideBoard(Board.MOVE.RIGHT);
                    }

                }

                if (line.startsWith("x")) {
                    game.placeTile(TileColor.BLUE, 1 , 2, 1);
                }

                if (line.startsWith("aiplace")) {

                    p.performPlaceTurn();
                }

                if (line.startsWith("slide")) {

                    p.performMoveTurn();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TileColor getTileColorForCurrentMove(int currentMove) {
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

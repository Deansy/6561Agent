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
                       game.slideUp();

                   } else if (move.equals("D")) {
                       game.slideDown();

                   } else if (move.equals("L")) {
                       game.slideLeft();

                   } else if (move.equals("R")) {
                       game.slideRight();
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
                        game.slideUp();
                    }
                    if (tokens[1].equals("D")) {
                        game.slideDown();
                    }
                    if (tokens[1].equals("L")) {
                        game.slideLeft();
                    }
                    if (tokens[1].equals("R")) {
                        game.slideRight();
                    }

                }

                if (line.startsWith("x")) {
                    game.placeTile(TileColor.BLUE, 1 , 2, 1);
                }

                if (line.startsWith("aiplace")) {
                    Player p = new DepthLimitedDFSAgent(game);
                    p.performPlaceTurn();
                }

                if (line.startsWith("slide")) {
                    Player p = new DepthLimitedDFSAgent(game);
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

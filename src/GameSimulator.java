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

                if (line.startsWith("help")) {
                    System.out.println("print - prints current board");
                    System.out.println("exit - exits the current game");
                    System.out.println("place - x y color(R,B,G) value - e.g. 1 1 R 3");
                    System.out.println("move - direction(U,D,L,R)");
                    System.out.println("aiplace - AI will perform a place move on the board");
                    System.out.println("aislide - AI will perfrom a slide move on the board");
                }

                // For pasting in from logs?
                if (line.startsWith("round")) {
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


                if (line.startsWith("aiplace")) {

                    ElapsedTimer t = new ElapsedTimer();
                    p.performPlaceTurn();
                    System.out.println(t);
                }

                if (line.startsWith("aislide")) {
                    p.performMoveTurn();
                }

                if(line.startsWith("aiplay")) {
                    ElapsedTimer t = new ElapsedTimer();
                    p.performPlaceTurn();
                    p.performPlaceTurn();
                    p.performPlaceTurn();

                    p.performMoveTurn();
                    p.performMoveTurn();

                    p.performPlaceTurn();
                    p.performPlaceTurn();
                    p.performPlaceTurn();


                    p.performMoveTurn();
                    p.performMoveTurn();
                    System.out.println(t);
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

import java.util.Scanner;

/**
 * Created by camsh on 22/12/2015.
 */
public class GamePlayer {

    public static void main(String [] args) {
        GamePlayer gp = new GamePlayer();

    }

    public GamePlayer() {

        Board game = new Board();


           while (true) {
               // Get input
               Scanner reader = new Scanner(System.in);
               String n = reader.nextLine();

               // Check exit
               if (n.equals("exit")) {
                   System.exit(0);
               }
               // Is print command

               if (n.equals("print")) {
                   game.printBoard(false);
               }

               if (n.startsWith("place")) {
                   String[] tokens = n.split(" ");
                   if (tokens[3].toLowerCase().equals("R".toLowerCase())) {
                       game.placeTile(TileColor.RED, Integer.parseInt(tokens[1]) - 1 , Integer.parseInt(tokens[2]) -1 , 1);
                   }

                   if (tokens[3].toLowerCase().equals("B".toLowerCase())) {
                       game.placeTile(TileColor.BLUE, Integer.parseInt(tokens[1]) - 1 , Integer.parseInt(tokens[2]) -1 , 1);

                   }

                   if (tokens[3].toLowerCase().equals("G".toLowerCase())) {
                       game.placeTile(TileColor.GREY, Integer.parseInt(tokens[1]) - 1 , Integer.parseInt(tokens[2]) -1 , 1);
                   }


               }

               if (n.startsWith("move")) {
                   String[] tokens = n.split(" ");
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

           }
    }

}

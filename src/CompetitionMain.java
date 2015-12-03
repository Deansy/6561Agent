import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by camsh on 30/11/2015.
 */
public class CompetitionMain {

    Board n;
    String player;
    int currentMove;
    List<Board> previousStates;

    public static void main(String [] args) {

        CompetitionMain main = new CompetitionMain();

        main.gameLoop();

    }



    public void gameLoop() {

        n = new Board();

        Scanner in = new Scanner(System.in);

        currentMove = 1;
        previousStates = new ArrayList<Board>();


        player = in.next();

        System.err.println("I am player: " + player);

        // If we are going first
        if (player.equals("A")) {
            // place a tile

//            n.placeTile(TileColor.BLUE, 0, 0, 1);
//            System.out.println("11");
//            currentMove++;
//            System.out.flush();
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


                // TODO: Add logic to determine this
                boolean isPlaceTurn = true;

                // Play our move

                if (isPlaceTurn) {
                    performPlaceTurn();
                }
                else {
                    performMoveTurn();
                }

            }


        }


    }

    // Handle a place turn from the opponent
    private void handlePlaceTurn(String moveInfo) {
        // TODO: Log the previous state
        try {
            // TODO: Enter the correct colour of tile
            n.placeTile(TileColor.RED, Integer.parseInt(moveInfo.substring(0,0)), Integer.parseInt(moveInfo.substring(1,1)), 1);
            currentMove++;
        }
        // TODO: Better error handling
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Handle a place turn from the opponent
    private void handleMoveTurn(String moveInfo) {
        // TODO: Log the previous state

        if (moveInfo.equals("U")) {
            n.slideUp();
        }
        else if (moveInfo.equals("D")) {
            n.slideDown();
        }
        else if (moveInfo.equals("L")) {
            n.slideLeft();
        }
        else if (moveInfo.equals("R")) {
            n.slideRight();
        }


        currentMove++;
    }

    // Compute and perform a place turn
    private void performPlaceTurn() {
        // TODO: Log the previous state

        //TODO: Add AI Logic
        n.placeTile(TileColor.GREY, 1,2, 1);
        System.out.println("23");
        System.out.flush();
    }
    // Compute and perform a move turn
    private void performMoveTurn() {
        // TODO: Log the previous state
    }


    private enum TileColor {
        BLUE, RED, GREY, EMPTY
    }

    private class Board {


        private Tile board[][];
        final int boardWidth = 4;
        final int boardHeight = 4;

        // TODO: Relocate these?
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_BLACK = "\u001B[30m";


        public Board() {
            board = new Tile[boardWidth][boardHeight] ;
            initBoard();

        }

        public void initBoard() {

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    board[i][j] = new Tile(TileColor.EMPTY, i, j, 0);
                }

            }


        }

        // An idea to multi thread the shifting?
        // Have a function which only slides one row
        // Create a thread for each row and have each thread slide only one row



        public void slideLeft() {

            // Code must be run twice to ensure proper shifting and merging
            for (int i = 0; i < 2; i++) {

                // Copy the board, Perform any operations and replace the board afterwards
                Tile[][] boardCopy = board.clone();


                for (int y = 0; y < 4; y++) {
                    for (int x = 0; x < 4; x++) {
                        if (boardCopy[x][y].value == 0) {

                            for (int k = x + 1; k < 4; k++) {
                                if (boardCopy[k][y].value != 0) {
                                    boardCopy[x][y] = boardCopy[k][y];

                                    boardCopy[x][y].xPos = x;
                                    boardCopy[x][y].yPos = y;

                                    boardCopy[k][y] = new Tile(TileColor.EMPTY, k, y, 0);
                                    break;
                                }
                            }
                        }
                    }
                }

                board = boardCopy;
                // Prevents new pieces from being modified on the same turn as they are created
                if (i == 0) {
                    mergeLeft();
                }
            }
        }

        private void mergeRight() {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {

                    Tile tileOne = board[j][i];
                    Tile tileTwo = board[j+1][i];


                    // We don't want to deal with 'empty tiles' and only with tiles which have the same value
                    if ((tileOne.value != 0) && tileOne.value == tileTwo.value ) {

                        // Merge if the tiles are the same color
                        if (tileOne.tileColor == tileTwo.tileColor) {
                            board[j][i] = new Tile(tileOne.tileColor, j, i, tileOne.value * 3);
                            board[j+1][i] = new Tile(TileColor.EMPTY, j+1, i, 0);
                        }
                        // Otherwise the tiles will be a different color
                        // In this case we want to 'destroy' the tiles
                        else {
                            board [j][i] = new Tile(TileColor.EMPTY, j, i, 0);
                            board [j+1][i] = new Tile(TileColor.EMPTY, j + 1, i, 0);
                        }

                    }
                }
            }
        }

        private void mergeLeft() {
            for (int i = 0; i < 4; i++) {
                for (int j = 3; j > 0; j--) {

                    Tile tileOne = board[j][i];
                    Tile tileTwo = board[j-1][i];

                    // We don't want to deal with 'empty tiles' and only with tiles which have the same value
                    if ((tileOne.value != 0) && tileOne.value == tileTwo.value ) {

                        // Merge if the tiles are the same color
                        if (tileOne.tileColor == tileTwo.tileColor) {
                            board[j][i] = new Tile(tileOne.tileColor, j, i, tileOne.value * 3);
                            board[j-1][i] = new Tile(TileColor.EMPTY, j-1, i, 0);
                        }
                        // Otherwise the tiles will be a different color
                        // In this case we want to 'destroy' the tiles
                        else {
                            board [j][i] = new Tile(TileColor.EMPTY, j, i, 0);
                            board [j-1][i] = new Tile(TileColor.EMPTY, j - 1, i, 0);
                        }


                    }



                }
            }
        }

        private void mergeDown() {
            for (int i = 0; i < 4; i++) {
                for (int j = 3; j > 0; j--) {
                    Tile tileOne = board[i][j];
                    Tile tileTwo = board[i][j-1];

                    // We don't want to deal with 'empty tiles' and only with tiles which have the same value
                    if ((tileOne.value != 0) && tileOne.value == tileTwo.value ) {

                        // Merge if the tiles are the same color
                        if (tileOne.tileColor == tileTwo.tileColor) {
                            board[i][j] = new Tile(tileOne.tileColor, i, j, tileOne.value * 3);
                            board[i][j-1] = new Tile(TileColor.EMPTY, i, j - 1, 0);
                        }
                        // Otherwise the tiles will be a different color
                        // In this case we want to 'destroy' the tiles
                        else{
                            //
                            board[i][j] = new Tile(TileColor.EMPTY, i, j, 0);
                            board[i][j-1] = new Tile(TileColor.EMPTY, i, j - 1, 0);
                        }




                    }
                }
            }
        }
        //
        private void mergeUp() {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {

                    Tile tileOne = board[i][j];
                    Tile tileTwo = board[i][j+1];

                    // We don't want to deal with 'empty tiles' and only with tiles which have the same value
                    if ((tileOne.value != 0) && tileOne.value == tileTwo.value ) {

                        // Merge if the tiles are the same color
                        if (tileOne.tileColor == tileTwo.tileColor) {
                            board[i][j] = new Tile(tileOne.tileColor, i, j, tileOne.value * 3);
                            board[i][j+1] = new Tile(TileColor.EMPTY, i, j + 1, 0);
                        }
                        // Otherwise the tiles will be a different color
                        // In this case we want to 'destroy' the tiles
                        else {
                            board[i][j] = new Tile(TileColor.EMPTY, i, j, 0);
                            board[i][j+1] = new Tile(TileColor.EMPTY, i, j + 1, 0);
                        }
                    }
                }
            }
        }



        public void slideRight() {

            // Code must be run twice to ensure all tiles are shifted and merged
            for (int i = 0; i < 2; i++) {

                // Copy the board, Perform any operations and replace the board afterwards
                Tile[][] boardCopy = board.clone();


                for (int y = 0; y < 4; y++) {
                    for (int x = 3; x > 0; x--) {
                        if (boardCopy[x][y].value == 0) {

                            for (int k = x - 1; k >= 0; k--) {
                                if (boardCopy[k][y].value != 0) {
                                    boardCopy[x][y] = boardCopy[k][y];

                                    boardCopy[x][y].xPos = x;
                                    boardCopy[x][y].yPos = y;

                                    boardCopy[k][y] = new Tile(TileColor.EMPTY, k, y, 0);
                                    break;
                                }
                            }
                        }
                    }
                }

                board = boardCopy;
                // Prevents new pieces from being modified on the same turn as they are created
                if (i == 0) {
                    mergeRight();
                }
            }

        }

        public void slideUp() {

            // Code must be run twice to ensure proper shifting and merging
            for (int i = 0; i < 2; i++) {
                // Copy the board, Perform any operations and replace the board afterwards
                Tile[][] boardCopy = board.clone();


                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        if (boardCopy[x][y].value == 0) {

                            for (int k = y + 1; k < 4; k++) {
                                if (boardCopy[x][k].value != 0) {
                                    boardCopy[x][y] = boardCopy[x][k];

                                    boardCopy[x][y].xPos = x;
                                    boardCopy[x][y].yPos = y;

                                    boardCopy[x][k] = new Tile(TileColor.EMPTY, x, k, 0);
                                    break;
                                }
                            }
                        }
                    }

                }

                board = boardCopy;
                // Prevents new pieces from being modified on the same turn as they are created
                if (i == 0)  {
                    mergeUp();
                }
            }
        }

        public void slideDown() {

            for (int i = 0; i < 2; i++) {
                // Copy the board, Perform any operations and replace the board afterwards
                Tile[][] boardCopy = board.clone();


                for (int x = 0; x < 4; x++) {
                    for (int y = 3; y >= 0; y--) {
                        if (boardCopy[x][y].value == 0) {

                            for (int k = y - 1; k >= 0; k--) {
                                if (boardCopy[x][k].value != 0) {
                                    boardCopy[x][y] = boardCopy[x][k];

                                    boardCopy[x][y].xPos = x;
                                    boardCopy[x][y].yPos = y;

                                    boardCopy[x][k] = new Tile(TileColor.EMPTY, x, k, 0);
                                    break;
                                }
                            }
                        }
                    }

                }

                board = boardCopy;

                if (i == 0) {
                    mergeDown();
                }
            }

        }


        public void placeTile(TileColor tileColor, int xPos, int yPos, int value) {
            if (xPos <= boardWidth - 1 && xPos >= 0) {
                if (yPos <= boardHeight - 1 && yPos >= 0) {

                    if (board[xPos][yPos].tileColor != TileColor.EMPTY) {
                        System.out.println("Can only place tiles in empty slots");
                    }
                    else {
                        board[xPos][yPos] = new Tile(tileColor, xPos, yPos, value);
                    }
                }
                else {
                    System.out.println("Invalid Y coordinate");
                }
            }
            else {
                System.out.println("Invalid X coordinate");

            }
        }

        public void printBoard() {
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (board[x][y].tileColor == TileColor.BLUE) {
                        //System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
                        System.out.print(ANSI_BLUE + board[x][y].value + " " + ANSI_RESET);

                    }
                    else if (board[x][y].tileColor == TileColor.GREY) {
                        System.out.print(ANSI_BLACK + board[x][y].value + " " + ANSI_RESET);
                    }
                    else if(board[x][y].tileColor == TileColor.RED) {
                        System.out.print(ANSI_RED + board[x][y].value + " " + ANSI_RESET);
                    }
                    else {
                        System.out.print("0 ");
                    }
                }
                System.out.println(" ");
            }

            System.out.println(" ");
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            Board otherBoard = (Board) obj;

            for (int x = 0; x < boardWidth; x++) {
                for (int y = 0; y < boardHeight; y++) {
                    if (!otherBoard.board[x][y].equals(board[x][y])) {
                        return false;
                    }
                }
            }


            return true;
        }
    }

    class Tile {
        int value;
        TileColor tileColor;

        int xPos;
        int yPos;

        public Tile(TileColor tileColor, int xPos, int yPos) {
            this.tileColor = tileColor;
            this.xPos = xPos;
            this.yPos = yPos;
            this.value = 1;
        }

        public Tile(TileColor tileColor, int xPos, int yPos, int value) {
            this.tileColor = tileColor;
            this.xPos = xPos;
            this.yPos = yPos;

            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            Tile otherTile = (Tile) obj;

            if (otherTile.tileColor != tileColor) {
                return false;
            }

            if (otherTile.value != value) {
                return false;
            }

            if (otherTile.xPos != xPos) {
                return false;
            }

            if (otherTile.yPos != yPos) {
                return false;
            }




            return true;
        }
    }


}

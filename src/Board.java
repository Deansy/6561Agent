

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by camsh on 22/12/2015.
 */
public class Board {


    private Tile board[][];
    final int boardWidth = 4;
    final int boardHeight = 4;
    public static List<Integer> placeTurns = Arrays.asList(1,2,3,6,7,8);

    // TODO: Relocate these?
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_BLACK = "\u001B[30m";


    public Board() {
        board = new Tile[boardWidth][boardHeight] ;
        initBoard();

    }

    public Board(Board oldBoard) {
        // A more effecient method is probably possible
        board = new Tile[boardWidth][boardHeight] ;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                board[x][y] = oldBoard.board[x][y];
            }

        }
    }

    public void initBoard() {

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                board[x][y] = new Tile(TileColor.EMPTY, x, y, 0);
            }
        }




    }

    // An idea to multi thread the shifting?
    // Have a function which only slides one row
    // Create a thread for each row and have each thread slide only one row


    public enum MOVE {
        LEFT, UP, RIGHT, DOWN;
    }

    // Returns a list of pairs which contain a slide direction and the resultant board state
    public List<Pair<MOVE, Board>> getSlidesWithBoard() {
        List<Pair<MOVE, Board>> validSlidesWithBoard = new ArrayList<>();
        Board b;

        // Left
        b = new Board(this);
        b.slideUp();
        if (!b.equals(this)) {
            // The slide affected something
            Pair<MOVE, Board> p = new Pair<>(MOVE.UP, b);
            validSlidesWithBoard.add(p);

        }

        b = new Board(this);
        b.slideDown();
        if (!b.equals(this)) {
            // The slide affected something
            Pair<MOVE, Board> p = new Pair<>(MOVE.DOWN, b);
            validSlidesWithBoard.add(p);
        }

        b = new Board(this);
        b.slideLeft();
        if (!b.equals(this)) {
            // The slide affected something
            Pair<MOVE, Board> p = new Pair<>(MOVE.LEFT, b);
            validSlidesWithBoard.add(p);
        }

        b = new Board(this);
        b.slideRight();
        if (!b.equals(this)) {
            // The slide affected something
            Pair<MOVE, Board> p = new Pair<>(MOVE.RIGHT, b);
            validSlidesWithBoard.add(p);
        }
        return validSlidesWithBoard;
    }

    // Returns a list of directions which are possible from the current state
    public List<MOVE> getSlides() {

        List<MOVE> validSlides = new ArrayList<>();
        Board b;


        b = new Board(this);
        b.slideUp();

        if (!b.equals(this)) {
            // The slide affected something
            validSlides.add(MOVE.UP);
        }

        b = new Board(this);
        b.slideDown();
        if (!b.equals(this)) {
            // The slide affected something
            validSlides.add(MOVE.DOWN);
        }

        b = new Board(this);
        b.slideLeft();
        if (!b.equals(this)) {
            // The slide affected something
            validSlides.add(MOVE.LEFT);
        }

        b = new Board(this);
        b.slideRight();
        if (!b.equals(this)) {
            // The slide affected something
            validSlides.add(MOVE.RIGHT);
        }

        return validSlides;
    }

    // Returns the raw game board
    public Tile[][] getBoard() {
        return board;
    }

    // Slide the board in a given direction
    public void slideBoard(MOVE m) {
        switch (m) {
            case LEFT:
                this.slideLeft();
                break;
            case RIGHT:
                this.slideRight();
                break;
            case UP:
                this.slideUp();
                break;
            case DOWN:
                this.slideDown();
                break;
        }
    }

    // TODO: Think up a better parameter name
    // Returns all possible tiles for a placment

    //TODO: My board deals in 0-3, The game wants 1-4, This is a problem
    public List<Pair<Integer, Integer>> getPlaces(TileColor colorToConsider) {

        // A list of grid coordinates for tile placement
        List<Pair<Integer, Integer>> places = new ArrayList<>();


        for (int x = 1; x <= boardHeight; x++) {
            for (int y = 1; y <= boardWidth; y++) {
                // For each tile

                if (isEmpty(x, y)) {
                    // Add each possible location suitable for a tile here
                    places.add(new Pair<>(x, y));
                }
            }
        }

        return places;
    }



    private void mergeUp() {
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
                        board [j+1][i] = new Tile(TileColor.EMPTY, j+1, i, 0);
                    }

                }
            }
        }
    }

    private void mergeDown() {
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

    private void mergeRight() {
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
    private void mergeLeft() {
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

    private void slideUp() {

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
                mergeUp();
            }
        }
    }

    private void slideDown() {

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
                mergeDown();
            }
        }

    }

    private void slideLeft() {

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
                mergeLeft();
            }
        }
    }

    private void slideRight() {

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
                mergeRight();
            }
        }

    }


    public void placeTile(TileColor tileColor, int xPos, int yPos, int value) {
        if (xPos <= boardWidth && xPos >= 1) {
            if (yPos <= boardHeight && yPos >= 1) {




//                if (board[xPos-1][yPos-1].value != 0) {
//                    System.err.println("Can only place tiles in empty slots, Printing board");
//                    System.err.println("X:" + xPos + " Y:" + yPos );
//
//                    printBoard(true);
//                }

                if(board[xPos-1][yPos-1].tileColor != TileColor.EMPTY) {
                    System.err.println("Can only place tiles in empty slots, Printing board");
                    System.err.println("X:" + xPos + " Y:" + yPos );

                    printBoard(true);
                }
                else {
                    board[xPos-1][yPos-1] = new Tile(tileColor, xPos-1, yPos-1, value);
                }
            }
            else {
                System.err.println("Invalid Y coordinate: " + yPos);
            }
        }
        else {
            System.err.println("Invalid X coordinate: " + xPos);

        }
    }

    public void printBoard(boolean errorPrint) {
        if (errorPrint) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (board[x][y].tileColor == TileColor.BLUE) {
                        //System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
                        System.err.print(ANSI_BLUE + board[x][y].value + " " + ANSI_RESET);

                    } else if (board[x][y].tileColor == TileColor.GREY) {
                        System.err.print(ANSI_BLACK + board[x][y].value + " " + ANSI_RESET);
                    } else if (board[x][y].tileColor == TileColor.RED) {
                        System.err.print(ANSI_RED + board[x][y].value + " " + ANSI_RESET);
                    } else {
                        System.err.print("0 ");
                    }
                }
                System.err.println(" ");
            }

            System.err.println(" ");
        }
        else {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (board[x][y].tileColor == TileColor.BLUE) {
                        //System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
                        System.out.print(ANSI_BLUE + board[x][y].value + " " + ANSI_RESET);

                    } else if (board[x][y].tileColor == TileColor.GREY) {
                        System.out.print(ANSI_BLACK + board[x][y].value + " " + ANSI_RESET);
                    } else if (board[x][y].tileColor == TileColor.RED) {
                        System.out.print(ANSI_RED + board[x][y].value + " " + ANSI_RESET);
                    } else {
                        System.out.print("0 ");
                    }
                }
                System.out.println(" ");
            }

            System.out.println(" ");
        }
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

    public boolean isEmpty(int x, int y) {
        if (board[x-1][y-1].tileColor == TileColor.EMPTY) {
            return true;
        }

        return false;
    }
}

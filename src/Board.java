public class Board {
    String board[][];
    int boardWidth = 4;
    int boardHeight = 4;

    public enum TileColor {
        BLUE, RED, GREY
    }

    public Board() {
        board = new String[boardWidth][boardHeight] ;
        initBoard();

    }

    public void initBoard() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = "0";
            }

        }
    }

    // An idea to multi thread the shifting?
    // Have a function which only slides one row
    // Create a thread for each row and have each thread slide only one row


    public void slideLeft() {
        // Copy the board, Perform any operations and replace the board afterwards
        String[][] boardCopy = board.clone();

        // We are only shifting the top row currently
        int y = 0;
        // This builds the current row, Mainly for debugging purposes
        String currentRow = boardCopy[0][y] + boardCopy[1][y] +boardCopy[2][y] +boardCopy[3][y];


        // for each tile from left to right
        int i = 0;
        while (i < boardWidth) {
            // If the tile is 0, we need to shift tiles into it
            if (boardCopy[i][y].equals("0")) {
                int j = i;

                // j will loop through all tiles to the right of i and shift them
                // while we still have tiles to shift
                while (j < boardWidth) {
                    // We reached the last tile, Place with a 0, instead of trying to get off the board
                    if (j+1 == boardWidth) {
                        boardCopy[j][y] = "0";
                        currentRow = boardCopy[0][y] + boardCopy[1][y] +boardCopy[2][y] +boardCopy[3][y];
                    }
                    else {
                        // Replace the current cell with the tile to the right
                        boardCopy[j][y] = boardCopy[j+1][y];
                        currentRow = boardCopy[0][y] + boardCopy[1][y] +boardCopy[2][y] +boardCopy[3][y];
                    }
                    j++;
                }

            }

            // Without this check it wont shift far enough
            // With it, It gets stuck here
            //TODO: I need to a check that ensures all shifting is done and I can get out of this function
            if (boardCopy[i][y].equals("0") == false) {
                i++;
            }
        }

        board = boardCopy;
    }


    public void placeTile(TileColor tileColor, int xPos, int yPos) {
        if (xPos < boardWidth - 1 || xPos >= 0) {
            if (yPos < boardHeight - 1 || yPos >= 0) {
                board[xPos][yPos] = "1";
            }
            else {
                System.out.println("Error placing tile: 1");
            }
        }
        else {
            System.out.println("Error placing tile: 2");

        }
    }

    public void printBoard() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                System.out.print(board[x][y]);
            }
            System.out.println(" ");
        }

        System.out.println(" ");
    }
}

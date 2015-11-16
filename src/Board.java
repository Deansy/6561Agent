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


        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (boardCopy[x][y].equals("0")) {

                    for (int k=x+1; k<4; k++) {
                        if (boardCopy[k][y].equals("0") == false) {
                            boardCopy[x][y] = boardCopy[k][y];
                            boardCopy[k][y] = "0";
                            break;
                        }
                    }
                }
            }
        }

        board = boardCopy;

    }
    public void slideRight() {

        // Copy the board, Perform any operations and replace the board afterwards
        String[][] boardCopy = board.clone();


        for (int y = 0; y < 4; y++) {
            for (int x = 3; x > 0; x--) {
                if (boardCopy[x][y].equals("0")) {

                    for (int k=x-1; k>=0; k--) {
                        if (boardCopy[k][y].equals("0") == false) {
                            boardCopy[x][y] = boardCopy[k][y];
                            boardCopy[k][y] = "0";
                            break;
                        }
                    }
                }
            }
        }

        board = boardCopy;

    }

    public void slideUp() {

        // Copy the board, Perform any operations and replace the board afterwards
        String[][] boardCopy = board.clone();


        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (boardCopy[x][y].equals("0")) {

                    for (int k=y+1; k<4; k++) {
                        if (boardCopy[x][k].equals("0") == false) {
                            boardCopy[x][y] = boardCopy[x][k];
                            boardCopy[x][k] = "0";
                            break;
                        }
                    }
                }
            }

        }

        board = boardCopy;

    }

    public void slideDown() {

        // Copy the board, Perform any operations and replace the board afterwards
        String[][] boardCopy = board.clone();


        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {
                if (boardCopy[x][y].equals("0")) {

                    for (int k=y-1; k>=0; k--) {
                        if (boardCopy[x][k].equals("0") == false) {
                            boardCopy[x][y] = boardCopy[x][k];
                            boardCopy[x][k] = "0";
                            break;
                        }
                    }
                }
            }

        }

        board = boardCopy;

    }


    public void placeTile(TileColor tileColor, int xPos, int yPos, String value) {
        if (xPos < boardWidth - 1 || xPos >= 0) {
            if (yPos < boardHeight - 1 || yPos >= 0) {
                board[xPos][yPos] = value;
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

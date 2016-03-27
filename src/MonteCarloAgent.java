import java.util.LinkedList;
import java.util.List;

/**
 * Created by cameron on 07/03/2016.
 */
public class MonteCarloAgent implements Player {

    int currentMove;
    Board currentBoard;
    String previousMove;

    MCTSNode currentNode;

    public MonteCarloAgent(Board board) {
        currentMove = 1;
        currentBoard = board;

        currentNode = new MCTSNode();


        int n = 200;
        for (int i=0; i<n; i++) {
            currentNode.selectAction();
        }
    }

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

        if (currentMove % 50 == 0) {
            System.err.println("200 Expand");
            for (int i = 0; i < 200; i++) {
                currentNode.selectAction();
            }
        }

        if (currentNode.children.size() == 0) {
           currentNode.expand();
        }

        double highestTotalValue = 0;
        try {
            highestTotalValue = currentNode.children.get(0).totValue;

        }
        catch (Exception e) {
            System.err.println("No Possible Move");
            currentBoard.printBoard(true);
            e.printStackTrace();
        }
        MCTSNode bestChildNode = currentNode.children.get(0);
        for (MCTSNode n : currentNode.children) {
            if (n.totValue > highestTotalValue) {
                bestChildNode = n;
            }
        }

        Board.NewStateData nsd = bestChildNode.stateData;

        currentNode = bestChildNode;

        currentBoard.placeTile(nsd.color, nsd.x, nsd.y, 1);
        System.out.println("" + nsd.x + "" + nsd.y);
        System.err.println("" + nsd.x + "" + nsd.y);
        currentMove++;

        System.out.flush();



    }

    @Override
    public void performMoveTurn() {

        if (currentMove % 50 == 0) {
            System.err.println("200 Expand");
            for (int i = 0; i < 200; i++) {
                currentNode.selectAction();
            }
        }

        if (currentNode.children.size() == 0) {
            currentNode.expand();
        }

        double highestTotalValue = currentNode.children.get(0).totValue;
        MCTSNode bestChildNode = currentNode.children.get(0);
        for (MCTSNode n : currentNode.children) {
            if (n.totValue > highestTotalValue) {
                bestChildNode = n;
            }
        }

        Board.NewStateData nsd = bestChildNode.stateData;

        currentBoard.slideBoard(nsd.move);

        currentNode = bestChildNode;

        System.err.println(nsd.move);

        switch (nsd.move) {
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

}

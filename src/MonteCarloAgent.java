import java.util.LinkedList;
import java.util.List;

/**
 * Created by cameron on 07/03/2016.
 */
public class MonteCarloAgent implements Player {

    int currentMove;
    Board currentBoard;
    String previousMove;

    public MonteCarloAgent(Board board) {
        currentMove = 1;
        currentBoard = board;
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

    }

    @Override
    public void performMoveTurn() {

    }


    private class MonteCarloTreeSearch {


    }


    private class MCTSNode {
        Board board;
        int currentMove;



        double nVisits, totValue;
        double epsilon = 1e-6;
        List<MCTSNode> children = new LinkedList<>();



        public void selectAction(MCTSNode root) {
            List<MCTSNode> visited = new LinkedList<>();

            MCTSNode cur = this;

            visited.add(this);

            while(!this.board.hasGameEnded()) {
                cur = select();
                visited.add(cur);
            }

            cur.expand();
            MCTSNode newNode = cur.select();
            visited.add(newNode);

            double value = rollOut(newNode);

            for (MCTSNode node : visited) {
                node.updateStats(value);
            }


        }

        public void expand() {

            List<Board> board = this.board.getNextStates(currentMove);

            for (Board b : board) {
                MCTSNode newNode = new MCTSNode();
                newNode.board = b;
                newNode.currentMove = currentMove + 1;
            }

        }

        public MCTSNode select() {
            MCTSNode selected = null;

            double bestValue = Double.MIN_VALUE;

            for (MCTSNode n : children) {
                double uctValue = n.totValue / (n.nVisits + n.epsilon) +
                        Math.sqrt(Math.log(nVisits + 1) / (n.nVisits + epsilon));

                if (uctValue > bestValue) {
                    selected = n;
                    bestValue = uctValue;
                }
            }

            return selected;

        }

        public double rollOut (MCTSNode n) {
            return n.board.gameScore();
        }

        public void updateStats(double value) {
            nVisits++;
            totValue += value;
        }
    }
}

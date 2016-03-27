import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by cameron on 07/03/2016.
 */
public class MCTSNode {
    Board board;
    int currentMove;

    Board.NewStateData stateData;





    static Random r = new Random();
    double nVisits, totValue;
    double epsilon = 1e-6;



    List<MCTSNode> children = new LinkedList<>();



    MCTSNode() {
        board = new Board();
        currentMove = 0;
    }


    public void selectAction() {
        List<MCTSNode> visited = new LinkedList<>();

        MCTSNode cur = this;

        visited.add(this);

        while(!cur.children.isEmpty()) {
            cur = cur.select();
            visited.add(cur);
        }

        cur.expand();
        double value = 0;
        MCTSNode newNode = cur.select();
        if (newNode == null) {
            System.err.println("CurrentMove: " + currentMove);
            cur.board.printBoard(false);
            newNode = cur.select();
        }
        visited.add(newNode);

        value = rollOut(newNode);


        for (MCTSNode node : visited) {
            node.updateStats(value);
        }


    }

    public void expand() {

        List<Pair<Board, Board.NewStateData>> boards = this.board.getNextStatesWithStateData(currentMove);

        if (boards.size() > 5) {
            for (int i = 0; i < 5; i++) {
                MCTSNode newNode = new MCTSNode();
                newNode.board = boards.get(i).first;
                newNode.stateData = boards.get(i).second;
                newNode.currentMove = currentMove + 1;


                children.add(newNode);
            }
        }
        else {
            for (Pair x : boards) {
                Board b = (Board)x.first;
                MCTSNode newNode = new MCTSNode();
                newNode.board = b;
                newNode.stateData = (Board.NewStateData)x.second;
                newNode.currentMove = currentMove + 1;

                children.add(newNode);
            }
        }

        //System.out.println("Exapanded " + children.size() + " from Turn " + currentMove);




    }

    public MCTSNode select() {
        MCTSNode selected = null;

        double bestValue = Double.MIN_VALUE;

        for (MCTSNode n : children) {

            //System.out.println("Move: " + n.currentMove + " - " + n.children.size() );

            double uctValue =
                    n.totValue / (n.nVisits + n.epsilon) +
                    Math.sqrt(Math.log(nVisits + 1) / (n.nVisits + epsilon)) +
                    r.nextDouble() * epsilon;

            if (uctValue > bestValue) {
                selected = n;
                bestValue = uctValue;
            }
        }

        if (children.isEmpty()) {
            System.err.println("No Children, Returning null");
        }

        return selected;

    }

    public double rollOut (MCTSNode n) {
        RandomMoveAgent randAgent = new RandomMoveAgent(board, currentMove);
        for (int i = 0; i < 20; i++) {
            if (Board.placeTurns.contains(currentMove + i)) {
                randAgent.performPlaceTurn();
            }
            else {
                randAgent.performMoveTurn();
            }
        }

        try {
            int score = randAgent.currentBoard.gameScore();
            //randAgent.currentBoard.printBoard(false);
            //System.out.println("Score: " + score);
            //return n.board.gameScore();
            return score;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public void updateStats(double value) {
        nVisits++;
        totValue += value;
    }
}
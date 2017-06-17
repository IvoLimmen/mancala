package nl.sogyo.mancala.computer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nl.sogyo.mancala.Computable;
import nl.sogyo.mancala.Player;
import nl.sogyo.mancala.Store;
import nl.sogyo.mancala.exception.InvalidMoveException;

public class ComputerPlayer extends Player implements Computable {

    public ComputerPlayer() {

        super("Computer");
    }

    /* (non-Javadoc)
     * @see nl.sogyo.mancala.computer.Computable#calculateMove()
     */
    public void calculateMove() throws InvalidMoveException {

        System.out.println("*** calculcate move");

        System.out.println("*** strategic move");
        int move = strategicMove();

        if (move == 0) {

            System.out.println("*** devensive move");
            move = devensiveMove();
        }

        if (move == 0) {

            System.out.println("*** random move");
            move = randomMove();
        }

        System.out.println("*** move = " + move);

        takeFromStore(move);
    }

    private int devensiveMove() {

        Map<Integer, Integer> possibleMoves = new HashMap<Integer, Integer>();

        //find empty stores of opponent 
        Integer[] stores = nextPlayer().getStores();

        for (int index = 0; index < stores.length; index++) {

            if (stores[index] == 0
                && getStore(index + 1).getBeads() > 0) {

                System.out.println("possible move " + (index + 1) + " value " + getStore(index + 1).getBeads());
                possibleMoves.put(getStore(index + 1).getBeads(), (index + 1));
            }
        }

        if (possibleMoves.size() == 0) {

            return 0;
        }

        int move = 0;
        Iterator<Map.Entry<Integer, Integer>> moveIterator
            = possibleMoves.entrySet().iterator();

        while (moveIterator.hasNext()) {

            move = moveIterator.next().getValue();
        }

        return move;
    }

    private int strategicMove() {

        boolean moveFound = false;
        int move = getTotalStores();

        while (!moveFound && move > 0) {

            Store store = getStore(move);

            if (store.getBeads() == ((1 + getTotalStores()) - move)) {

                moveFound = true;
            } else {

                // take the store before it
                move--;
            }
        }

        return move;
    }

    private int randomMove() {

        int move = 1 + (int) (Math.random() * getTotalStores());

        while (getStore(move).getBeads() == 0) {

            move = 1 + (int) (Math.random() * getTotalStores());
        }

        return move;
    }
}

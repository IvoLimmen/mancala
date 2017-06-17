package nl.sogyo.mancala;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Player> players = new ArrayList<Player>();

    private Board() {

        super();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static Board create(int holes, int beads, Player... players) {

        if (players == null || players.length < 2) {

            throw new IllegalArgumentException("You must play with at least two players.");
        }

        Board board = new Board();

        Player firstPlayer = null;
        Player previousPlayer = null;
        Store firstLine = null;
        Store currentLine = null;

        for (Player player : players) {

            if (previousPlayer != null) {

                previousPlayer.setNextPlayer(player);
                previousPlayer = player;

            } else {

                previousPlayer = player;
            }

            player.setWinner(false);
            board.players.add(player);

            if (currentLine == null) {

                currentLine = createLine(player, holes, beads);
                firstLine = currentLine;

            } else {

                Store newLine = createLine(player, holes, beads);
                currentLine.getHome().setNext(newLine);
                currentLine = newLine;
            }

            if (firstPlayer == null) {

                firstPlayer = player;
            }
        }

        previousPlayer.setNextPlayer(firstPlayer);

        // finish the circle
        currentLine.getHome().setNext(firstLine);

        // activate the first player
        firstPlayer.setActive(true);

        return board;
    }

    private static Store createLine(Player player, int holes, int beads) {

        Store first = null;
        Store currentStore = null;

        for (int index = 0; index < holes; index++) {

            if (currentStore == null) {

                currentStore = new Store(player, beads);
                player.setFirstStore(currentStore);
                first = currentStore;

            } else {

                currentStore.setNext(new Store(player, beads));
                currentStore = currentStore.nextStore();
            }
        }

        // home has no beads to start with
        currentStore.setNext(new Home(player, 0));

        return first;
    }
}

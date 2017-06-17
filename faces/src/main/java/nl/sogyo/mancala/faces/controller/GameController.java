package nl.sogyo.mancala.faces.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import nl.sogyo.mancala.Board;
import nl.sogyo.mancala.Computable;
import nl.sogyo.mancala.Player;
import nl.sogyo.mancala.exception.InvalidMoveException;

public class GameController {

    private int beads;

    private Board board = null;

    private int holes;

    private String newPlayer = "";

    private List<Player> players = null;

    public void addPlayer() {

        players.add(new Player(getNewPlayer()));
        setNewPlayer("");
    }

    protected FacesContext context() {

        return FacesContext.getCurrentInstance();
    }

    public List<Player> getActivePlayers() {

        return getBoard().getPlayers();
    }

    public int getBeads() {

        return beads;
    }

    public Board getBoard() {

        return board;
    }

    public int getHoles() {

        return holes;
    }

    public String getNewPlayer() {

        return newPlayer;
    }

    public List<Player> getPlayers() {

        return players;
    }

    public Integer[] getStores() {

        Map<String, Object> requestMap = context().getExternalContext()
            .getRequestMap();

        Player player = (Player) requestMap.get("player");

        return player.getStores();
    }

    public boolean isActive() {

        return board != null;
    }

    public boolean isComputable() {

        Map<String, Object> requestMap = context().getExternalContext()
            .getRequestMap();

        Player player = (Player) requestMap.get("player");

        return (player instanceof Computable);
    }

    public String newGame() {

        // remove the board
        board = null;
        players = new ArrayList<Player>();

        return "setup";
    }

    public void removePlayer() {

        Map<String, Object> requestMap = context().getExternalContext()
            .getRequestMap();

        players.remove((Player) requestMap.get("player"));
    }

    public void setBeads(int beads) {

        this.beads = beads;
    }

    public void setHoles(int holes) {

        this.holes = holes;
    }

    public void setNewPlayer(String newPlayer) {

        this.newPlayer = newPlayer;
    }

    public String startGame() {

        Player[] players = getPlayers().toArray(new Player[0]);
        this.players.clear();

        board = Board.create(getHoles(), getBeads(), players);

        setHoles(0);
        setBeads(0);

        return "start";
    }

    public void takeFromStore() {

        Map<String, Object> requestMap = context().getExternalContext()
            .getRequestMap();

        Player player = (Player) requestMap.get("player");
        Integer store = (Integer) requestMap.get("store");

        try {

            player.takeFromStore(store);
        }
        catch (InvalidMoveException e) {

            e.printStackTrace();
        }
    }
}

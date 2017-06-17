package nl.sogyo.mancala;

import java.util.ArrayList;
import java.util.List;

import nl.sogyo.mancala.exception.InvalidMoveException;

public class Player {

    private boolean active;

    private Store firstStore;

    private String name;

    private Player nextPlayer;

    private int totalStores = 0;

    private boolean winner;

    public Player(String name) {

        super();
        this.name = name;
    }

    /**
     * Deactive this player and activate the next player.
     */
    public void deactivate() {

        nextPlayer().setActive(true);
        setActive(false);

        if (nextPlayer() instanceof Computable && nextPlayer().isActive()) {

            Computable computable = (Computable) nextPlayer();

            try {

                while (nextPlayer().isActive()
                    && !winnerExists()) {

                    System.out.println("*** computer has turn");

                    computable.calculateMove();
                }

            }
            catch (InvalidMoveException e) {

                // should not be possible
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Player) {

            Player other = (Player) obj;

            if (this.getName().equals(other.getName())) {

                return true;
            }
        }

        return false;
    }

    private void findWinner() {

        int score = getScore();
        Player winner = this;
        Player current = this;

        while (!current.nextPlayer().equals(this)) {

            current = current.nextPlayer();

            if (current.getScore() > score) {

                score = current.getScore();
                winner = current;
            }
        }

        winner.setWinner(true);
    }

    public Store getFirstStore() {
        return firstStore;
    }

    public Home getHome() {

        return firstStore.getHome();
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {

        return getHome().getBeads();
    }

    protected Store getStore(int storeIndex) {

        storeIndex--;
        Store store = firstStore;

        while (storeIndex > 0) {

            storeIndex--;
            store = store.nextStore();

            if (store.isHome()) {

                throw new IllegalArgumentException();
            }
        }

        return store;
    }

    public Integer[] getStores() {

        List<Integer> stores = new ArrayList<Integer>();

        Store store = firstStore;

        while (!store.isHome()) {

            stores.add(store.getBeads());
            store = store.nextStore();
        }

        return stores.toArray(new Integer[stores.size()]);
    }

    private int getTotalBeads() {

        int totalBeads = 0;

        Store store = firstStore;

        while (!store.isHome()) {

            totalBeads += store.getBeads();
            store = store.nextStore();
        }

        return totalBeads;
    }

    public int getTotalStores() {

        if (totalStores == 0) {

            Store store = firstStore;

            while (!store.isHome()) {

                totalStores++;
                store = store.nextStore();
            }

        }

        return totalStores;
    }

    @Override
    public int hashCode() {

        return "name".hashCode() * 3 + getName().hashCode() * 7;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isWinner() {
        return winner;
    }

    public Player nextPlayer() {
        return nextPlayer;
    }

    public void setActive(boolean active) {

        this.active = active;
    }

    public void setFirstStore(Store firstStore) {
        this.firstStore = firstStore;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    /**
     * Takes the beads from a store of this player and puts them in the next stores until all beads are depleted.
     *
     * @param storeIndex a store index ranged from 1 to the amount of stores
     * @throws InvalidMoveException when choosing an empty slot or when this player may not play
     */
    public void takeFromStore(int storeIndex) throws InvalidMoveException {

        if (!isActive()) {

            throw new InvalidMoveException("It's not " + getName()
                + " his turn.");
        }

        Store store = getStore(storeIndex);

        int beads = store.getBeads();

        if (beads == 0) {

            throw new InvalidMoveException("Store has no beads to play with");
        }

        int position = (beads + storeIndex) % (getTotalStores() + 1);

        Store rivalStore = nextPlayer().getStore(position);

        store = store.nextStore().placeBeads(this, rivalStore, store.empty());

        // it is a home we finish on
        if (store.isHome()) {

            if (store.getPlayer().equals(this)) {

                // we can play one more time
                // simply return from this method
                return;
            }
        }

        if (getTotalBeads() == 0) {

            findWinner();
        }

        // finished the turn
        deactivate();
    }

    protected boolean winnerExists() {

        if (isWinner()) {

            return true;

        } else {

            Player current = this;

            while (!current.nextPlayer().equals(this)) {

                current = current.nextPlayer();

                if (current.isWinner()) {

                    return true;
                }
            }
        }

        return false;
    }
}

package nl.sogyo.mancala;

/**
 * This is a playing hole on the board.
 *
 * @author Ivo Limmen
 */
public class Store {

    private int beads = 0;

    private Store next;

    private Player player;

    public Store(Player player, int beads) {

        this.player = player;
        this.beads = beads;
    }

    public void addBead() {

        this.beads++;
    }

    public int empty() {

        int value = beads;
        beads = 0;

        return value;
    }

    public int getBeads() {
        return beads;
    }

    public Home getHome() {

        if (isHome()) {

            return (Home) this;
        } else {

            return nextStore().getHome();
        }
    }

    public Player getPlayer() {

        return player;
    }

    public boolean isHome() {

        return false;
    }

    public void moveBeadsFrom(Store store) {

        this.beads += store.empty();
    }

    public Store nextStore() {

        return next;
    }

    public Store placeBeads(Player player, Store rivalStore, int placementBeads) {

        // it this store empty? it is the last bead? (is it not a home?)
        if (this.beads == 0
            && placementBeads == 1
            && !isHome()) {

            getHome().addBead();
            getHome().moveBeadsFrom(rivalStore);

            // finished
            return this;
        }

        this.beads++;

        if (placementBeads > 1) {

            return nextStore().placeBeads(player, rivalStore, --placementBeads);
        }

        return this;
    }

    public void setNext(Store next) {

        this.next = next;
    }
}

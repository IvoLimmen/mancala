package nl.sogyo.mancala;

/**
 * The Home of a player, where he collects all the beads.
 *
 * @author Ivo Limmen
 */
public class Home extends Store {

    public Home(Player player, int beads) {

        super(player, beads);
    }

    @Override
    public boolean isHome() {

        return true;
    }

    @Override
    public Store placeBeads(Player player, Store rivalStore, int beads) {

        // your own home?
        if (getPlayer().equals(player)) {

            // plce beads as normal
            return super.placeBeads(player, rivalStore, beads);
        } else {

            // skip this store and go to the next one
            return nextStore().placeBeads(player, rivalStore, beads);
        }
    }
}

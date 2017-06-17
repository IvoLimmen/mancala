package nl.sogyo.mancala;

import nl.sogyo.mancala.exception.InvalidMoveException;

public interface Computable {

    public void calculateMove() throws InvalidMoveException;
}

package nl.supertom01.triominos.exceptions;

/**
 * Thrown when a stone cannot be placed on the board.
 */
public class PlacementException extends Exception {

    public PlacementException(String message) {
        super(message);
    }

}

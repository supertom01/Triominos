package nl.supertom01.triominos.controller;

import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.model.Move;
import nl.supertom01.triominos.model.Player;

/**
 * A more sophisticated AI that tries each stone and each move and makes the move that yields the highest score.
 */
public class HardPlayer extends Player {

    public HardPlayer() {
        super("Hard Player");
    }

    @Override
    public Move determineMove(Board board) {
        return null;
    }

}

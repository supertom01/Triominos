package nl.supertom01.triominos.controller;

import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.model.Move;
import nl.supertom01.triominos.model.Player;

/**
 * A simple AI that picks the first stone that it can place on the board.
 * If it can't find a stone it will take a stone from the stack.
 */
public class EasyPlayer extends Player {

    public EasyPlayer() {
        super("Easy Player");
    }

    @Override
    public Move determineMove(Board board) {
        return null;
    }
}

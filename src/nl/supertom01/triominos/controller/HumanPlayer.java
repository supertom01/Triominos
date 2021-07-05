package nl.supertom01.triominos.controller;

import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.model.Move;
import nl.supertom01.triominos.model.Player;

/**
 * This player is an actual human being. The human is requested to perform a move, which can either be placing a stone
 * on the board or picking a stone from the stack.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String username) {
        super(username);
    }

    @Override
    public Move determineMove(Board board) {
        return null;
    }

}

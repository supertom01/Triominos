package nl.supertom01.triominos.controller;

import java.util.List;
import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.model.Move;
import nl.supertom01.triominos.model.Orientation;
import nl.supertom01.triominos.model.Player;
import nl.supertom01.triominos.model.Stone;
import nl.supertom01.triominos.util.Tuple;

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
        List<Tuple<Tuple<Integer, Integer>, Orientation>> availableSpots = board.getOpenFields();
        List<Stone> stones = getStones();

        // Check each stone in our collection.
        for (Stone stone : stones) {
            // Check each possible field.
            for (Tuple<Tuple<Integer, Integer>, Orientation> field : availableSpots) {
                int x = field.getLeft().getLeft();
                int y = field.getLeft().getRight();
                // Check each possible rotation.
                for (int i = 0; i < 6; i++) {
                    Move move = new Move(stone, x, y);
                    if(board.isValidMove(move)) {
                        return move;
                    }
                }
            }
        }

        // No possible move found... So draw from the stack.
        Stone stone = board.getFromStack();
        if(stone != null) {
            addStone(stone);
        }
        return null;
    }
}

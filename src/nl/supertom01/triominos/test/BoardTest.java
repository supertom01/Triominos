package nl.supertom01.triominos.test;

import nl.supertom01.triominos.controller.Board;
import nl.supertom01.triominos.exceptions.PlacementException;
import nl.supertom01.triominos.model.Stone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        this.board = new Board();
    }

    @Test
    public void testIsEmpty() throws PlacementException {
        Assertions.assertTrue(this.board.isEmpty());
        this.board.placeStone(new Stone(1,1,1), 2, 3);
        Assertions.assertFalse(this.board.isEmpty());
    }

    @Test
    public void testIsValidMove() throws PlacementException {
        Stone s1 = new Stone(1, 2, 3);
        Stone s2 = new Stone(1, 2, 1);
        s2.rotateRight();
        Stone s3 = new Stone(1, 1, 1);
        Stone s4 = new Stone(3, 4, 3);
        Assertions.assertTrue(board.isValidMove(s1, 56, 56));
        board.placeStone(s1, 56, 56);
        Assertions.assertTrue(board.isValidMove(s2, 57, 56));
        board.placeStone(s2, 57, 56);
        Assertions.assertTrue(board.isValidMove(s3, 57, 55));
        board.placeStone(s3, 57, 55);
        Assertions.assertFalse(board.isValidMove(s4, 58, 56));
    }

}

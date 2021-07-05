package nl.supertom01.triominos.test;

import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.exceptions.PlacementException;
import nl.supertom01.triominos.model.Move;
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
        this.board.placeStone(new Move(new Stone(1,1,1), 2, 3));
        Assertions.assertFalse(this.board.isEmpty());
    }

    @Test
    public void testIsValidMove() throws PlacementException {
        Stone s1 = new Stone(1, 2, 3);
        Stone s2 = new Stone(1, 2, 1);
        s2.rotateRight();
        Stone s3 = new Stone(1, 1, 1);
        Stone s4 = new Stone(3, 4, 3);
        Move m1 = new Move(s1, 56, 56);
        Move m2 = new Move(s2, 57, 56);
        Move m3 = new Move(s3, 57, 55);
        Move m4 = new Move(s4, 58, 56);
        Assertions.assertTrue(board.isValidMove(m1));
        board.placeStone(m1);
        Assertions.assertTrue(board.isValidMove(m2));
        board.placeStone(m2);
        Assertions.assertTrue(board.isValidMove(m3));
        board.placeStone(m3);
        Assertions.assertFalse(board.isValidMove(m4));
    }

}

package nl.supertom01.triominos.test;

import nl.supertom01.triominos.controller.HumanPlayer;
import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.exceptions.PlacementException;
import nl.supertom01.triominos.model.Move;
import nl.supertom01.triominos.model.Player;
import nl.supertom01.triominos.model.Stone;
import org.junit.Assert;
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

    @Test
    public void testPointCalculation() throws PlacementException {
        Stone[] stones = new Stone[6];
        for (int i = 0; i < 6; i++) {
            Stone stone = new Stone(i, i, i);
            if(i % 2 == 0) {
                stone.rotateRight();
            }
            stones[i] = stone;
        }

        Player player = new HumanPlayer("Test");

        Move m0 = new Move(stones[0], 56, 56);
        Move m1 = new Move(stones[1], 57, 56);
        Move m2 = new Move(stones[2], 58, 56);
        Move m3 = new Move(stones[3], 58, 55);
        Move m4 = new Move(stones[4], 57, 55);
        Move m5 = new Move(stones[5], 56, 55);

        Assertions.assertEquals(40, board.determinePoints(m0, player));
        Assertions.assertEquals(10 + 3, board.determinePoints(m1, player));
        board.placeStone(m1);
        Assertions.assertEquals(6, board.determinePoints(m2, player));
        Assertions.assertEquals(0, board.determinePoints(m0, player));
        board.placeStone(m0);
        board.placeStone(m2);
        Assertions.assertEquals(-5, board.determinePoints(null, player));
        Assertions.assertEquals(9, board.determinePoints(m3, player));
        board.placeStone(m3);
        Assertions.assertEquals(12, board.determinePoints(m4, player));
        Assertions.assertEquals(15, board.determinePoints(m5, player));
        board.placeStone(m4);
        Assertions.assertEquals(15 + 50, board.determinePoints(m5, player));
        board.placeStone(m5);
        board.removeStone(m3);
        Assertions.assertEquals(9 + 50, board.determinePoints(m3, player));
        board.placeStone(m3);
        board.removeStone(m0);
        Assertions.assertEquals(50, board.determinePoints(m0, player));
        board.placeStone(m0);
    }

}

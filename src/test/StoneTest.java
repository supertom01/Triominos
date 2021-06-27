package test;

import model.Orientation;
import model.Stone;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StoneTest {

    private Stone stone;

    private static void assertStoneEquals(Stone expected, Stone actual) {
        assertEquals(String.format("Expected values: %s; Actual values: %s%nExpected orientation: %s; Actual orientation: %s",
                Arrays.toString(expected.getValues()), Arrays.toString(actual.getValues()), expected.getOrientation(),
                actual.getOrientation()), expected, actual);
    }

    @Before
    public void setUp() {
        this.stone = new Stone(1, 2, 3);
    }

    @Test
    public void rotateRightTest() {
        // The identity operation of a rotation is rotating 6 times.
        Stone expected = new Stone(1, 2, 3);
        for (int i = 0; i < 6; i++) {
            stone.rotateRight();
        }
        assertStoneEquals(expected, stone);

        /*
          /\         ------
         /1 \   -->  \3  1/
        /3  2\        \2 /
        ------         \/
         */
        expected = new Stone(3, 1, 2);
        expected.setOrientation(Orientation.TOP);
        stone.rotateRight();
        assertStoneEquals(expected, stone);
    }

    @Test
    public void rotateLeftTest() {
        // The identity operation of a rotation is rotating 6 times.
        Stone expected = new Stone(1, 2, 3);
        for (int i = 0; i < 6; i++) {
            stone.rotateLeft();
        }
        assertStoneEquals(expected, stone);

        /*
          /\         ------
         /1 \   -->  \1  2/
        /3  2\        \3 /
        ------         \/
         */
        expected = new Stone(1, 2, 3);
        expected.setOrientation(Orientation.TOP);
        stone.rotateLeft();
        assertStoneEquals(expected, stone);
    }

}

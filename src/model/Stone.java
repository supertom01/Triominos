package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The stone model class.
 *
 * @author Tom Meulenkamp
 * @version 27-06-2021
 */
public class Stone {

    /**
     * The current orientation of this stone.
     *
     * ────────
     * \     /             /\
     *  \   /      OR    /   \
     *   \/             /     \
     *                  ───────
     *  TOP              BOTTOM
     */
    private Orientation orientation;

    /** The values that are on a stone. */
    private final int[] values;

    /**
     * The neighbours of this stone on the board.
     *         1
     *     ────────
     *     \ 0 1 /         0   /\   1
     *   0  \ 2 / 2    OR    / 0 \
     *       \/             / 2 1 \
     *                      ───────
     *                         2
     */
    private Stone[] neighbours;

    public Stone(int nr1, int nr2, int nr3) {
        this.orientation = Orientation.DOWN;
        this.values = new int[]{nr1, nr2, nr3};
        this.neighbours = new Stone[3];
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int[] getValues() {
        return values;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Stone[] getNeighbours() {
        return neighbours;
    }

    /**
     * Rotates this stone clockwise, by one position.
     */
    public void rotateRight() {
        if (this.orientation == Orientation.DOWN) {
            int temp = this.values[2];
            this.values[2] = this.values[1];
            this.values[1] = this.values[0];
            this.values[0] = temp;
            this.orientation = Orientation.TOP;
        } else {
            this.orientation = Orientation.DOWN;
        }
    }

    /**
     * Rotates this stone counter-clockwise, by one position.
     */
    public void rotateLeft() {
        if (this.orientation == Orientation.TOP) {
            int temp = this.values[0];
            this.values[0] = this.values[1];
            this.values[1] = this.values[2];
            this.values[2] = temp;
            this.orientation = Orientation.DOWN;
        } else {
            this.orientation = Orientation.TOP;
        }
    }

    /**
     * Check if this stone is compatible with the provided set of neighbours.
     * @param neighbours The set of neighbours to check for placing this stone.
     *                   The neighbours should be placed in the order specified in this.neighbours.
     *                   neighbours.length == 3
     * @return true if the stone can be placed, taking into account the neighbours.
     */
    public boolean match(Stone[] neighbours) {
        boolean compatibleOrientation = true;
        int nonNullNeighbours = 0;
        for (Stone neighbour : neighbours) {
            if (neighbour != null) {
                compatibleOrientation = compatibleOrientation && this.orientation != neighbour.getOrientation();
                nonNullNeighbours++;
            }
        }
        if (nonNullNeighbours == 0 || !compatibleOrientation) {
            return false;
        }

        boolean compatibleValues = true;
        for (int i = 0; i < neighbours.length; i++) {
            int[] nVals = neighbours[i].getValues();
            if (i == 0) {
                compatibleValues = nVals[0] == values[0] && nVals[2] == values[2];
            } else if (i == 1) {
                compatibleValues = compatibleValues && nVals[0] == values[0] && nVals[1] == values[1];
            } else {
                compatibleValues = compatibleValues && nVals[1] == values[1] && nVals[2] == values[2];
            }
        }
        return compatibleValues;
    }

    /**
     * Connects this stone to its neighbours when placed on the board.
     * @requires That the neighbours are compatible with this stone.
     *           The current neighbours should all be null (this stone shouldn't be placed yet...)
     * @param    neighbours The neighbouring stones.
     */
    public void place(Stone[] neighbours) {
        if (neighbours[0] != null) {
            neighbours[0].getNeighbours()[1] = this;
        }
        if (neighbours[1] != null) {
            neighbours[1].getNeighbours()[2] = this;
        }
        if (neighbours[2] != null) {
            neighbours[2].getNeighbours()[0] = this;
        }

        this.neighbours = neighbours;
    }

    /**
     * Two stones are equal if their orientation and values are equal. (Used for testing purposes)
     * @param  o The object that is being compared to this object.
     * @return True if the objects are deemed equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stone stone = (Stone) o;
        return orientation == stone.orientation && Arrays.equals(values, stone.values);
    }

    /**
     * Generates a string representation of the Stone object.
     * @return
     * ────────
     * \ 0 1 /             /\
     *  \ 2 /      OR    / 0 \
     *   \/             / 2 1 \
     *                  ───────
     * Where 0,1,2 represent the indices of the values array.
     */
    @Override
    public String toString() {
        if (this.orientation == Orientation.TOP) {
            return String.format("%d %d%n %d%n", this.values[0], this.values[1], this.values[2]);
        } else {
            return String.format(" %d%n%d %d%n", this.values[0], this.values[2], this.values[1]);
        }
    }

    /**
     * Generates all the stones that are used in the game and makes sure that they're shuffled.
     * @return A list with stones.
     */
    public static List<Stone> shuffle() {
        ArrayList<Stone> stones = new ArrayList<>(216);
        for (int i = 0; i < 6; i++) {
            for (int j = i; j < 6; j++) {
                for (int k = j; k < 6; k++) {
                    stones.add(new Stone(i,j,k));
                }
            }
        }
        Collections.shuffle(stones);
        return stones;
    }

}

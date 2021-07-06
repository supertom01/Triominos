package nl.supertom01.triominos.model;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import nl.supertom01.triominos.exceptions.PlacementException;
import nl.supertom01.triominos.styles.Style;
import nl.supertom01.triominos.util.Tuple;
import nl.supertom01.triominos.view.EmptyLocation;

/**
 * The board class.
 *
 * @author Tom Meulenkamp
 * @version 01-07-2021
 */
public class Board extends StackPane {

    public static final int NR_STONES = 56;

    private final List<Stone> stack;
    private final Stone[][] board;

    public Board() {
        this.stack = Stone.shuffle();
        this.board = new Stone[NR_STONES * 2][NR_STONES * 2];
    }

    /**
     * Checks if the current board is empty.
     * @return True if the board is empty, otherwise false.
     */
    public boolean isEmpty() {
        for(Stone[] row : board) {
            for(Stone stone : row) {
                if(stone != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get a new stone from the stack.
     * @return The stone
     */
    public Stone getFromStack() {
        if(!stack.isEmpty()) {
            return stack.remove(0);
        }
        return null;
    }

    public int getStackSize() {
        return stack.size();
    }

    /**
     * Places a stone on the board.
     * @param move  The move to do on the board.
     * @return      Returns the number of points that are gained with the placement.
     * @throws PlacementException Thrown when a move cannot be performed.
     */
    public int placeStone(Move move) throws PlacementException {
        int x = move.getX();
        int y = move.getY();
        if(this.board[x][y] != null) {
            throw new PlacementException("This location on the board has already a stone on it.");
        }
        this.board[x][y] = move.getStone();

        // TODO: Implement the point system.
        return -1;
    }

    /**
     * Checks if a stone can be placed on the provided location on the board.
     * @param move  The move to do on the board.
     * @return      True if the stone can be placed, otherwise false.
     */
    public boolean isValidMove(Move move) {
        int x = move.getX();
        int y = move.getY();
        Stone stone = move.getStone();

        // If the current board is empty, any stone is valid as a placement.
        if(isEmpty()) {
            return true;
        }

        // Determine the neighbours of the stone, when placed on the board.
        Stone[] neighbours = new Stone[3];
        if(stone.getOrientation() == Orientation.TOP) {
            neighbours[0] = this.board[x][y - 1];
        } else {
            neighbours[0] = this.board[x][y + 1];
        }
        neighbours[1] = this.board[x - 1][y];
        neighbours[2] = this.board[x + 1][y];

        // The stone should have at least a single neighbour and each
        // neighbouring stone should have the opposite orientation.
        boolean hasNeighbour = false;
        boolean orientation = true;
        for(Stone neighbour : neighbours) {
            if (neighbour != null) {
                hasNeighbour = true;
                orientation = orientation && neighbour.getOrientation() != stone.getOrientation();
            }
        }
        if(!(hasNeighbour && orientation)) {
            return false;
        }

        // The numbers of the neighbours should match with this stone.
        boolean matchingNumbers = true;
        if(stone.getOrientation() == Orientation.TOP) {
            for (int i = 0; i < 3; i++) {
                if(neighbours[i] != null) {
                    if(i == 0) {
                        matchingNumbers = stone.getValues()[0] == neighbours[0].getValues()[2];
                        matchingNumbers = matchingNumbers && stone.getValues()[1] == neighbours[0].getValues()[1];
                    } else if (i == 1) {
                        matchingNumbers = matchingNumbers && stone.getValues()[0] == neighbours[1].getValues()[0];
                        matchingNumbers = matchingNumbers && stone.getValues()[2] == neighbours[1].getValues()[1];
                    } else {
                        matchingNumbers = matchingNumbers && stone.getValues()[1] == neighbours[2].getValues()[0];
                        matchingNumbers = matchingNumbers && stone.getValues()[2] == neighbours[2].getValues()[2];
                    }
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                if(neighbours[i] != null) {
                    if(i == 0) {
                        matchingNumbers = stone.getValues()[2] == neighbours[0].getValues()[0];
                        matchingNumbers = matchingNumbers && stone.getValues()[1] == neighbours[0].getValues()[1];
                    } else if (i == 1) {
                        matchingNumbers = matchingNumbers && stone.getValues()[0] == neighbours[1].getValues()[1];
                        matchingNumbers = matchingNumbers && stone.getValues()[2] == neighbours[1].getValues()[2];
                    } else {
                        matchingNumbers = matchingNumbers && stone.getValues()[0] == neighbours[2].getValues()[0];
                        matchingNumbers = matchingNumbers && stone.getValues()[1] == neighbours[2].getValues()[2];
                    }
                }
            }
        }
        return matchingNumbers;
    }

    /**
     * Gets the extreme values of the board on which stones are present.
     * @return A list with the minimal and maximal x and y coordinates.
     *         [x_min, y_min, x_max, y_max]
     */
    public int[] getExtremeValues() {
        int[] firstIndex = new int[]{NR_STONES * 2, NR_STONES * 2};
        int[] lastIndex  = new int[]{0,0};
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if(this.board[i][j] != null) {
                    // Find the minimum x variable.
                    if(i < firstIndex[0]) {
                        firstIndex[0] = i;
                    }
                    // Find the minimum y variable.
                    if(j < firstIndex[1]) {
                        firstIndex[1] = j;
                    }

                    // Find the maximum x variable.
                    if(i > lastIndex[0]) {
                        lastIndex[0] = i;
                    }
                    // Find the maximum y variable.
                    if(j > lastIndex[1]) {
                        lastIndex[1] = j;
                    }
                }
            }
        }
        return new int[]{firstIndex[0], firstIndex[1], lastIndex[0], lastIndex[1]};
    }

    public int[] getExtremeValuesMove() {
        List<Tuple<Tuple<Integer, Integer>, Orientation>> openFields = getOpenFields();
        int minX = 112;
        int minY = 112;
        int maxX = 0;
        int maxY = 0;
        for(Tuple<Tuple<Integer, Integer>, Orientation> field : openFields) {
            Tuple<Integer, Integer> coordinate = field.getLeft();
            if(coordinate.getLeft() < minX) {
                minX = coordinate.getLeft();
            }
            if(coordinate.getLeft() > maxX) {
                maxX = coordinate.getLeft();
            }
            if(coordinate.getRight() < minY) {
                minY = coordinate.getRight();
            }
            if(coordinate.getRight() > maxY) {
                maxY = coordinate.getRight();
            }
        }

        return new int[]{minX, minY, maxX, maxY};
    }

    /**
     * Updates the current board view with the latest lay-out.
     * @param move If true, the board is equipped with clickable spots on which possible tiles can be laid down.
     */
    public void update(boolean move) {
        int[] extremes;

        if (move) {
            extremes = getExtremeValuesMove();
        } else {
            extremes = getExtremeValues();
        }

        int minX = extremes[0];
        int minY = extremes[1];
        int maxX = extremes[2];
        int maxY = extremes[3];

        GridPane pane = new GridPane();
        GridPane space = new GridPane();
        for (int x = 0; x < maxX - minX + 1; x++) {
            pane.getColumnConstraints().add(new ColumnConstraints(Style.CENTER));
            space.getColumnConstraints().add(new ColumnConstraints(Style.CENTER));
        }
        for (int y = 0; y < maxY - minY + 1; y++) {
            pane.getRowConstraints().add(new RowConstraints(Style.HEIGHT));
            space.getRowConstraints().add(new RowConstraints(Style.HEIGHT));
        }

        // Fill the board with stones and if needed empty spots.
        for (int x = 0; x < maxX - minX + 1; x++) {
            for (int y = 0; y < maxY - minY + 1; y++) {
                if (this.board[x + minX][y + minY] != null) {
                    // Add the stone to the board.
                    pane.add(this.board[x + minX][y + minY].toJavaFX(), x, y);
                } else if (move) {
                    // Add an empty field.
                    EmptyLocation location = getEmptyLocation(x + minX, y + minY);
                    if(location != null) {
                        space.add(location, x, y);
                    }
                }
            }
        }

        this.getChildren().addAll(pane, space);
        this.alignmentProperty().setValue(Pos.TOP_LEFT);
    }

    /**
     * Get all the indices of the fields that are just next to the already existing structure.
     * Iterates over the board location that contains possible stones. If a stone is encountered its neighbours are
     * added to the coordinates list if they are null.
     * @return A list with the orientation of the open field, and the coordinates of these fields.
     */
    public List<Tuple<Tuple<Integer, Integer>, Orientation>> getOpenFields() {
        List<Tuple<Tuple<Integer, Integer>, Orientation>> coordinates = new ArrayList<>();
        int[] extremes = getExtremeValues();
        int minX = extremes[0];
        int minY = extremes[1];
        int maxX = extremes[2];
        int maxY = extremes[3];
        for (int x = minX; x < maxX + 1; x++) {
            for (int y = minY; y < maxY + 1; y++) {
                Stone stone = this.board[x][y];
                if(stone != null) {
                    // Check the location above or under the stone, depending on the orientation.
                    if(stone.getOrientation() == Orientation.TOP) {
                        if(this.board[x][y - 1] == null) {
                            coordinates.add(new Tuple<>(new Tuple<>(x, y - 1), Orientation.DOWN));
                        }
                    } else {
                        if(this.board[x][y + 1] == null) {
                            coordinates.add(new Tuple<>(new Tuple<>(x, y + 1), Orientation.TOP));
                        }
                    }

                    // Check the left side of the stone.
                    if(this.board[x - 1][y] == null) {
                        Orientation orientation;
                        if (this.board[x][y].getOrientation() == Orientation.TOP) {
                            orientation = Orientation.DOWN;
                        } else {
                            orientation = Orientation.TOP;
                        }
                        coordinates.add(new Tuple<>(new Tuple<>(x - 1, y), orientation));
                    }

                    // Check the right side of the stone.
                    if(this.board[x + 1][y] == null) {
                        Orientation orientation;
                        if (this.board[x][y].getOrientation() == Orientation.TOP) {
                            orientation = Orientation.DOWN;
                        } else {
                            orientation = Orientation.TOP;
                        }
                        coordinates.add(new Tuple<>(new Tuple<>(x + 1, y), orientation));
                    }
                }
            }
        }
        return coordinates;
    }

    /**
     * Iterates over the list of open fields and if this list contains the coordinates that are provided it creates a
     * new empty location and returns it.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The empty location on (x,y) or null if there is not an empty location.
     */
    public EmptyLocation getEmptyLocation(int x, int y) {
        for(Tuple<Tuple<Integer, Integer>, Orientation> field : getOpenFields()) {
            Tuple<Integer, Integer> coordinate = field.getLeft();
            if(coordinate.getLeft().equals(x) && coordinate.getRight().equals(y)) {
                return new EmptyLocation(field.getRight(), x, y);
            }
        }
        return null;
    }

}

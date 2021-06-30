package nl.supertom01.triominos.model;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import nl.supertom01.triominos.exceptions.PlacementException;

import java.util.List;

/**
 * The board class.
 *
 * @author Tom Meulenkamp
 * @version 01-07-2021
 */
public class Board {

    private List<Stone> unUsedStones;
    private Stone[][] board;

    public Board() {
        this.unUsedStones = Stone.shuffle();
        this.board = new Stone[112][112];
    }

    /**
     * Checks if the current board is empty.
     * @return True if the board is empty, otherwise false.
     */
    public boolean isEmpty() {
        boolean empty = true;
        for(Stone[] row : board) {
            for(Stone stone : row) {
                if(stone != null) {
                    empty = false;
                    break;
                }
            }
        }
        return empty;
    }

    /**
     * Places a stone on the board.
     * @param stone The stone to place.
     * @param x     The x-coordinate on the board.
     * @param y     The y-coordinate on the board.
     * @return      Returns the number of points that are gained with the placement.
     */
    public int placeStone(Stone stone, int x, int y) throws PlacementException {
        if(this.board[x][y] != null) {
            throw new PlacementException("This location on the board has already a stone on it.");
        }
        this.board[x][y] = stone;

        // TODO: Implement the point system.
        return -1;
    }

    /**
     * Checks if a stone can be placed on the provided location on the board.
     * @param stone The stone to place.
     * @param x     The x-coordinate on the board.
     * @param y     The y-coordinate on the board.
     * @return      True if the stone can be placed, otherwise false.
     */
    public boolean isValidMove(Stone stone, int x, int y) {
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
     * Converts the Java Board object to a pane that can be used by JavaFX.
     * @return A pane that contains all the stones that are on the board.
     */
    public Pane toJavaFX() {
        // Find the first top left index that has a stone in it.
        int[] firstIndex = new int[]{112,112};
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
                    if(j > lastIndex[1]) {
                        lastIndex[1] = j;
                    }
                }
            }
        }
        int width = lastIndex[0] - firstIndex[0] + 1;
        int height = lastIndex[1] - firstIndex[1] + 1;

        GridPane pane = new GridPane();
        for (int i = 0; i < width; i++) {
            pane.getColumnConstraints().add(new ColumnConstraints(75));
        }
        for (int i = 0; i < height; i++) {
            pane.getRowConstraints().add(new RowConstraints(130));
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(this.board[i + firstIndex[0]][j + firstIndex[1]] == null) {
                    continue;
                }
                pane.add(this.board[i + firstIndex[0]][j + firstIndex[1]].toJavaFX(), i, j);
            }
        }
        return pane;
    }

}

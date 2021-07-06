package nl.supertom01.triominos.model;

import java.util.ArrayList;
import java.util.List;

import nl.supertom01.triominos.exceptions.PlacementException;

/**
 * The player model class.
 *
 * @author Tom Meulenkamp
 * @version 01-07-2021
 */
public abstract class Player {

    /** The stones in the player's inventory. */
    private final List<Stone> stones;

    /** The name of this player. */
    private final String username;

    /** The total number of points of this player. */
    private int points;

    public Player(String username) {
        this.username = username;
        this.stones = new ArrayList<>();
        this.points = 0;
    }

    public String getUsername() {
        return username;
    }

    public List<Stone> getStones() {
        return stones;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Add a stone to this player's stones.
     * @param stone The stone to add
     */
    public void addStone(Stone stone) {
        stones.add(stone);
    }

    /**
     * Adds points to this player.
     * @param points The number of points to add.
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Determine the move that the player wants to make.
     * @param board The GUI scene.
     * @return A valid move that has been chosen by the player.
     */
    public abstract Move determineMove(Board board);

    /**
     * Makes a move on the board.
     * @param board The board on which the move should be made.
     */
    public void makeMove(Board board) {
        Move move = determineMove(board);
        int points = 0;
        try {
            points = board.placeStone(move);
        } catch (PlacementException e) {
            System.err.printf("[ERROR] [PLAYER] %s: \"%s\"", getUsername(), e.getMessage());
        }
        addPoints(points);
    }
}

package nl.supertom01.triominos.model;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.supertom01.triominos.controller.HumanPlayer;
import nl.supertom01.triominos.styles.Style;

/**
 * The game class connects the players and the board.
 * It keeps track of the progress of the game and it has a JavaFX representation.
 *
 * @author Tom Meulenkamp
 */
public class Game extends GridPane {

    /** The amount of stones with which each player starts. */
    public static final int STOCK_SIZE = 10;

    /** The board on which the game will be played. */
    private final Board board;

    /** The players that are taking part in the game. */
    private final Player[] players;

    /** The current player */
    private int currentPlayer;

    public Game(Player[] players) {
        this.board = new Board();
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * Provides each player with their private stack of stones.
     */
    public void initialize() {
        for (Player player : this.players) {
            player.resetStones();
            for (int i = 0; i < STOCK_SIZE; i++) {
                player.addStone(this.board.getFromStack());
            }
        }
    }

    /**
     * Find the index of the player.
     * @param player The player to look for.
     * @return The index of the player object in the players array.
     */
    public int getPlayerIndex(Player player) {
        for (int i = 0; i < this.players.length; i++) {
            if (this.players[i].equals(player)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Determines the player that may start.
     */
    public int getFirstPlayer() {
        // Determine which player may start.
        // Either the player with the highest triple (or if no one has a triple, start with the highest stone).
        Player highestTriplePlayer = null;
        Stone highestTripleStone = new Stone(-1,-1,-1);
        Player highestPlayer = null;
        Stone highestStone = new Stone(-1,-1,-1);
        for (Player player : this.players) {
            for (Stone stone : player.getStones()) {
                if (stone.isTriple() && stone.getSum() > highestTripleStone.getSum()) {
                    highestTriplePlayer = player;
                    highestTripleStone = stone;
                }
                if(stone.getSum() > highestStone.getSum()) {
                    highestStone = stone;
                    highestPlayer = player;
                }
            }
        }

        if(highestTriplePlayer != null) {
            return getPlayerIndex(highestTriplePlayer);
        } else {
            return getPlayerIndex(highestPlayer);
        }

    }

    /**
     * Checks if the current round is finished.
     * A round is finished if one person has no stones left or if no one is able to place a stone.
     * @return true if this round has indeed finished.
     */
    public boolean isRoundFinished() {
        if(board.getStackSize() != 0) {
            for (Player player : this.players) {
                if (player.getStones().size() == 0) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Checks if the complete game has finished.
     * A game has finished if a round is finished and if at least one of the players has more than 400 points.
     * @return true if the game has indeed finished.
     */
    public boolean isFinished() {
        if (isRoundFinished()) {
            for (Player player : this.players) {
                if (player.getPoints() >= 400) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * Determines the winner of the game, based on the player with the maximum score.
     * @return The winner of the game.
     */
    public Player getWinner() {
        Player winner = players[0];
        for (Player player : this.players) {
            if (player.getPoints() > winner.getPoints()) {
                winner = player;
            }
        }
        return winner;
    }

    public void play() {
        update();
        while(!isFinished()) {
            initialize();
            currentPlayer = getFirstPlayer();
            while(!isRoundFinished()) {
                board.update(players[currentPlayer] instanceof HumanPlayer);
                players[currentPlayer].makeMove(board);
                update();
                currentPlayer = (currentPlayer + 1) % players.length;
            }
        }
    }

    public void update() {
        // Set up the left bar.
        GridPane leftBar = new GridPane();
        leftBar.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        leftBar.getColumnConstraints().add(new ColumnConstraints(200));

        // Add the title of the game to the bar.
        Text title = new Text("Triominos");
        title.setFont(Font.font(Style.FONT_FAMILY, FontWeight.BOLD, Style.FONT_SIZE_TITLE));
        leftBar.add(title, 0, 0);

        // Add "players:" to the bar.
        Text players = new Text("Players:");
        players.setFont(Font.font(Style.FONT_FAMILY, Style.FONT_SIZE_BODY));
        leftBar.add(players, 0, 1);

        // Add the players to the bar
        for (int i = 0; i < this.players.length; i++) {
            Text player = new Text(this.players[i].getUsername());
            Text score  = new Text(String.valueOf(this.players[i].getPoints()));
            Font font;
            if(i == currentPlayer) {
                font = Font.font(Style.FONT_FAMILY, FontWeight.BOLD, Style.FONT_SIZE_BODY);
            } else {
                font = Font.font(Style.FONT_FAMILY, Style.FONT_SIZE_BODY);
            }
            player.setFont(font);
            score.setFont(font);
            leftBar.add(player, 0, i + 2);
            leftBar.add(score, 1, i + 2);
        }

        // Display the stones of the current player.
        // TODO: Implement.

        this.add(leftBar, 0, 0);
        this.add(board, 1, 0);
    }

}

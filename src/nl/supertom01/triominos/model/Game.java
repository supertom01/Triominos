package nl.supertom01.triominos.model;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import nl.supertom01.triominos.styles.Style;

/**
 * The game class connects the players and the board.
 * It keeps track of the progress of the game and it has a JavaFX representation.
 *
 * @author Tom Meulenkamp
 */
public class Game {

    /** The amount of stones with which each player starts. */
    public static final int STOCK_SIZE = 10;

    /** The board on which the game will be played. */
    private final Board board;

    /** The players that are taking part in the game. */
    private final Player[] players;

    private int currentPlayer;

    public Game(Player[] players) {
        this.board = new Board();
        this.players = players;
    }

    /**
     * Provides each player with their private stack of stones.
     */
    public void initialize() {
        for (Player player : this.players) {
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
    public void play() {
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
            currentPlayer = getPlayerIndex(highestTriplePlayer);
        } else {
            currentPlayer = getPlayerIndex(highestPlayer);
        }

        while(!isFinished()) {
            this.players[currentPlayer].makeMove(this.board);
            update();
            currentPlayer = (currentPlayer + 1) % players.length;
        }

        // TODO: Check if one of the players has 400 points or more, if so, the game stops otherwise a new round starts.

    }

    /**
     * Checks if the current round is finished.
     * A round is finished if one person has no stones left or if no one is able to place a stone.
     * @return true if the game has indeed finished.
     */
    public boolean isFinished() {
        // TODO: Implement.
        return true;
    }

    /**
     * Updates the board and shows the user the progress of the game.
     */
    public void update() {
        // TODO: Implement.
        // TODO: Might want to move this to the Main class, where JavaFX is working.
    }

    /**
     * Transforms the game object into one that can be used in JavaFX.
     * @return A JavaFX pane that can be used in the GUI.
     */
    public Pane toJavaFX() {
        GridPane pane = new GridPane();

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

        pane.add(leftBar, 0, 0);

        pane.add(this.board.toJavaFX(), 1, 0);
        return pane;
    }


}

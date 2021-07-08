package nl.supertom01.triominos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.supertom01.triominos.controller.HumanPlayer;
import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.model.Game;
import nl.supertom01.triominos.model.Move;
import nl.supertom01.triominos.model.Player;
import nl.supertom01.triominos.model.Stone;

public class Main extends Application {

    private Game game;
    private int currentPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // TODO: Initialize the players.
        Player[] players = new Player[]{
            new HumanPlayer("Tom"),
            new HumanPlayer("Robin")
        };

        Board board = new Board();
        board.placeStone(new Move(new Stone(1,2,3), 56, 56));
        board.update(true);

        Scene scene = new Scene(board, 600, 300);
        scene.setFill(Color.BLUE);

        primaryStage.setTitle("Triominos");
        primaryStage.setScene(scene);
        primaryStage.show();

        // TODO: Check if the game has finished, or if we need another round.

    }

    public static void main(String[] args) {
        launch(args);
    }

}

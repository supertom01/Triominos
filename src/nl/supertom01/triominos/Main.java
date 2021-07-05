package nl.supertom01.triominos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.model.Game;
import nl.supertom01.triominos.model.Move;
import nl.supertom01.triominos.model.Player;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Player p1 = new Player("Test 1") {
            @Override
            public Move determineMove(Board board) {
                return null;
            }
        };

        Player p2 = new Player("Test 2") {
            @Override
            public Move determineMove(Board board) {
                return null;
            }
        };

        Player p3 = new Player("Test 3") {
            @Override
            public Move determineMove(Board board) {
                return null;
            }
        };

        Game game = new Game(new Player[]{p1, p2, p3});
        game.initialize();
        game.play();

        Scene scene = new Scene(game.toJavaFX(), 600, 300);
        scene.setFill(Color.DARKBLUE);

        primaryStage.setTitle("Triominos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

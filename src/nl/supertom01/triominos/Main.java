package nl.supertom01.triominos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.supertom01.triominos.model.Board;
import nl.supertom01.triominos.model.Move;
import nl.supertom01.triominos.model.Stone;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Board board = new Board();

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

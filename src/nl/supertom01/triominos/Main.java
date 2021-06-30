package nl.supertom01.triominos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nl.supertom01.triominos.controller.Board;
import nl.supertom01.triominos.model.Stone;

public class Main extends Application {

    private Board board;

    @Override
    public void start(Stage primaryStage) throws Exception {
        board = new Board();

        Stone s1 = new Stone(1, 2, 3);
        Stone s2 = new Stone(1, 2, 1);
        s2.rotateRight();
        Stone s3 = new Stone(1, 1, 1);
        board.placeStone(s1, 56, 56);
        board.placeStone(s2, 57, 56);
        board.placeStone(s3, 57, 55);

        Scene scene = new Scene(board.toJavaFX(), 600, 300);
        scene.setFill(Color.BROWN);

        primaryStage.setTitle("Triominos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

package nl.supertom01.triominos.view;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import nl.supertom01.triominos.model.Orientation;
import nl.supertom01.triominos.styles.Style;

/**
 * This JavaFX object represents a location where a stone
 * can be attached to the already existing structure on the board.
 *
 * @author Tom Meulenkamp
 */
public class EmptyLocation extends Triangle {

    private final int x;
    private final int y;

    /**
     * Creates a new empty location.
     * @param orientation The orientation of this location, this follows the same principle as a normal Stone, so this
     *                    property indicates where the horizontal piece is located. (Top or down)
     * @param x The x-coordinate on the board of this piece.
     * @param y The y-coordinate on the board of this piece.
     */
    public EmptyLocation(Orientation orientation, int x, int y) {
        super(Style.WIDTH, orientation);
        setFill(Color.TRANSPARENT);

        // TODO: Move the handlers to the controller package.
        setOnMouseClicked(this::handleClick);
        setOnMouseEntered(mouseEvent -> {
            setFill(Style.EMPTY_LOCATION_COLOR);
            this.getScene().setCursor(Cursor.HAND);
        });
        setOnMouseExited(mouseEvent -> {
            setFill(Color.TRANSPARENT);
            this.getScene().setCursor(Cursor.DEFAULT);
        });
        this.x = x;
        this.y = y;
    }

    /**
     * When this location has been clicked, get the selected stone and check if it can be placed.
     * If so, place the stone, otherwise make sure that the user selects another one.
     * @param mouseEvent The mouse click on this location.
     */
    public void handleClick(MouseEvent mouseEvent) {
        System.out.printf("Clicked on %d,%d%n", x, y);
    }

}

package nl.supertom01.triominos.model;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.supertom01.triominos.styles.Style;

/**
 * The stone class.
 *
 * @author Tom Meulenkamp
 * @version 01-07-2021
 */
public class Stone {

    /**
     * The current orientation of this stone.
     *
     * ────────
     * \     /             /\
     *  \   /      OR    /   \
     *   \/             /     \
     *                  ───────
     *  TOP              DOWN
     */
    private Orientation orientation;

    /** The values that are on a stone. */
    private final int[] values;

    public Stone(int nr1, int nr2, int nr3) {
        this.orientation = Orientation.DOWN;
        this.values = new int[]{nr1, nr2, nr3};
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int[] getValues() {
        return values;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Returns true if this stone is a triple (each number on the stone is equal).
     * @return True if this stone is a triple and otherwise false.
     */
    public boolean isTriple() {
        return this.values[0] == this.values[1] && this.values[1] == this.values[2];
    }

    /**
     * Determine the sum of the stone.
     * @return The sum of this stone.
     */
    public int getSum() {
        return this.values[0] + this.values[1] + this.values[2];
    }

    /**
     * Rotates this stone clockwise, by one position.
     */
    public void rotateRight() {
        if (this.orientation == Orientation.DOWN) {
            int temp = this.values[2];
            this.values[2] = this.values[1];
            this.values[1] = this.values[0];
            this.values[0] = temp;
            this.orientation = Orientation.TOP;
        } else {
            this.orientation = Orientation.DOWN;
        }
    }

    /**
     * Rotates this stone counter-clockwise, by one position.
     */
    public void rotateLeft() {
        if (this.orientation == Orientation.TOP) {
            int temp = this.values[0];
            this.values[0] = this.values[1];
            this.values[1] = this.values[2];
            this.values[2] = temp;
            this.orientation = Orientation.DOWN;
        } else {
            this.orientation = Orientation.TOP;
        }
    }

    /**
     * Converts the Java object to a pane that can be used in JavaFX.
     * @return A pane that contains a white triangle, with the numbers that should be on it.
     */
    public Pane toJavaFX() {
        Point2D p1;
        Point2D p2;
        Point2D p3;
        if (this.orientation == Orientation.TOP) {
            p1 = new Point2D(0,0);
            p2 = new Point2D(Style.WIDTH, 0);
            p3 = new Point2D(Style.CENTER, Style.HEIGHT);
        } else {
            p1 = new Point2D(Style.CENTER,0);
            p2 = new Point2D(0, Style.HEIGHT);
            p3 = new Point2D(Style.WIDTH,Style.HEIGHT);
        }

        Point2D center = p1.midpoint(p2).midpoint(p3);
        Point2D p1Corrected = p1.subtract(center);
        Point2D p2Corrected = p2.subtract(center);
        Point2D p3Corrected = p3.subtract(center);

        Polygon triangle = new Polygon(
                p1Corrected.getX(), p1Corrected.getY(),
                p2Corrected.getX(), p2Corrected.getY(),
                p3Corrected.getX(), p3Corrected.getY()
        );
        triangle.setFill(Color.WHITE);
        triangle.setStroke(Color.BLACK);
        triangle.setLayoutX(center.getX());
        triangle.setLayoutY(center.getY());

        Text t1 = new Text();
        Text t2 = new Text();
        Text t3 = new Text();
        t1.setText(String.valueOf(this.values[0]));
        t2.setText(String.valueOf(this.values[1]));
        t3.setText(String.valueOf(this.values[2]));
        t1.setFont(Font.font(Style.FONT_FAMILY, Style.FONT_SIZE_STONE));
        t2.setFont(Font.font(Style.FONT_FAMILY, Style.FONT_SIZE_STONE));
        t3.setFont(Font.font(Style.FONT_FAMILY, Style.FONT_SIZE_STONE));

        StackPane pane = new StackPane();
        pane.getChildren().addAll(triangle, t1, t2, t3);
        if(this.orientation == Orientation.TOP) {
            StackPane.setAlignment(t1, Pos.TOP_LEFT);
            StackPane.setMargin(t1, new Insets(0, 0, 0, Style.FONT_MARGIN));
            StackPane.setAlignment(t2, Pos.TOP_RIGHT);
            StackPane.setMargin(t2, new Insets(0, Style.FONT_MARGIN, 0, 0));
            StackPane.setAlignment(t3, Pos.BOTTOM_CENTER);
            StackPane.setMargin(t3, new Insets(0, 0, Style.FONT_MARGIN, 0));
        } else {
            StackPane.setAlignment(t1, Pos.TOP_CENTER);
            StackPane.setMargin(t1, new Insets(Style.FONT_MARGIN, 0, 0, 0));
            StackPane.setAlignment(t2, Pos.BOTTOM_RIGHT);
            StackPane.setMargin(t2, new Insets(0, Style.FONT_MARGIN, 0, 0));
            StackPane.setAlignment(t3, Pos.BOTTOM_LEFT);
            StackPane.setMargin(t3, new Insets(0, 0, 0, Style.FONT_MARGIN));
        }

        return pane;
    }

    /**
     * Two stones are equal if their orientation and values are equal. (Used for testing purposes)
     * @param  o The object that is being compared to this object.
     * @return True if the objects are deemed equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stone stone = (Stone) o;
        return orientation == stone.orientation && Arrays.equals(values, stone.values);
    }

    /**
     * Generates a string representation of the Stone object.
     * @return
     * ────────
     * \ 0 1 /             /\
     *  \ 2 /      OR    / 0 \
     *   \/             / 2 1 \
     *                  ───────
     * Where 0,1,2 represent the indices of the values array.
     */
    @Override
    public String toString() {
        if (this.orientation == Orientation.TOP) {
            return String.format("%d %d%n %d%n", this.values[0], this.values[1], this.values[2]);
        } else {
            return String.format(" %d%n%d %d%n", this.values[0], this.values[2], this.values[1]);
        }
    }

    /**
     * Generates all the stones that are used in the game and makes sure that they're shuffled.
     * @return A list with stones.
     */
    public static List<Stone> shuffle() {
        ArrayList<Stone> stones = new ArrayList<>(216);
        for (int i = 0; i < 6; i++) {
            for (int j = i; j < 6; j++) {
                for (int k = j; k < 6; k++) {
                    stones.add(new Stone(i,j,k));
                }
            }
        }
        Collections.shuffle(stones);
        return stones;
    }

}

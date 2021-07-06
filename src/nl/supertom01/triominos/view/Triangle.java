package nl.supertom01.triominos.view;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import nl.supertom01.triominos.model.Orientation;

/**
 * An equally sided triangle based on the JavaFX Polygon.
 *
 * @author Tom Meulenkamp
 */
public class Triangle extends Polygon {

    /**
     * Creates an equally sided triangle based on a provided leg width and the orientation of the triangle.
     * @param width         The width of the triangle (the length of a leg)
     * @param orientation   The orientation of this triangle.
     */
    public Triangle(double width, Orientation orientation) {
        double c = width / 2;
        double height = Math.sqrt(Math.pow(width, 2) - Math.pow(c, 2));

        Point2D p1;
        Point2D p2;
        Point2D p3;
        if (orientation == Orientation.TOP) {
            p1 = new Point2D(0, 0);
            p2 = new Point2D(width, 0);
            p3 = new Point2D(c, height);
        } else {
            p1 = new Point2D(c, 0);
            p2 = new Point2D(0, height);
            p3 = new Point2D(width, height);
        }

        Point2D center = p1.midpoint(p2).midpoint(p3);

        Point2D p1Corrected = p1.subtract(center);
        Point2D p2Corrected = p2.subtract(center);
        Point2D p3Corrected = p3.subtract(center);

        getPoints().addAll(
            p1Corrected.getX(), p1Corrected.getY(),
            p2Corrected.getX(), p2Corrected.getY(),
            p3Corrected.getX(), p3Corrected.getY()
        );

        setLayoutX(center.getX());
        setLayoutY(center.getY());
    }

}

package nl.supertom01.triominos.styles;

import javafx.scene.paint.Color;

public class Style {

    // Font
    public static final String FONT_FAMILY = "arial";

    // Left bar
    public static final int FONT_SIZE_TITLE = 34;
    public static final int FONT_SIZE_BODY = 21;

    // Stone
    public static final int FONT_SIZE_STONE = 30;
    public static final int FONT_MARGIN = 20;
    public static final int WIDTH = 150;
    public static final int CENTER = WIDTH / 2;
    public static final int HEIGHT = (int) Math.sqrt(Math.pow(WIDTH, 2) - Math.pow(CENTER, 2));

    // Empty location
    public static final Color EMPTY_LOCATION_COLOR = Color.LIGHTSKYBLUE;

}

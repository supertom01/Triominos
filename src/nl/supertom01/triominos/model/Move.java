package nl.supertom01.triominos.model;

/**
 * Represents a move performed by a player.
 * If the stone object is null, then the player draws a new stone.
 */
public record Move(Stone stone, int x, int y) {

    public Stone getStone() {
        return stone;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

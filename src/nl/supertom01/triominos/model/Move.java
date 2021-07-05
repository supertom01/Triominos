package nl.supertom01.triominos.model;

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

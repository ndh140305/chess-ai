import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private int coordinatesX;
    private int coordinatesY;
    private String color;
    public int value;
    int[][] positionValue;

    public Piece(int coordinatesX, int coordinatesY) {
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
    }

    /**
     * cons 2.
     *
     * @param coordinatesX x
     * @param coordinatesY y
     * @param color c
     */
    public Piece(int coordinatesX, int coordinatesY, String color) {
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
        this.color = color;
    }

    public Piece() {
        this.coordinatesX = 0;
        this.coordinatesY = 0;
    }

    public abstract String getSymbol();

    public abstract boolean canMove(Board board, int x, int y);

    public int getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(int coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public int getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(int coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean checkPosition(Piece piece) {
        return this.coordinatesX == piece.coordinatesX && this.coordinatesY == piece.coordinatesY;
    }

    public abstract Piece clone();
}

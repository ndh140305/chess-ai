public class Knight extends Piece {
    public Knight() {
        super();
    }

    public Knight(int x, int y) {
        setCoordinatesX(x);
        setCoordinatesY(y);
    }

    public Knight(int x, int y, String c) {
        setCoordinatesX(x);
        setCoordinatesY(y);
        setColor(c);
        setValue(300);
    }

    public String getSymbol() {
        return "N";
    }

    @Override
    public boolean canMove(Board board, int x, int y) {
        if (!board.validate(x, y)) {
            return false;
        }
        if (board.getAt(x, y) != null && board.getAt(x, y).getColor().equals(this.getColor())) {
            return false;
        }
        int dx = Math.abs(this.getCoordinatesX() - x);
        int dy = Math.abs(this.getCoordinatesY() - y);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    @Override
    public Knight clone() {
        Knight copy = new Knight(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
        return copy;
    }
}

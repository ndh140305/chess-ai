public class Queen extends Piece {
    public Queen() {
        super();
    }

    public Queen(int x, int y) {
        setCoordinatesX(x);
        setCoordinatesY(y);
    }

    public Queen(int x, int y, String c) {
        setCoordinatesX(x);
        setCoordinatesY(y);
        setColor(c);
        setValue(900);
    }

    public String getSymbol() {
        return "Q";
    }

    @Override
    public boolean canMove(Board board, int x, int y) {
        if (!board.validate(x, y)) return false;
        if (board.getAt(x, y) != null && board.getAt(x, y).getColor().equals(this.getColor())) return false;

        int cx = this.getCoordinatesX();
        int cy = this.getCoordinatesY();
        int dx = Integer.compare(x, cx);
        int dy = Integer.compare(y, cy);

        if (dx == 0 && dy == 0) return false;
        if (dx != 0 && dy != 0 && Math.abs(x - cx) != Math.abs(y - cy)) return false;
        if (dx == 0 || dy == 0 || Math.abs(x - cx) == Math.abs(y - cy)) {
            int i = cx + dx, j = cy + dy;
            while (i != x || j != y) {
                if (board.getAt(i, j) != null) return false;
                i += dx;
                j += dy;
            }
            return true;
        }
        return false;
    }

    @Override
    public Queen clone() {
        Queen copy = new Queen(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
        return copy;
    }
}

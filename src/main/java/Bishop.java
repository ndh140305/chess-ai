public class Bishop extends Piece {
    public Bishop() {
        super();
    }

    public Bishop(int x, int y) {
        setCoordinatesX(x);
        setCoordinatesY(y);
    }

    public Bishop(int x, int y, String c) {
        setCoordinatesX(x);
        setCoordinatesY(y);
        setColor(c);
        setValue(300);
    }

    public String getSymbol() {
        return "B";
    }

    @Override
    public boolean canMove(Board board, int x, int y) {
        if (!board.validate(x, y)) return false;
        if (board.getAt(x, y) != null && board.getAt(x, y).getColor().equals(this.getColor())) return false;

        int cx = this.getCoordinatesX();
        int cy = this.getCoordinatesY();
        int dx = x - cx;
        int dy = y - cy;

        if (Math.abs(dx) != Math.abs(dy)) return false;

        int stepX = Integer.compare(dx, 0);
        int stepY = Integer.compare(dy, 0);

        int i = cx + stepX, j = cy + stepY;
        while (i != x && j != y) {
            if (board.getAt(i, j) != null) return false;
            i += stepX;
            j += stepY;
        }

        return true;
    }

    @Override
    public Bishop clone() {
        Bishop copy = new Bishop(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
        return copy;
    }
}

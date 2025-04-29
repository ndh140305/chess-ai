public class Rook extends Piece {
    private boolean hasMoved = false;
    public Rook() {
        super();
    }

    /**
     * cons 1
     *
     * @param x x
     * @param y y
     */
    public Rook(int x, int y) {
        setCoordinatesX(x);
        setCoordinatesY(y);
    }

    /**
     * cons 2.
     *
     * @param x x
     * @param y y
     * @param c c
     */
    public Rook(int x, int y, String c) {
        setCoordinatesX(x);
        setCoordinatesY(y);
        setColor(c);
        setValue(500);
    }

    public String getSymbol() {
        return "R";
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }

    @Override
    public boolean canMove(Board board, int x, int y) {
        if (!board.validate(x, y)) return false;

        if (x == getCoordinatesX() && y == getCoordinatesY()) return false;

        if (x != getCoordinatesX() && y != getCoordinatesY()) return false;

        Piece target = board.getAt(x, y);
        if (target != null && target.getColor().equals(this.getColor())) return false;

        if (y == getCoordinatesY()) {
            int start = Math.min(x, getCoordinatesX()) + 1;
            int end = Math.max(x, getCoordinatesX());
            for (int i = start; i < end; i++) {
                if (board.getAt(i, y) != null) return false;
            }
        }

        if (x == getCoordinatesX()) {
            int start = Math.min(y, getCoordinatesY()) + 1;
            int end = Math.max(y, getCoordinatesY());
            for (int i = start; i < end; i++) {
                if (board.getAt(x, i) != null) return false;
            }
        }
        return true;
    }

    @Override
    public Rook clone() {
        Rook copy = new Rook(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
        copy.setHasMoved(this.hasMoved);
        return copy;
    }
}

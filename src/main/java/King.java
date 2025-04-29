public class King extends Piece {
    private boolean hasMoved = false;

    public King() {
        super();
    }

    public King(int x, int y) {
        setCoordinatesX(x);
        setCoordinatesY(y);
    }

    public King(int x, int y, String c) {
        setCoordinatesX(x);
        setCoordinatesY(y);
        setColor(c);
        setValue(10000);
    }

    public String getSymbol() {
        return "K";
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
        if (board.getAt(x, y) != null && board.getAt(x, y).getColor().equals(this.getColor())) return false;

        int dx = Math.abs(this.getCoordinatesX() - x);
        int dy = Math.abs(this.getCoordinatesY() - y);

        if (dx <= 1 && dy <= 1) return true;

        if (!hasMoved && dy == 0 && dx == 2 && canCastle(board, x, y)) {
            return true;
        }

        return false;
    }

    public boolean canCastle(Board board, int x, int y) {
        int row = this.getCoordinatesY();
        int colFrom = this.getCoordinatesX();
        int colTo = x;

        if (this.getColor().equals("white") && row != 1) return false;
        if (this.getColor().equals("black") && row != 8) return false;

        int rookCol = (colTo > colFrom) ? 8 : 1;
        Piece rook = board.getAt(rookCol, row);
        if (!(rook instanceof Rook)) return false;
        if (((Rook) rook).getHasMoved()) return false;

        int step = (colTo > colFrom) ? 1 : -1;
        for (int c = colFrom + step; c != rookCol; c += step) {
            if (board.getAt(c, row) != null) return false;
        }

        for (int c = colFrom; c != colTo + step; c += step) {
            if (board.isSquareAttacked(c, row, this.getColor())) return false;
        }
        return true;
    }

    public CastleMove doCastleAndReturnMove(Board board, int x, int y) {
        int kingFromX = this.getCoordinatesX();
        int kingFromY = this.getCoordinatesY();
        int kingToX   = x;
        int kingToY   = y;

        int rookFromX = (x > kingFromX) ? 8 : 1;
        int rookFromY = kingFromY;
        int rookToX   = (x > kingFromX) ? 6 : 4;
        int rookToY   = rookFromY;

        King king = this;
        Rook rook = (Rook) board.getAt(rookFromX, rookFromY);

        board.removeAt(kingFromX, kingFromY);
        board.removeAt(rookFromX, rookFromY);

        king.setCoordinatesX(kingToX);
        king.setCoordinatesY(kingToY);
        king.setHasMoved(true);
        board.addPiece(king);

        rook.setCoordinatesX(rookToX);
        rook.setCoordinatesY(rookToY);
        rook.setHasMoved(true);
        board.addPiece(rook);

        return new CastleMove(
                kingFromX, kingFromY,
                kingToX,   kingToY,
                king,
                rook,
                rookFromX, rookFromY,
                rookToX,   rookToY
        );
    }

    @Override
    public King clone() {
        King copy = new King(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
        copy.setHasMoved(this.hasMoved);
        return copy;
    }
}

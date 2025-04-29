public class Pawn extends Piece {
    private boolean enPassantVulnerable = false;

    public Pawn() {
        super();
    }

    public Pawn(int x, int y) {
        setCoordinatesX(x);
        setCoordinatesY(y);
    }

    public Pawn(int x, int y, String c) {
        setCoordinatesX(x);
        setCoordinatesY(y);
        setColor(c);
        setValue(100);
    }

    public String getSymbol() {
        return "P";
    }

    @Override
    public boolean canMove(Board board, int x, int y) {
        if (!board.validate(x, y)) return false;

        int dx = x - this.getCoordinatesX();
        int dy = y - this.getCoordinatesY();
        int direction = this.getColor().equals("white") ? 1 : -1;
        int startRow = this.getColor().equals("white") ? 2 : 7;

        if (dx == 0 && dy == direction && board.getAt(x, y) == null) {
            return true;
        }

        if (dx == 0 && dy == 2 * direction && this.getCoordinatesY() == startRow
                && board.getAt(x, y) == null && board.getAt(x, y - direction) == null) {
            return true;
        }

        if (Math.abs(dx) == 1 && dy == direction) {
            Piece target = board.getAt(x, y);
            if (target != null && !target.getColor().equals(this.getColor())) return true;

            Piece adjacent = board.getAt(x, this.getCoordinatesY());
            if (adjacent instanceof Pawn && !adjacent.getColor().equals(this.getColor())) {
                Pawn adjPawn = (Pawn) adjacent;
                return adjPawn.isEnPassantVulnerable();
            }
        }

        return false;
    }

    public void promoteTo(Board board, String pieceType) {
        if ((this.getColor().equals("white") && this.getCoordinatesY() == 8) ||
                (this.getColor().equals("black") && this.getCoordinatesY() == 1)) {

            board.removeAt(this.getCoordinatesX(), this.getCoordinatesY());
            Piece promotedPiece;

            switch (pieceType.toLowerCase()) {
                case "queen":
                    promotedPiece = new Queen(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
                    break;
                case "rook":
                    promotedPiece = new Rook(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
                    break;
                case "bishop":
                    promotedPiece = new Bishop(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
                    break;
                case "knight":
                    promotedPiece = new Knight(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
                    break;
                default:
                    promotedPiece = new Queen(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
            }

            board.addPiece(promotedPiece);
        }
    }

    public boolean isEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public void setEnPassantVulnerable(boolean enPassantVulnerable) {
        this.enPassantVulnerable = enPassantVulnerable;
    }

    @Override
    public Pawn clone() {
        Pawn copy = new Pawn(this.getCoordinatesX(), this.getCoordinatesY(), this.getColor());
        return copy;
    }
}

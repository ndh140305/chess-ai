import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    private ArrayList<Piece> pieces;

    public Board() {
        this.pieces = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        for (Piece p: pieces) {
            if (p.getCoordinatesX() == piece.getCoordinatesX()
                    && p.getCoordinatesY() == piece.getCoordinatesY()) {
                return;
            }
        }
        this.pieces.add(piece);
    }

    public boolean validate(int x, int y) {
        return (x >= 1 && x <= 8 && y >= 1 && y <= 8);
    }

    public void removeAt(int x, int y) {
        pieces.remove(getAt(x, y));
    }

    public Piece getAt(int x, int y) {
        for (Piece p: pieces) {
            if (p.getCoordinatesX() == x && p.getCoordinatesY() == y) {
                return p;
            }
        }
        return null;
    }

    public Piece getKing (String color) {
        for (Piece piece: pieces) {
            if (piece.getClass().getSimpleName().equals("King") && piece.getColor().equals(color)) {
                return piece;
            }
        }
        return null;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public void clear() {
        pieces.clear();
    }

    public boolean isSquareAttacked(int x, int y, String color) {
        String opponentColor = color.equals("white") ? "black" : "white";

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getAt(col, row);
                if (piece != null && piece.getColor().equals(opponentColor)) {
                    if (piece.canMove(this, x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Board clone() {
        Board copy = new Board();
        for (Piece piece : this.getPieces()) {
            copy.addPiece(piece.clone());
        }
        return copy;
    }
}

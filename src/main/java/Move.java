public class Move {
    private int startX;
    private int endX;
    private int startY;
    private int endY;
    private Piece movedPiece;
    private Piece killedPiece;

    /**
     * ef.
     *
     */
    public Move() {
        this.startX = 0;
        this.endX = 0;
        this.startY = 0;
        this.endY = 0;
    }

    /**
     * e 3.
     *
     * @param startX x
     * @param endX x
     * @param startY y
     * @param endY y
     * @param movedPiece p
     */
    public Move(int startX, int endX, int startY, int endY, Piece movedPiece) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.movedPiece = movedPiece;
    }

    /**
     * r e.
     *
     * @param startX x
     * @param endX y
     * @param startY y
     * @param endY y
     * @param movedPiece m
     * @param killedPiece v
     */
    public Move(int startX, int endX, int startY, int endY, Piece movedPiece, Piece killedPiece) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.movedPiece = movedPiece;
        this.killedPiece = killedPiece;
    }

    /**
     * re r.
     *
     * @return string
     */
    public String toString() {
        String color = movedPiece.getColor();
        String[] letter = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String x = letter[endX - 1];
        String y = Integer.toString(endY);
        return color + "-" + movedPiece.getSymbol() + x + y;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public Piece getKilledPiece() {
        return killedPiece;
    }

    public void setKilledPiece(Piece killedPiece) {
        this.killedPiece = killedPiece;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public void setMovedPiece(Piece movedPiece) {
        this.movedPiece = movedPiece;
    }

    public Move clone() {
        Piece clonedMovedPiece = this.movedPiece != null ? this.movedPiece.clone() : null;
        Piece clonedKilledPiece = this.killedPiece != null ? this.killedPiece.clone() : null;
        return new Move(this.startX, this.endX, this.startY, this.endY, clonedMovedPiece, clonedKilledPiece);
    }
}

public class CastleMove extends Move {
    private Rook rook;
    private int rookStartX;
    private int rookStartY;
    private int rookEndX;
    private int rookEndY;

    public CastleMove(int kingStartX, int kingStartY,
                      int kingEndX,   int kingEndY,
                      King king,
                      Rook rook,
                      int rookStartX, int rookStartY,
                      int rookEndX,   int rookEndY) {
        super(kingStartX, kingEndX, kingStartY, kingEndY, king);
        this.rook         = rook;
        this.rookStartX   = rookStartX;
        this.rookStartY   = rookStartY;
        this.rookEndX     = rookEndX;
        this.rookEndY     = rookEndY;
    }

    public Rook getRook() {
        return rook;
    }

    public int getRookStartX() {
        return rookStartX;
    }

    public int getRookStartY() {
        return rookStartY;
    }

    public int getRookEndX() {
        return rookEndX;
    }

    public int getRookEndY() {
        return rookEndY;
    }

    @Override
    public String toString() {
        String kingStr = "K(" + getStartX() + "," + getStartY() + "->" + getEndX() + "," + getEndY() + ")";
        String rookStr = "R(" + rookStartX + "," + rookStartY + "->" + rookEndX + "," + rookEndY + ")";
        return getMovedPiece().getColor() + "-Castle " + kingStr + " & " + rookStr;
    }
}

public class PromotionMove extends Move {
    private final String promotionType;

    public PromotionMove(int startX, int endX, int startY, int endY, Piece movedPiece, String promotionType) {
        super(startX, endX, startY, endY, movedPiece);
        this.promotionType = promotionType;
    }

    public PromotionMove(int startX, int endX, int startY, int endY, Piece movedPiece, Piece killedPiece, String promotedTo) {
        super(startX, endX, startY, endY, movedPiece, killedPiece);
        this.promotionType = promotedTo;
    }

    public String getPromotionType() {
        return promotionType;
    }

    @Override
    public String toString() {
        String base = super.toString();
        return base + "=" + promotionType.charAt(0);
    }

    @Override
    public Move clone() {
        Piece clonedMoved = getMovedPiece() != null ? getMovedPiece().clone() : null;
        Piece clonedKilled = getKilledPiece() != null ? getKilledPiece().clone() : null;
        return new PromotionMove(getStartX(), getEndX(), getStartY(), getEndY(), clonedMoved, clonedKilled, promotionType);
    }
}

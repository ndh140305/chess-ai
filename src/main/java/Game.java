import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private String currentTurnColor;
    private Board board;
    private ArrayList<Move> moveHistory;

    public Game() {
        board = new Board();
        currentTurnColor = "white";
        moveHistory = new ArrayList<>();
        initBoard();
    }

    private void initBoard() {
        board.clear();
        board.addPiece(new Rook(1, 1, "white"));
        board.addPiece(new Knight(2, 1, "white"));
        board.addPiece(new Bishop(3, 1, "white"));
        board.addPiece(new Queen(4, 1, "white"));
        board.addPiece(new King(5, 1, "white"));
        board.addPiece(new Bishop(6, 1, "white"));
        board.addPiece(new Knight(7, 1, "white"));
        board.addPiece(new Rook(8, 1, "white"));
        for (int x = 1; x <= 8; x++) {
            board.addPiece(new Pawn(x, 2, "white"));
        }

        board.addPiece(new Rook(1, 8, "black"));
        board.addPiece(new Knight(2, 8, "black"));
        board.addPiece(new Bishop(3, 8, "black"));
        board.addPiece(new Queen(4, 8, "black"));
        board.addPiece(new King(5, 8, "black"));
        board.addPiece(new Bishop(6, 8, "black"));
        board.addPiece(new Knight(7, 8, "black"));
        board.addPiece(new Rook(8, 8, "black"));
        for (int x = 1; x <= 8; x++) {
            board.addPiece(new Pawn(x, 7, "black"));
        }
    }

    public void showMoveHistory() {
        int turn = 1;
        for (int i = 0; i < moveHistory.size(); i++) {
            Move move = moveHistory.get(i);
            String moveStr = move.toString();
            System.out.print(turn + " "  + moveStr);
            turn++;
        }
        System.out.println();
    }

    /**
     * fe fe.
     *
     * @param piece p
     * @param x x
     * @param y y
     */
    public void movePiece(Piece piece, int x, int y) {
        if (!piece.getColor().equals(currentTurnColor)) return;
        if (!piece.canMove(this.board, x, y)) return;

        int oldX = piece.getCoordinatesX();
        int oldY = piece.getCoordinatesY();
        Piece target = board.getAt(x, y);
        boolean isKilled = target != null && !target.getColor().equals(piece.getColor());
        Piece enPassantTarget = null;

        if (piece instanceof King) {
            ((King) piece).setHasMoved(true);
            int dx = x - oldX;
            if (Math.abs(dx) == 2 && ((King) piece).canCastle(board, x, y)) {
                Move castleMove = ((King) piece).doCastleAndReturnMove(board, x, y);
                moveHistory.add(castleMove);
                currentTurnColor = currentTurnColor.equals("white") ? "black" : "white";
                return;
            }
        }

        if (piece instanceof Rook) {
            ((Rook) piece).setHasMoved(true);
        }

        if (piece instanceof Pawn) {
            int dx = x - oldX;
            int dy = y - oldY;
            if (Math.abs(dx) == 1 && board.getAt(x, y) == null) {
                Piece adjacent = board.getAt(x, oldY);
                if (adjacent instanceof Pawn && !adjacent.getColor().equals(piece.getColor())) {
                    Pawn adjPawn = (Pawn) adjacent;
                    if (adjPawn.isEnPassantVulnerable()) {
                        enPassantTarget = adjPawn;
                        isKilled = true;
                    }
                }
            }
        }

        piece.setCoordinatesX(x);
        piece.setCoordinatesY(y);
        if (enPassantTarget != null) board.getPieces().remove(enPassantTarget);
        else if (isKilled) board.getPieces().remove(target);

        boolean kingInCheck = isCheck(piece.getColor());

        piece.setCoordinatesX(oldX);
        piece.setCoordinatesY(oldY);
        if (enPassantTarget != null) board.getPieces().add(enPassantTarget);
        else if (isKilled) board.getPieces().add(target);

        if (kingInCheck) return;

        piece.setCoordinatesX(x);
        piece.setCoordinatesY(y);
        if (enPassantTarget != null) board.getPieces().remove(enPassantTarget);
        else if (isKilled) board.getPieces().remove(target);

        for (Piece p : board.getPieces()) {
            if (p instanceof Pawn) {
                ((Pawn) p).setEnPassantVulnerable(false);
            }
        }

        if (piece instanceof Pawn && Math.abs(y - oldY) == 2) {
            ((Pawn) piece).setEnPassantVulnerable(true);
        }

        if (piece instanceof Pawn && (y == 1 || y == 8)) {
            board.getPieces().remove(piece);
            Piece promoted = new Queen(x, y, piece.getColor());
            board.getPieces().add(promoted);
            Move promotionMove = new PromotionMove(oldX, x, oldY, y, promoted, target, "queen");
            moveHistory.add(promotionMove);
        } else {
            Move move = isKilled
                    ? new Move(oldX, x, oldY, y, piece, (enPassantTarget != null ? enPassantTarget : target))
                    : new Move(oldX, x, oldY, y, piece);
            moveHistory.add(move);
        }

        currentTurnColor = currentTurnColor.equals("white") ? "black" : "white";
    }


    public void undoMove() {
        if (moveHistory.isEmpty()) return;

        Move lastMove = moveHistory.remove(moveHistory.size() - 1);

        if (lastMove instanceof CastleMove) {
            CastleMove castleMove = (CastleMove) lastMove;
            King king = (King) castleMove.getMovedPiece();
            board.removeAt(castleMove.getEndX(), castleMove.getEndY());
            king.setCoordinatesX(castleMove.getStartX());
            king.setCoordinatesY(castleMove.getStartY());
            king.setHasMoved(false);
            board.addPiece(king);

            Rook rook = castleMove.getRook();
            board.removeAt(castleMove.getRookEndX(), castleMove.getRookEndY());
            rook.setCoordinatesX(castleMove.getRookStartX());
            rook.setCoordinatesY(castleMove.getRookStartY());
            rook.setHasMoved(false);
            board.addPiece(rook);

            currentTurnColor = currentTurnColor.equals("white") ? "black" : "white";
            return;
        }

        if (lastMove instanceof PromotionMove) {
            PromotionMove promotionMove = (PromotionMove) lastMove;
            Piece movedPiece = promotionMove.getMovedPiece();

            Pawn pawn = (Pawn) movedPiece;
            board.removeAt(promotionMove.getEndX(), promotionMove.getEndY());
            pawn.setCoordinatesX(promotionMove.getStartX());
            pawn.setCoordinatesY(promotionMove.getStartY());

            Piece originalPawn = new Pawn(promotionMove.getStartX(), promotionMove.getStartY(), pawn.getColor());
            board.addPiece(originalPawn);

            Piece capturedPiece = promotionMove.getKilledPiece();
            if (capturedPiece != null) {
                board.getPieces().add(capturedPiece);
            }
        }

        Piece movedPiece = lastMove.getMovedPiece();

        movedPiece.setCoordinatesX(lastMove.getStartX());
        movedPiece.setCoordinatesY(lastMove.getStartY());

        Piece captured = lastMove.getKilledPiece();
        if (captured != null) {
            board.getPieces().add(captured);
        }

        currentTurnColor = currentTurnColor.equals("white") ? "black" : "white";
    }

    public boolean isCheck(String color) {
        Piece king = board.getKing(color);
        for (Piece enemy: board.getPieces()) {
            if (enemy.getColor().equals(color)) {
                continue;
            }
            if (enemy.canMove(board, king.getCoordinatesX(), king.getCoordinatesY())) {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckmate(String color) {
        return isCheck(color) && this.generateMoves(color).isEmpty();
    }

    public boolean isStalemate(String color) {
        if (isCheck(color)) return false;
        return this.generateMoves(color).isEmpty();
    }

    public List<Move> generateMoves(String color) {
        List<Piece> pieces = this.getBoard().getPieces();
        List<Move> moves = new ArrayList<>();
        for (Piece p: pieces) {
            if (p.getColor().equals(color)) {
                for (int i = 1; i <= 8; ++i) {
                    for (int j = 1; j <= 8; ++j) {
                        if (p.canMove(this.getBoard(), i, j)) {
                            Game simulateGame = this.clone();
                            Piece simulatePiece = simulateGame.getBoard().getAt(p.getCoordinatesX(), p.getCoordinatesY());
                            simulateGame.movePiece(simulatePiece, i, j);
                            if (!simulateGame.getCurrentTurnColor().equals(color)) {
                                moves.add(new Move(p.getCoordinatesX(), i, p.getCoordinatesY(), j, p));
                            }
                        }
                    }
                }
            }
        }
        return moves;
   }

    public ArrayList<Move> getMoveHistory() {
        return moveHistory;
    }

    public void setMoveHistory(ArrayList<Move> moveHistory) {
        this.moveHistory = moveHistory;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getCurrentTurnColor() {
        return currentTurnColor;
    }

    public void setCurrentTurnColor(String currentTurnColor) {
        this.currentTurnColor = currentTurnColor;
    }

    public Game clone() {
        Game copy = new Game();
        copy.setCurrentTurnColor(this.currentTurnColor);
        copy.setBoard(this.board.clone());

        ArrayList<Move> copiedHistory = new ArrayList<>();
        for (Move move : this.moveHistory) {
            copiedHistory.add(move.clone());
        }
        copy.setMoveHistory(copiedHistory);

        return copy;
    }
}

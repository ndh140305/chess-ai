import java.util.List;

public class Main {
    public static void showBoard(Game game) {
        for (int i = 8; i >= 1; --i) {
            for (int j = 8; j >= 1; --j) {
                Piece piece = game.getBoard().getAt(j, i);
                if (piece != null) {
                    System.out.print((piece.getColor().equals("white") ? "w" : "b") + piece.getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        List<Move> moves = game.generateMoves(game.getCurrentTurnColor());

        for (Piece p : game.getBoard().getPieces()) {
           System.out.println(p.getClass().getSimpleName() + "-" + p.getColor() + "-" + p.getCoordinatesX() + "," + p.getCoordinatesY());
        }
        System.out.println(Minimax.countMaterial(game.getBoard(), "black"));
        for (Move move: moves) {
            System.out.println(move.getMovedPiece().getSymbol() + move.getStartX() + "," + move.getStartY() + " -> " + move.getEndX() + "," + move.getEndY());

        }
        System.out.println(moves.size());

        game.movePiece(game.getBoard().getAt(2, 2), 2, 4);
        List<Move> moves1 = game.generateMoves("white");
        for (Move move: moves1) {
            System.out.println(move.getMovedPiece().getSymbol() + move.getStartX() + "," + move.getStartY() + " -> " + move.getEndX() + "," + move.getEndY());

        }
        System.out.println(moves1.size());
    }
}

import java.util.List;

class Minimax {
    private static final int[][] PAWN_TABLE = {
            {  0,  0,  0,   0,   0,  0,  0,  0 },
            { 50, 50, 50, -50, -50, 50, 50, 50 },
            { 10, 10, 20,  30,  30, 20, 10, 10 },
            {  0,  0,  0,  20,  20,  0,  0,  0 },
            {  0,  0,  0, -20, -20,  0,  0,  0 },
            { 10,-10,-20,   0,   0,-20,-10, 10 },
            { 10, 20, 20, -20, -20, 20, 20, 10 },
            {  0,  0,  0,   0,   0,  0,  0,  0 }
    };

    private static final int[][] KNIGHT_TABLE = {
            {-50, -40, -30, -30, -30, -30, -40, -50},
            {-40, -20,   0,   0,   0,   0, -20, -40},
            {-30,   0,  10,  15,  15,  10,   0, -30},
            {-30,   5,  15,  20,  20,  15,   5, -30},
            {-30,   0,  15,  20,  20,  15,   0, -30},
            {-30,   5,  10,  15,  15,  10,   5, -30},
            {-40, -20,   0,   5,   5,   0, -20, -40},
            {-50, -40, -30, -30, -30, -30, -40, -50}
    };

    private static final int[][] BISHOP_TABLE = {
            {-20, -10, -10, -10, -10, -10, -10, -20},
            {-10,   0,   0,   0,   0,   0,   0, -10},
            {-10,   0,   5,  10,  10,   5,   0, -10},
            {-10,   5,   5,  10,  10,   5,   5, -10},
            {-10,   0,  10,  10,  10,  10,   0, -10},
            {-10,  10,  10,  10,  10,  10,  10, -10},
            {-10,   5,   0,   0,   0,   0,   5, -10},
            {-20, -10, -10, -10, -10, -10, -10, -20}
    };

    private static final int[][] ROOK_TABLE = {
            {  0,   0,   0,   0,   0,   0,   0,   0 },
            {  5,  10,  10,  10,  10,  10,  10,   5 },
            {-5,   0,   0,   0,   0,   0,   0,  -5 },
            {-5,   0,   0,   0,   0,   0,   0,  -5 },
            {-5,   0,   0,   0,   0,   0,   0,  -5 },
            {-5,   0,   0,   0,   0,   0,   0,  -5 },
            {-5,   0,   0,   0,   0,   0,   0,  -5 },
            {  0,   0,   0,   5,   5,   0,   0,   0 }
    };

    private static final int[][] QUEEN_TABLE = {
            {-20, -10, -10,  -5,  -5, -10, -10, -20},
            {-10,   0,   0,   0,   0,   0,   0, -10},
            {-10,   0,   5,   5,   5,   5,   0, -10},
            { -5,   0,   5,   5,   5,   5,   0,  -5},
            {  0,   0,   5,   5,   5,   5,   0,  -5},
            {-10,   5,   5,   5,   5,   5,   0, -10},
            {-10,   0,   5,   0,   0,   0,   0, -10},
            {-20, -10, -10,  -5,  -5, -10, -10, -20}
    };

    private static final int[][] KING_TABLE = {
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            { 20,  20,   0,   0,   0,   0,  20,  20},
            { 20,  30,  10,   0,   0,  10,  30,  20}
    };

    private static void sortMoves(List<Move> moves, Board board) {
        moves.sort((a, b) -> {
            Piece aTarget = board.getAt(a.getEndX(), a.getEndY());
            Piece bTarget = board.getAt(b.getEndX(), b.getEndY());

            int aScore = (aTarget != null ? aTarget.getValue() : 0);
            int bScore = (bTarget != null ? bTarget.getValue() : 0);

            return Integer.compare(bScore, aScore);
        });
    }


    public static Move findBestMove(Game game, int depth) {
        String currentColor = game.getCurrentTurnColor();
        List<Move> moves = game.generateMoves(currentColor);
        sortMoves(moves, game.getBoard());

        if (moves.isEmpty()) return null;

        Move bestMove = null;
        int bestEval = currentColor.equals("white") ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : moves) {
            Game nextGame = game.clone();
            Piece realPiece = nextGame.getBoard().getAt(move.getStartX(), move.getStartY());
            nextGame.movePiece(realPiece, move.getEndX(), move.getEndY());

            int eval = minimax(nextGame, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, !currentColor.equals("white"));

            if (currentColor.equals("white")) {
                if (eval > bestEval) {
                    bestEval = eval;
                    bestMove = move;
                }
            } else {
                if (eval < bestEval) {
                    bestEval = eval;
                    bestMove = move;
                }
            }
        }

        return bestMove;
    }


    private static int minimax(Game game, int depth, int alpha, int beta, boolean isWhiteToMove) {
        if (depth == 0) return evaluate(game);

        String currentColor = game.getCurrentTurnColor();
        List<Move> moves = game.generateMoves(currentColor);
        sortMoves(moves, game.getBoard());

        if (moves.isEmpty()) {
            if (game.isCheckmate(currentColor))
                return isWhiteToMove ? Integer.MIN_VALUE + 1 : Integer.MAX_VALUE - 1;
            if (game.isStalemate(currentColor)) return 0;
            return evaluate(game);
        }

        if (isWhiteToMove) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                Game nextGame = game.clone();
                Piece piece = nextGame.getBoard().getAt(move.getStartX(), move.getStartY());
                nextGame.movePiece(piece, move.getEndX(), move.getEndY());

                int eval = minimax(nextGame, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
                Game nextGame = game.clone();
                Piece piece = nextGame.getBoard().getAt(move.getStartX(), move.getStartY());
                nextGame.movePiece(piece, move.getEndX(), move.getEndY());

                int eval = minimax(nextGame, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    public static int evaluate(Game game) {
        int whiteEval = 0;
        int blackEval = 0;

        Board board = game.getBoard();

        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                Piece piece = board.getAt(i, j);
                if (piece != null) {
                    int material = piece.getValue();
                    int positional = getPositionalValue(piece, i, j);
                    if (piece.getColor().equals("white")) {
                        whiteEval += material + positional;
                    } else {
                        blackEval += material + positional;
                    }
                }
            }
        }

        int evaluation = whiteEval - blackEval;
        return evaluation;
    }


    public static int countMaterial(Board board, String color) {
        int total = 0;
        for (int i = 1; i <= 8; ++i) {
            for (int j = 1; j <= 8; ++j) {
                if (board.getAt(i, j) != null && board.getAt(i, j).getColor().equals(color)) {
                    total += board.getAt(i, j).getValue();
                }
            }
        }
        return total;
    }

    private static int getPositionalValue(Piece piece, int x, int y) {
        int row = piece.getColor().equals("white") ? 8 - y : y - 1;
        int col = x - 1;

        switch (piece.getSymbol()) {
            case "P":
                return piece.getColor().equals("white") ? PAWN_TABLE[row][col] : PAWN_TABLE[7 - row][col];
            case "N":
                return piece.getColor().equals("white") ? KNIGHT_TABLE[row][col] : KNIGHT_TABLE[7 - row][col];
            case "B":
                return piece.getColor().equals("white") ? BISHOP_TABLE[row][col] : BISHOP_TABLE[7 - row][col];
            case "R":
                return piece.getColor().equals("white") ? ROOK_TABLE[row][col] : ROOK_TABLE[7 - row][col];
            case "Q":
                return piece.getColor().equals("white") ? QUEEN_TABLE[row][col] : QUEEN_TABLE[7 - row][col];
            case "K":
                return piece.getColor().equals("white") ? KING_TABLE[row][col] : KING_TABLE[7 - row][col];
            default:
                return 0;
        }
    }

}

import javax.swing.*;
import java.awt.*;

public class ChessGameUI extends JFrame {
    private Game game;
    private JButton[][] buttons = new JButton[8][8];
    private Piece selectedPiece = null;
    private PieceSpriteLoader spriteLoader;
    private boolean isVsAI = false;

    private void checkGameOver() {
        String currentColor = game.getCurrentTurnColor();
        if (game.isCheckmate(currentColor)) {
            JOptionPane.showMessageDialog(this, currentColor + " is checkmated. Game over.");
            disableBoard();
            replayMessage();

        } else if (game.isStalemate(currentColor)) {
            JOptionPane.showMessageDialog(this, "Stalemate. Game over.");
            disableBoard();
            replayMessage();
        }
    }

    private void disableBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                buttons[x][y].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        game = new Game();
        refreshBoard();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                buttons[x][y].setEnabled(true);
            }
        }
    }

    private void replayMessage() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Replay", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            disableBoard();
        }
    }

    public ChessGameUI() {
        int option = JOptionPane.showOptionDialog(
                null,
                "Select Game Mode",
                "Game Mode",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"1 Player (vs AI)", "2 Players"},
                "2 Players"
        );

        if (option == 0) {
            isVsAI = true;
        }

        game = new Game();
        initUI();
        refreshBoard();
    }


    private void initUI() {
        setTitle("Chess Game");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        try {
            spriteLoader = new PieceSpriteLoader("C:\\Users\\Cuong\\IdeaProjects\\chess\\src\\main\\java\\Chess_Pieces_Sprite.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int y = 8; y >= 1; y--) {
            for (int x = 1; x <= 8; x++) {
                JButton button = new JButton();
                button.setBackground((x + y) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99));
                int finalX = x;
                int finalY = y;
                button.addActionListener(e -> handleClick(finalX, finalY));
                buttons[x - 1][8 - y] = button;
                boardPanel.add(button);
            }
        }

        JButton undoButton = new JButton("Undo Move");
        undoButton.addActionListener(e -> {
            game.undoMove();
            refreshBoard();
        });

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(undoButton, BorderLayout.NORTH);

        add(boardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        setVisible(true);
    }

    private void refreshBoard() {
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                Piece piece = game.getBoard().getAt(x, y);
                JButton button = buttons[x - 1][8 - y];
                if (piece != null) {
                    button.setIcon(spriteLoader.getIcon(piece));
                    button.setText("");
                } else {
                    button.setIcon(null);
                    button.setText("");
                }

            }
        }
    }

    private void handleClick(int x, int y) {
        Piece clickedPiece = game.getBoard().getAt(x, y);

        if (selectedPiece == null) {
            if (clickedPiece != null && clickedPiece.getColor().equals(game.getCurrentTurnColor())) {
                selectedPiece = clickedPiece;
            }
        } else {
            if (selectedPiece == clickedPiece) {
                selectedPiece = null;
                return;
            }

            if (selectedPiece.canMove(game.getBoard(), x, y)) {
                game.movePiece(selectedPiece, x, y);
                handlePawnPromotion(selectedPiece, x, y);
                refreshBoard();
                checkGameOver();

                if (isVsAI && game.getCurrentTurnColor().equals("black")) {
                    Move bestMove = Minimax.findBestMove(game.clone(), 4);
                    System.out.println(bestMove);
                    System.out.println(game.getCurrentTurnColor());
                    if (bestMove != null) {
                        Piece pieceToMove = game.getBoard().getAt(bestMove.getStartX(), bestMove.getStartY());
                        System.out.println(pieceToMove);
                        game.movePiece(pieceToMove, bestMove.getEndX(), bestMove.getEndY());
                        System.out.println(game.getCurrentTurnColor());
                        refreshBoard();
                        checkGameOver();
                    }
                }
            }

            selectedPiece = null;
        }
    }

    private void handlePawnPromotion(Piece piece, int x, int y) {
        if (piece instanceof Pawn) {
            int promotionRow = piece.getColor().equals("white") ? 8 : 1;
            if (y == promotionRow) {
                String[] options = {"Queen", "Rook", "Bishop", "Knight"};
                String choice = (String) JOptionPane.showInputDialog(
                        this,
                        "Choose piece to promote to:",
                        "Pawn Promotion",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (choice != null) {
                    ((Pawn) piece).promoteTo(game.getBoard(), choice);
                    refreshBoard();
                }
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGameUI::new);
    }
}
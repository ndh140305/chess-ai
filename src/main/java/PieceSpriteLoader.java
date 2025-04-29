import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PieceSpriteLoader {
    private BufferedImage spriteSheet;
    private int pieceWidth;
    private int pieceHeight;

    public PieceSpriteLoader(String path) throws IOException {
        spriteSheet = ImageIO.read(new File(path));
        pieceWidth = spriteSheet.getWidth() / 6;
        pieceHeight = spriteSheet.getHeight() / 2;
    }

    public ImageIcon getIcon(Piece piece) {
        int row = piece.getColor().equals("white") ? 0 : 1;
        int col = switch (piece.getClass().getSimpleName()) {
            case "King" -> 0;
            case "Queen" -> 1;
            case "Bishop" -> 2;
            case "Knight" -> 3;
            case "Rook" -> 4;
            case "Pawn" -> 5;
            default -> throw new IllegalArgumentException("Invalid piece: " + piece);
        };
        BufferedImage subImg = spriteSheet.getSubimage(col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight);
        Image scaled = subImg.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}

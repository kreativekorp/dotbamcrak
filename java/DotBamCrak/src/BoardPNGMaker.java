import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;
import com.kreative.dotbamcrak.board.Board;
import com.kreative.dotbamcrak.board.BoardPopulator;
import com.kreative.dotbamcrak.board.RandomSolvableBoardPopulator;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.settings.Library;
import com.kreative.dotbamcrak.tileset.TileSet;
import com.kreative.dotbamcrak.tileset.TileSetInfo;

public class BoardPNGMaker {
	public static void main(String[] args) throws Exception {
		BoardPopulator populator = new RandomSolvableBoardPopulator(new Random());
		Library library = new Library();
		for (TileSetInfo tilesetinfo : library.getTileSets()) {
			TileSet tileset = tilesetinfo.createTileSet();
			for (Layout layout : library.getLayouts()) {
				Board board = new Board(tileset, layout, populator);
				BufferedImage image = new BufferedImage(board.getWindowPixelWidth(), board.getWindowPixelHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.createGraphics();
				board.paintBackground(g, 0, 0, image.getWidth(), image.getHeight());
				board.paintBoard(g, 0, 0, image.getWidth(), image.getHeight(), false);
				g.dispose();
				ImageIO.write(image, "png", new File(tileset.toString() + " - " + layout.toString() + ".png"));
			}
		}
	}
}

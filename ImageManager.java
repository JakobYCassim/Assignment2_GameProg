import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
/**
   The ImageManager class manages the loading and processing of images.
*/

public class ImageManager {
    private static Map<String, Image> images;
	private static Map<String, ImageIcon> gifs;

	static{
		images = new HashMap<>();
		gifs = new HashMap<>();
	}
   	public ImageManager () {

	}

	public static Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}

	public static void loadImage(String name, String fileName) {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(fileName));
			images.put(name, bufferedImage);
		} catch (IOException e) {
			System.out.println ("Error opening file " + fileName + ":" + e);
		}
	}

	public static void loadGIF(String name, String fileName) {
		ImageIcon icon = new ImageIcon(fileName);
		gifs.put(name, icon);
	}

	public static Image getImage(String name) {
		return images.get(name);
	}

	public static ImageIcon getGIF(String name) {
		return gifs.get(name);
	}

	public static BufferedImage loadBufferedImage(String filename) {
		BufferedImage bi = null;

		File file = new File (filename);
		try {
			bi = ImageIO.read(file);
		}
		catch (IOException ioe) {
			System.out.println ("Error opening file " + filename + ":" + ioe);
		}
		return bi;
	}


  	// make a copy of the BufferedImage src

	public static BufferedImage copyImage(BufferedImage src) {
		if (src == null)
			return null;


		int imWidth = src.getWidth();
		int imHeight = src.getHeight();

		BufferedImage copy = new BufferedImage (imWidth, imHeight,
							BufferedImage.TYPE_INT_ARGB);

    		Graphics2D g2d = copy.createGraphics();

    		// copy image
    		g2d.drawImage(src, 0, 0, null);
    		g2d.dispose();

    		return copy; 
	  }

}



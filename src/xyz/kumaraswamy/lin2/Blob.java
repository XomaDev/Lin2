package xyz.kumaraswamy.lin2;

import xyz.kumaraswamy.lin2.structs.Pixel;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Blob {

  private final BufferedImage image;
  private final int x, y;

  public Grid grid;

  public Blob(File file) throws IOException {
    image = ImageIO.read(file);
    x = image.getWidth();
    y = image.getHeight();
  }

  public List<char[]> extractBlobs(File output) throws IOException {
    List<char[]> blobs = new ArrayList<>();

    for (int xPos = 0; xPos < x; xPos++) {
      for (int yPos = 0; yPos < y; yPos++) {
        Pixel pixel = getColor(xPos, yPos);
        if (pixel == null) {
          continue;
        }
        if (isForeground(pixel)) {
          grid = new Grid();
          mapForegroundBlob(xPos, yPos);
          blobs.add(grid.computeGrid());
          //       least y
          //
          // least X,     max x
          //
          //        max y
          // width = max x - least X
          // height = max y - least y
        }
      }
    }
    ImageIO.write(image, "PNG", output);
    return blobs;
  }

  private void mapForegroundBlob(int x, int y) {
    grid.update(new Pixel(new Color(image.getRGB(x, y)), x, y));
    image.setRGB(x, y, Color.BLUE.getRGB());

    int[][] neighbors = new int[][]{
        {x - 1, y}, // left
        {x + 1, y}, // right
        {x, y - 1}, // top
        {x, y + 1}, // bottom
        {x - 1, y - 1}, // top slant left
        {x + 1, y - 1}, // top slant right
        {x - 1, y + 1}, // bottom slant left
        {x + 1, y + 1}, // bottom slant right
    };
    for (int i = 0; i < 8; i++) {
      int[] position = neighbors[i];
      int nX = position[0], nY = position[1];
      Pixel neighbor = getColor(nX, nY);
      if (neighbor == null) {
        continue;
      }
      if (isForeground(neighbor)) {
        mapForegroundBlob(nX, nY);
      }
    }
  }

  private boolean isForeground(Pixel pixel) {
    Color color = pixel.color;
    return color.getRed() + color.getGreen() + color.getBlue() >= 382;
  }

  private Pixel getColor(int xPos, int yPos) {
    if (xPos < 0 || yPos < 0 || xPos >= x || yPos >= y) {
      return null;
    }
    Pixel pixel = new Pixel(new Color(image.getRGB(xPos, yPos)), xPos, yPos);
    if (grid != null && grid.isOwned(pixel)) {
      // already read :)
      return null;
    }
    return pixel;
  }
}

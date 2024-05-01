package xyz.kumaraswamy.lin2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lin2 {
  public static List<char[]> bitImages(File[] images) throws IOException {
    List<char[]> bitResults = new ArrayList<>();
    for (File imageFile : images) {
      char[] bits = getBits(imageFile);
      bitResults.add(bits);
    }
    return bitResults;
  }

  public static char[] getBits(File imageFile) throws IOException {
    BufferedImage image = ImageIO.read(imageFile);
    int x = image.getWidth();
    int y = image.getHeight();
    char[] bits = new char[x * y];
    int i = 0;
    for (int xPos = 0; xPos < x; xPos++) {
      for (int yPos = 0; yPos < y; yPos++) {
        Color color = new Color(image.getRGB(xPos, yPos));
        int sum = color.getRed() + color.getGreen() + color.getBlue();
        bits[i++] = sum >= 382 ? '1' : '0';
      }
    }
    return bits;
  }
}

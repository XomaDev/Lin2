package xyz.kumaraswamy.lin2;

import xyz.kumaraswamy.lin2.structs.Pixel;

import java.util.ArrayList;
import java.util.List;

public class Grid {

  // a 28x28 grid
  public static final int LENGTH_WIDTH = 28;
  public static final int LENGTH_HEIGHT = 28;

  private final List<Pixel> pixels = new ArrayList<>();

  public int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
  public int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;

  public void update(Pixel pixel) {
    pixels.add(pixel);
    int x = pixel.xPos, y = pixel.yPos;

    minX = Math.min(minX, x);
    maxX = Math.max(maxX, x);

    minY = Math.min(minY, y);
    maxY = Math.max(maxY, y);
  }

  public char[] computeGrid() {
    int width = maxX - minX + 1;
    int height = maxY - minY + 1;
    // +1 to compensate 0 indexing

    System.out.println("Width: " + width);
    System.out.println("Height: " + height);

    // TODO:
    //  check values when width > LENGTH_WIDTH
    //                    height > LENGTH_HEIGHT
    //           then we'll have to resize

    int widthOff = LENGTH_WIDTH - width;
    int heightOff = LENGTH_HEIGHT - height;

    char[][] grid = new char[LENGTH_WIDTH][LENGTH_HEIGHT];
    // default init: '0'
    for (int i = 0; i < LENGTH_WIDTH; i++) {
      char[] bits = grid[i];
      for (int j = 0; j < LENGTH_HEIGHT; j++) {
        bits[j] = '0';
      }
    }
    System.out.println("minX: " + minX + " | maxX: " + maxX);
    System.out.println("minY: " + minY + " | maxY: " + maxY);

    System.out.println("DiffX: " + (maxX - minX));
    System.out.println("DiffY: " + (maxY - minY));

    for (Pixel pixel : pixels) {
      int xPos = pixel.xPos - minX;
      int yPos = pixel.yPos - minY;
      xPos += widthOff / 2;
      yPos += heightOff / 2;
      grid[xPos][yPos] = '1';
    }
    char[] flatten = new char[LENGTH_WIDTH * LENGTH_HEIGHT];
    int flatIndex = 0;
    for (char[] chars : grid) {
      System.out.println(new String(chars));
      for (char c : chars) {
        flatten[flatIndex++] = c;
      }
    }
    return flatten;
  }

  public boolean isOwned(Pixel pixel) {
    return pixels.contains(pixel);
  }
}

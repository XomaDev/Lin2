package xyz.kumaraswamy.lin2.structs;

import java.awt.*;
import java.util.Objects;

public class Pixel {

  public final Color color;
  public final int xPos, yPos;

  public Pixel(Color color, int xPos, int yPos) {
    this.color = color;
    this.xPos = xPos;
    this.yPos = yPos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pixel pixel = (Pixel) o;
    return xPos == pixel.xPos && yPos == pixel.yPos;
  }

  @Override
  public int hashCode() {
    return Objects.hash(xPos, yPos);
  }
}

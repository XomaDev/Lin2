package xyz.kumaraswamy.lin2;

import xyz.kumaraswamy.lin2.structs.Prediction;
import xyz.kumaraswamy.lin2.structs.Result;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Lin2 {

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

  public static List<Result> predict(Model[] models, char[] data) {
    int numberOfModels = models.length;
    List<Result> predictions = new ArrayList<>(numberOfModels);

    for (int i = 0, length = models.length; i < length; i++) {
      System.out.println("Running permutation [" + i + "/" + length + "]");
      Model model = models[i];
      Prediction prediction = model.compare(data);

      predictions.add(new Result(
          1 - (double) prediction.data / prediction.matched.length,
          prediction.data,
          prediction.groupName,
          prediction.name,
          prediction.matched
      ));
    }

    predictions.sort((o1, o2) -> Integer.compare(o1.leastDistance, o2.leastDistance));
    return predictions;
  }
}

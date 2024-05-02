package xyz.kumaraswamy.lin2;

import xyz.kumaraswamy.lin2.structs.Prediction;
import xyz.kumaraswamy.lin2.structs.Result;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    List<Result> predictions = Collections.synchronizedList(new ArrayList<>(numberOfModels));
    ExecutorService executor = Executors.newFixedThreadPool(numberOfModels);
    CountDownLatch latch = new CountDownLatch(numberOfModels);

    for (int i = 0, length = models.length; i < length; i++) {
      int finalI = i;
      executor.submit(() -> {
        try {
          System.out.println("Parallel permutation [" + finalI + "/" + length + "]");
          Result result = predictResult(data, models[finalI]);
          predictions.add(result);
        } finally {
          latch.countDown();
        }
      });
    }
    executor.shutdown();
    try {
      latch.await();
      predictions.sort(Comparator.comparingInt(o -> o.leastDistance));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return predictions;
  }

  private static Result predictResult(char[] data, Model model) {
    Prediction prediction = model.compare(data);
    return new Result(
        1 - (double) prediction.data / prediction.matched.length,
        prediction.data,
        prediction.groupName,
        prediction.name,
        prediction.matched
    );
  }
}

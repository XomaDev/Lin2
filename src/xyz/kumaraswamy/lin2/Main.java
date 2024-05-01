package xyz.kumaraswamy.lin2;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
  public static void main(String[] args) throws IOException {
    // the number 2
    // char[] test = Lin2.getBits(new File("/home/kumaraswamy/Mlemon/Lin2/testing/two.png"));
    // char[] test = Lin2.getBits(new File("/home/kumaraswamy/Mlemon/Lin2/testing/five.png"));
   //  char[] test = Lin2.getBits(new File("/home/kumaraswamy/Mlemon/Lin2/testing/nine.png"));
    char[] test = Lin2.getBits(new File("/home/kumaraswamy/Mlemon/Lin2/testing/Six1.png"));

    File[] train = new File("/home/kumaraswamy/Mlemon/Lin2/train").listFiles();
    for (File trainSet : train) {
      String setName = trainSet.getName();
      int leastDistance = Integer.MAX_VALUE;
      char[] leastDistanceChars = null;
      File leastDistanceMatch = null;
      for (File image : trainSet.listFiles()) {
        char[] bits = Lin2.getBits(image);
        int distance = DistanceAlgorithm.dist(bits, test);
        if (leastDistance > distance) {
          leastDistanceChars = bits;
          leastDistance = distance;
          leastDistanceMatch = image;
        }
      }
      System.out.println(setName);
      System.out.println("\t Least Distance: " + leastDistance);
      System.out.println("\t File: " + leastDistanceMatch.getAbsolutePath());
      System.out.println("\t Comparison: " + new String(test));
      System.out.println("\t With: " + new String(leastDistanceChars));
    }
  }
}
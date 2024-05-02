package xyz.kumaraswamy.lin2;

import xyz.kumaraswamy.lin2.structs.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Main {

  private static final File ROOT = new File(System.getProperty("user.dir"));
  private static final File TRAINING_ROOT = new File(ROOT, "/train");
  private static final File COMPILED_ROOT = new File(ROOT, "/compiled");

  public static void main(String[] args) throws IOException {
    // compileModels();
//     test();
     multitest();
  }

  private static void multitest() throws IOException {
    List<char[]> blobs = new Blob(new File("/home/kumaraswamy/Mlemon/Lin2/doubletest/numbers.png"))
        .extractBlobs(new File("/home/kumaraswamy/Mlemon/Lin2/doubletest/output.png"));

    Model[] models = Model.loadModels(COMPILED_ROOT.listFiles());
    StringBuilder numberOutput = new StringBuilder();
    for (char[] blob : blobs) {
      // System.out.println(new String(blob));
      List<Result> results = Lin2.predict(models, blob);
      numberOutput.append(results.get(0).group);
//      for (Result result : results) {
//        System.out.println("Group (" + result.group + ")");
//        System.out.println("\t Accuracy (" + result.accuracy + ")");
//        System.out.println("\t LeastDistance (" + result.leastDistance + ")");
//        System.out.println("\t Percentage (" + (int) (result.accuracy * 100) + "%)\n");
//      }
    }
    System.out.println("Recognized output: " + numberOutput);
  }

  private static void test() throws IOException {
    char[] test = Lin2.getBits(new File("/home/kumaraswamy/Mlemon/Lin2/test/eight.png"));
    if (true) {
      int index = 0;
      while (index != test.length) {
        for (int i = 0; i < 28; i++) {
          if (index == test.length) {
            break;
          }
          System.out.print(test[index++]);
        }
        System.out.println();
      }
      return;
    }
    System.out.println("Test Data: " + new String(test));
    System.out.println("Length: " + test.length);

    List<Result> results = Lin2.predict(Model.loadModels(COMPILED_ROOT.listFiles()), test);

    for (Result result : results) {
      System.out.println("Group (" + result.group + ")");
      System.out.println("\t Accuracy (" + result.accuracy + ")");
      System.out.println("\t LeastDistance (" + result.leastDistance + ")");
      System.out.println("\t Percentage (" + (int) (result.accuracy * 100) + "%)\n");
    }
  }

  private static void compileModels() throws IOException {
    File[] trainingModules = TRAINING_ROOT.listFiles();
    for (File moduleDir : trainingModules) {
      String moduleName = moduleDir.getName();
      File outputModuleFile = new File(COMPILED_ROOT, moduleName);

      File[] moduleImages = moduleDir.listFiles();
      try (FileOutputStream fileOutput = new FileOutputStream(outputModuleFile)) {
        Model.compileModel(moduleName, moduleImages, fileOutput);
      }
    }
  }
}
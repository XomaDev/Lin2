package xyz.kumaraswamy.lin2;

import xyz.kumaraswamy.lin2.io.BitInputStream;
import xyz.kumaraswamy.lin2.io.BitOutputStream;
import xyz.kumaraswamy.lin2.structs.Prediction;

import java.io.*;

public class Model {

  public static void compileModel(String label, File[] images, OutputStream moduleOutput) throws IOException {
    BitOutputStream bitsOutput = new BitOutputStream(moduleOutput);
    // module name
    bitsOutput.writeString(label);
    // write how many bit sets are written
    bitsOutput.writeInt32(images.length);
    for (File image : images) {
      String fileName = image.getName().split("\\.")[0];
      char[] bits = Lin2.getBits(image);

      // write file name
      bitsOutput.writeString(fileName);
      // write number of bits
      bitsOutput.writeInt32(bits.length);
      for (char bit : bits) {
        bitsOutput.writeBit(bit == '0' ? 0 : 1);
      }
    }
    bitsOutput.close();
  }

  public static Model[] loadModels(File[] files) throws IOException {
    int length = files.length;
    Model[] models = new Model[length];
    for (int i = 0; i < length; i++) {
      models[i] = new Model(files[i]);
    }
    System.out.println("Models loaded");
    return models;
  }

  public final String modelName;

  public final int numberOfSets;

  public final String[] names;
  public final char[][] bitSets;

  public Model(File modelFile) throws IOException {
    BitInputStream bitsInput = new BitInputStream(new FileInputStream(modelFile));
    modelName = bitsInput.readString();
    numberOfSets = bitsInput.readInt32();

    names = new String[numberOfSets];
    bitSets = new char[numberOfSets][];
    // load all the bit sets

    for (int i = 0; i < numberOfSets; i++) {
      String fileName = bitsInput.readString();
      int numberOfBits = bitsInput.readInt32();
      char[] bits = new char[numberOfBits];
      for (int j = 0; j < numberOfBits; j++) {
        bits[j] = bitsInput.readBit() == 1 ? '1' : '0';
      }
      names[i] = fileName;
      bitSets[i] = bits;
    }
  }

  public Prediction compare(char[] data) {
    String fileName = "";
    int leastDistance = Integer.MAX_VALUE;
    char[] bestMatch = null;
    for (int i = 0; i < numberOfSets; i++) {
      char[] bits = bitSets[i];
      // compare bits with data
      int distance = DistanceAlgorithm.dist(bits, data);
      if (leastDistance > distance) {
        bestMatch = bits;
        leastDistance = distance;
        fileName = names[i];
      }
    }
    return new Prediction(modelName, fileName, leastDistance, bestMatch);
  }
}

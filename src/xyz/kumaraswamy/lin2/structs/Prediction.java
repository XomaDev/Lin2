package xyz.kumaraswamy.lin2.structs;

public class Prediction {

  public final String groupName;

  public final String name;
  public int data;

  public final char[] matched;

  public Prediction(String groupName, String name, int data, char[] matched) {
    this.groupName = groupName;
    this.name = name;
    this.data = data;
    this.matched = matched;
  }


  @Override
  public String toString() {
    return "Prediction{" +
        "groupName='" + groupName + '\'' +
        ", name='" + name + '\'' +
        ", accuracy=" + data +
        '}';
  }
}
